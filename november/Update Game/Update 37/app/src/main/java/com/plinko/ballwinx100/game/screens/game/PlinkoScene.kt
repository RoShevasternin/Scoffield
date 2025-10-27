package com.plinko.ballwinx100.game.screens.game

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.plinko.ballwinx100.game.manager.GameDataStoreManager
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen
import com.plinko.ballwinx100.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.experimental.or

class PlinkoScene(
    private val screen: AdvancedScreen,
    private val world: World,
    private val onFinish: () -> Unit
) {

    private val TAG = "PlinkoScene"
    private val CATEGORY_BALL: Short = 0x0001
    private val CATEGORY_PIN: Short = 0x0002
    private val CATEGORY_BASKET: Short = 0x0004

    private val PPM = 100f
    private val ballRegions = listOf(
        screen.game.mainAssets.BALL_0,
        screen.game.mainAssets.BALL_1,
        screen.game.mainAssets.BALL_2,
        screen.game.mainAssets.BALL_3,
    )
    private val ballRegion = ballRegions.random()
    private val backgroundRegion = screen.game.mainAssets.IMAGE_PLATFORM
    private val pinStoppers = ballRegions.filter { it != ballRegion }.random()
    private val basketRegion = listOf(
        screen.game.mainAssets.BASKET_0,
        screen.game.mainAssets.BASKET_1,
        screen.game.mainAssets.BASKET_2,
        screen.game.mainAssets.BASKET_3,
        screen.game.mainAssets.BASKET_3,
        screen.game.mainAssets.BASKET_2,
        screen.game.mainAssets.BASKET_1,
        screen.game.mainAssets.BASKET_0,
    )
    private val pinsScheme = listOf(2, 3, 4, 5, 6, 7).reversed()

    private val debugRenderer = Box2DDebugRenderer()
    private val contactListener = PlinkoContactListener()

    var disappearedBallCount = 0

    private var ballCount = 5
    private var ballPrice = 1

    init {
        screen.coroutine?.launch {
            GameDataStoreManager.removeLevelMoney()

            ballCount = GameDataStoreManager.ballCount()
            ballPrice = GameDataStoreManager.ballPrice()

            runGDX{
                createBall()
                createPins(pinsScheme)
                createBonuses()
                world.setContactListener(
                    contactListener
                )
            }

        }
    }

    private fun createBonuses() {
        val basketCount = basketRegion.size
        val spacingPx = 20f
        val basketWidthPx = basketRegion[0].regionWidth.toFloat()
        val totalWidth = basketCount * basketWidthPx + (basketCount - 1) * spacingPx
        val stage = screen.stageUI
        val screenWidth = stage.viewport.worldWidth

        val startX = (screenWidth - totalWidth) / 2f
        val basketY = 100f

        for (i in 0 until basketCount) {
            val xPx = startX + i * (basketWidthPx + spacingPx)
            val yPx = basketY

            val basketRegion = basketRegion[i]
            // Create body
            val bodyDef = BodyDef().apply {
                type = BodyDef.BodyType.StaticBody
                position.set(
                    (xPx + basketWidthPx / 2f) / PPM,
                    (yPx + basketRegion.regionHeight / 2f) / PPM
                )
            }

            val body = world.createBody(bodyDef)

            val shape = PolygonShape().apply {
                setAsBox(
                    basketWidthPx / 2f / PPM,
                    basketRegion.regionHeight * 0.1f / PPM
                )
            }

            val fixtureDef = FixtureDef().apply {
                this.shape = shape
                restitution = 0.0f
                friction = 0.7f
                isSensor = true

                filter.categoryBits = CATEGORY_BASKET
                filter.maskBits = CATEGORY_BALL
            }

            val fixture = body.createFixture(fixtureDef)
            val basketIndex = if (i > (basketCount / 2) - 1) basketCount - i - 1 else i
            fixture.userData = FixtureUserData(
                ObjectType.valueOf("BASKET_$basketIndex")
            )

            body.isAwake = false
            shape.dispose()

            val actor = BonusBasketActor(body, basketRegion, PPM)
            actor.setBounds(xPx, yPx, basketWidthPx, basketRegion.regionHeight.toFloat())
            stage.addActor(actor)
        }
    }

    private fun createBall() {

        val screenWidth = screen.stageUI.viewport.worldWidth
        val screenHeight = screen.stageUI.viewport.worldHeight
        val ballRadiusMultiplier = 1.8f
        val radiusMeters = ballRegion.regionWidth / 2f / PPM
        val backgroundRadiusMeters = backgroundRegion.regionWidth / 2f
        val backgroundStartX = (screenWidth / 2f) - backgroundRadiusMeters
        val backgroundStartY =
            (screenHeight * 0.875f - ballRegion.regionWidth / 2f) - backgroundRadiusMeters

        val backgroundImage = Image(backgroundRegion).apply {
            setSize(backgroundRegion.regionWidth.toFloat(), backgroundRegion.regionHeight.toFloat())
            setPosition(
                backgroundStartX,
                backgroundStartY + 10f
            )
            setZIndex(100)
            setOrigin(Align.center)
        }

        screen.coroutine?.launch {
            for (i in 0 until ballCount) {
                runGDX {
                    val actorName = "ball$i"
                    val randomPosition = (-20..20).random().toFloat()
                    val startX = ((screenWidth / 2f) + randomPosition) / PPM
                    val startY = (screenHeight * 0.875f - ballRegion.regionWidth / 2f) / PPM

                    val bodyDef = BodyDef().apply {
                        type = BodyDef.BodyType.DynamicBody
                        position.set(startX, startY)
                    }

                    val body = world.createBody(bodyDef)
                    body.linearVelocity = Vector2(0.2f, 0f)
                    val shape = CircleShape().apply {
                        radius = radiusMeters * 0.5f
                    }

                    val fixtureDef = FixtureDef().apply {
                        this.shape = shape
                        density = 1f
                        friction = 0.2f
                        restitution = 0.3f

                        filter.categoryBits = CATEGORY_BALL
                        filter.maskBits = CATEGORY_PIN or CATEGORY_BASKET
                    }

                    val fixture = body.createFixture(fixtureDef)
                    val userData = FixtureUserData(
                        ObjectType.BALL,
                        actorName
                    )
                    fixture.userData = userData
                    shape.dispose()

                    val currentUserBall = Image(ballRegion).apply {
                        setSize(
                            radiusMeters * ballRadiusMultiplier * PPM,
                            radiusMeters * ballRadiusMultiplier * PPM
                        )
                        setOrigin(Align.center)
                    }

                    val actor = BallActor(body!!, currentUserBall, PPM) {
                        screen.coroutine?.launch {
                            disappearedBallCount++

                            if (disappearedBallCount >= ballCount) {
                                onFinish()
                            }
                        }
                    }
                    actor.name = actorName
                    screen.stageUI.addActor(actor)

                    backgroundImage.remove()
                    screen.stageUI.addActor(backgroundImage)

                    contactListener.addBallTrigger(actorName) { ballUserData, otherUserData ->
                        screen.coroutine?.launch {
                            when (otherUserData.objectType.type) {
                                "basket" -> {
                                    val multiplier = otherUserData.objectType.value
                                    val name = ballUserData.actorName

                                    val totalMoney = (ballPrice * multiplier).toInt()
                                    GameDataStoreManager.addLevelMoney(totalMoney)

                                    val actors = screen.stageUI.actors
                                    runGDX {
                                        val currentActor =
                                            actors.first { it.name == name } as BallActor
                                        currentActor.isVisible = false
                                    }
                                }

                                "pin" -> {

                                }

                                else -> {

                                }

                            }
                        }
                    }
                }
                delay(250)
            }

        }


    }

    private fun createPins(
        pinsScheme: List<Int>,
    ) {
        val stage = screen.stageUI
        val screenWidth = stage.viewport.worldWidth
        val screenHeight = stage.viewport.worldHeight

        val maxPinsInRow = pinsScheme.maxOrNull() ?: 0

        val totalWidth = screenWidth * 0.8f
        val totalHeight = screenHeight * 0.57f

        val xSpacing = totalWidth / (maxPinsInRow - 1)
        val ySpacing = totalHeight / (pinsScheme.size - 1)

        val startX = (screenWidth - totalWidth) / 2f
        val startY = screenHeight * 0.15f

        for (rowIndex in pinsScheme.indices) {
            val pinCount = pinsScheme[rowIndex]
            val rowY = startY + rowIndex * ySpacing
            val rowWidth = xSpacing * (pinCount - 1)
            val rowStartX = startX + (totalWidth - rowWidth) / 2f
            val radiusMultiplier = 1f
            val radiusMeters = pinStoppers.regionWidth / 2f / PPM

            for (i in 0 until pinCount) {
                val pinX = rowStartX + i * xSpacing
                val pinY = rowY

                // Create Box2D body
                val bodyDef = BodyDef().apply {
                    type = BodyDef.BodyType.StaticBody
                    position.set(pinX / PPM, pinY / PPM)
                }

                val body = world.createBody(bodyDef)

                val shape = CircleShape().apply {
                    radius = radiusMeters * radiusMultiplier
                }

                val fixtureDef = FixtureDef().apply {
                    this.shape = shape
                    density = 1f
                    restitution = 0.3f

                    filter.categoryBits = CATEGORY_PIN
                    filter.maskBits = CATEGORY_BALL
                }

                val fixture = body.createFixture(fixtureDef)
                fixture.userData = FixtureUserData(
                    ObjectType.PIN
                )
                shape.dispose()
                val image = Image(pinStoppers).apply {
                    setSize(
                        radiusMeters * radiusMultiplier * PPM,
                        radiusMeters * radiusMultiplier * PPM
                    )
                    setOrigin(Align.center)
                }

                val actor = PinActor(body, image, PPM)
                screen.stageUI.addActor(actor)
            }
        }
    }

    fun renderDebug(camera: Camera) {
        debugRenderer.render(world, camera.combined)
    }

    fun dispose() {
        debugRenderer.dispose()
    }
}
