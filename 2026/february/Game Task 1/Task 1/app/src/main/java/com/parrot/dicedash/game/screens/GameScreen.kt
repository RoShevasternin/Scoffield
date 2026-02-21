package com.parrot.dicedash.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.parrot.dicedash.game.actors.AEggPanelA
import com.parrot.dicedash.game.actors.AEggPanelB
import com.parrot.dicedash.game.utils.*
import com.parrot.dicedash.game.utils.actor.animDelay
import com.parrot.dicedash.game.utils.actor.animHide
import com.parrot.dicedash.game.utils.actor.animShow
import com.parrot.dicedash.game.utils.actor.disable
import com.parrot.dicedash.game.utils.actor.enable
import com.parrot.dicedash.game.utils.actor.setOnClickListener
import com.parrot.dicedash.game.utils.advanced.AdvancedScreen
import com.parrot.dicedash.game.utils.advanced.AdvancedStage
import com.parrot.dicedash.game.utils.font.FontParameter
import com.parrot.dicedash.util.log

class GameScreen(): AdvancedScreen() {

    // Field
    private val listPointA = getListPointPosA()
    private val listPointB = getListPointPosB()

    private var pointCountA = listPointA.size
        set(value) {
            field = value
            lblA.setText("$value")
        }
    private var pointCountB = listPointB.size
        set(value) {
            field = value
            lblB.setText("$value")
        }

    // Font
    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font49    = fontGenerator_Inter.generateFont(parameter.setSize(49))

    // Actors
    private val imgFinish = Image(gdxGame.assetsAll.finish)
    private val btnState  = Image(gdxGame.assetsAll.start)
    private val imgA      = Image(gdxGame.assetsAll.a)
    private val imgB      = Image(gdxGame.assetsAll.b)
    private val imgPanelA = Image(gdxGame.assetsAll.PANEL)
    private val imgPanelB = Image(gdxGame.assetsAll.PANEL)
    private val lblA      = Label("$pointCountA", Label.LabelStyle(font49, Color.WHITE))
    private val lblB      = Label("$pointCountB", Label.LabelStyle(font49, Color.WHITE))

    private val aEggPanelA = AEggPanelA(this)
    private val aEggPanelB = AEggPanelB(this)

    // Field

    private var blockState = { }
    private val stateMachine = GameStateMachine(GameUIHandler())

    private val PERS_START_SIZE  = 310f
    private val PERS_FINISH_SIZE = 78f
    private val personageScaleCoff = (PERS_START_SIZE - PERS_FINISH_SIZE) / listPointA.size

    override fun show() {
        stageUI.root.color.a = 0f

        setBackBackground(gdxGame.assetsLoader.BACKGROUND)

        super.show()

        animShow {
            log("Hello Game")
            stateMachine.change(GameState.START)
        }
    }

    override fun animShow(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }

        stageBackScreen.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animShow(TIME_ANIM_SCREEN)

        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animHide(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }

        stageBackScreen.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animHide(TIME_ANIM_SCREEN)

        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgPoints()
        addImgFinish()
        addBtnState()
        addPanelAB()
        addImgAB()

        addEggPanelA()
        addEggPanelB()
    }

    // Actors ------------------------------------------------------------------------

    private fun AdvancedStage.addImgFinish() {
        addActor(imgFinish)
        imgFinish.setBounds(407f, 1605f, 307f, 307f)
    }

    private fun AdvancedStage.addImgPoints() {
        listPointA.forEachIndexed { index, vector2 ->
            addActor(Image(gdxGame.assetsAll.point).also {
                it.setBounds(vector2.x, vector2.y, 46f, 46f)
            })
        }
        listPointB.forEachIndexed { index, vector2 ->
            addActor(Image(gdxGame.assetsAll.point).also {
                it.setBounds(vector2.x, vector2.y, 46f, 46f)
            })
        }
    }

    private fun AdvancedStage.addBtnState() {
        addActor(btnState)
        btnState.setBounds(385f, 230f, 310f, 138f)

        btnState.setOnClickListener(gdxGame.soundUtil) { blockState() }
    }

    private fun AdvancedStage.addImgAB() {
        addActors(imgB, imgA)
        imgA.setBounds(9f, 128f, 396f, 396f)
        imgB.setBounds(673f, 128f, 396f, 396f)
    }

    private fun AdvancedStage.addPanelAB() {
        addActors(imgPanelA, lblA, imgPanelB, lblB)
        imgPanelA.setBounds(68f, 84f, 282f, 107f)
        lblA.setBounds(188f, 119f, 19f, 36f)
        lblA.setAlignment(Align.center)

        imgPanelB.setBounds(709f, 84f, 282f, 107f)
        lblB.setBounds(829f, 119f, 19f, 36f)
        lblB.setAlignment(Align.center)
    }

    private fun AdvancedStage.addEggPanelA() {
        addActors(aEggPanelA)
        aEggPanelA.setBounds(300f, 719f, 481f, 481f)
        aEggPanelA.color.a = 0f
    }

    private fun AdvancedStage.addEggPanelB() {
        addActors(aEggPanelB)
        aEggPanelB.setBounds(300f, 719f, 481f, 481f)
        aEggPanelB.color.a = 0f
    }

    // Logic ------------------------------------------------------------------------

    private fun getListPointPosA() = listOf(
        Vector2(364f, 476f),
        Vector2(416f, 534f),
        Vector2(474f, 592f),
        Vector2(534f, 650f),
        Vector2(574f, 707f),
        Vector2(626f, 765f),
        Vector2(678f, 823f),
        Vector2(669f, 896f),
        Vector2(610f, 948f),
        Vector2(528f, 992f),
        Vector2(493f, 1050f),
        Vector2(434f, 1102f),
        Vector2(379f, 1167f),
        Vector2(434f, 1222f),
        Vector2(488f, 1278f),
        Vector2(533f, 1328f),
        Vector2(598f, 1389f),
        Vector2(663f, 1454f),
        Vector2(608f, 1521f),
        Vector2(538f, 1549f),
    )

    private fun getListPointPosB() = listOf(
        Vector2(1080f - 46f - 364f, 476f),//1080f - 46 - 160f, 382f),
        Vector2(1080f - 46f - 416f, 534f),//1080f - 46 - 219f, 440f),
        Vector2(1080f - 46f - 474f, 592f),//1080f - 46 - 292f, 486f),
        Vector2(1080f - 46f - 534f, 650f),//1080f - 46 - 338f, 556f),
        Vector2(1080f - 46f - 574f, 707f),//1080f - 46 - 384f, 618f),
        Vector2(1080f - 46f - 626f, 765f),//1080f - 46 - 413f, 686f),
        Vector2(1080f - 46f - 678f, 823f),//1080f - 46 - 459f, 754f),
        Vector2(1080f - 46f - 669f, 896f),//1080f - 46 - 517f, 822f),
        Vector2(1080f - 46f - 610f, 948f),//1080f - 46 - 575f, 895f),
        Vector2(1080f - 46f - 528f, 992f),//1080f - 46 - 637f, 941f),
        Vector2(1080f - 46f - 493f, 1050f),//1080f - 46 - 696f, 1017f),
        Vector2(1080f - 46f - 434f, 1102f),//1080f - 46 - 765f, 1085f),
        Vector2(1080f - 46f - 379f, 1167f),//1080f - 46 - 694f, 1144f),
        Vector2(1080f - 46f - 434f, 1222f),//1080f - 46 - 694f, 1232f),
        Vector2(1080f - 46f - 488f, 1278f),//1080f - 46 - 614f, 1255f),
        Vector2(1080f - 46f - 533f, 1328f),//1080f - 46 - 579f, 1323f),
        Vector2(1080f - 46f - 598f, 1389f),//1080f - 46 - 614f, 1391f),
        Vector2(1080f - 46f - 663f, 1454f),//1080f - 46 - 563f, 1446f),
        Vector2(1080f - 46f - 608f, 1521f),//1080f - 46 - 517f, 1523f),
        Vector2(1080f - 46f - 538f, 1549f),//1080f - 46 - 517f, 1581f),
    )

    inner class GameUIHandler() {
        var pointCount = 0

        fun setButtonAction(block: Block) {
            blockState = block
        }

        //fun setButtonTexture(texture: TextureRegion) {}

        fun showShakeElementA() {
            btnState.enable()
            btnState.drawable = TextureRegionDrawable(gdxGame.assetsAll.shake)
            aEggPanelA.animShow(0.3f)
        }

        fun shake(block: Block) {
            btnState.disable()
            btnState.animHide(0.2f)
            aEggPanelA.shakeEgg {
                pointCount = it
                block()
            }
        }

        fun moveA(block: Block) {
            aEggPanelA.animHide(0.3f)
            btnState.drawable = TextureRegionDrawable(gdxGame.assetsAll.moving)
            btnState.animShow(0.3f)

            pointCount = if (pointCount > pointCountA) pointCountA else pointCount
            log("pointCount = $pointCount")

            imgA.addAction(
                Acts.sequence(
                    Acts.repeat(pointCount, Acts.sequence(
                        Acts.run {
                            // Оновлюємо значення тут
                            val targetW = imgA.width - personageScaleCoff
                            val targetH = imgA.height - personageScaleCoff

                            val targetPointIndex = listPointA.size - pointCountA
                            pointCountA -= 1

                            // Створюємо тимчасовий екшен зміни розміру і додаємо його актору
                            // Це спрацює, бо цей код виконується ПІД ЧАС анімації
                            imgA.addAction(Acts.sequence(
                                Acts.run { gdxGame.soundUtil.apply { play(step) } },
                                Acts.parallel(
                                    Acts.sizeTo(targetW, targetH, 0.5f, Interpolation.swingIn),
                                    Acts.moveTo(listPointA[targetPointIndex].x - (targetW / 2f) + 23f, listPointA[targetPointIndex].y, 0.5f)
                                )
                            ))

                        },
                        Acts.delay(0.5f)
                    )),
                    Acts.delay(0.7f),
                    Acts.run {
                        if (pointCountA <= 0) {
                            log("FINISH")
                            animHide { gdxGame.navigationManager.navigate(WinScreen::class.java.name) }
                        }

                        block()
                    }
                )
            )
        }

        fun showShakeElementB() {
            btnState.drawable = TextureRegionDrawable(gdxGame.assetsAll.ai_turn)
            aEggPanelB.animShow(0.3f)
        }

        fun shakeAI(block: Block) {
            aEggPanelB.shakeEgg {
                pointCount = it
                block()
            }
        }

        fun moveB(block: Block) {
            aEggPanelB.animHide(0.3f)
            btnState.drawable = TextureRegionDrawable(gdxGame.assetsAll.moving)
            btnState.animShow(0.3f)

            pointCount = if (pointCount > pointCountB) pointCountB else pointCount
            log("pointCount = $pointCount")

            imgB.addAction(
                Acts.sequence(
                    Acts.repeat(pointCount, Acts.sequence(
                        Acts.run {
                            // Оновлюємо значення тут
                            val targetW = imgB.width - personageScaleCoff
                            val targetH = imgB.height - personageScaleCoff

                            val targetPointIndex = listPointB.size - pointCountB
                            pointCountB -= 1

                            // Створюємо тимчасовий екшен зміни розміру і додаємо його актору
                            // Це спрацює, бо цей код виконується ПІД ЧАС анімації
                            imgB.addAction(Acts.sequence(
                                Acts.run { gdxGame.soundUtil.apply { play(step) } },
                                Acts.parallel(
                                    Acts.sizeTo(targetW, targetH, 0.5f, Interpolation.swingIn),
                                    Acts.moveTo(listPointB[targetPointIndex].x - (targetW / 2f) + 23f, listPointB[targetPointIndex].y, 0.5f)
                                )
                            ))
                        },
                        Acts.delay(0.5f)
                    )),
                    Acts.delay(0.7f),
                    Acts.run {
                        if (pointCountB <= 0) {
                            log("FINISH")
                            animHide { gdxGame.navigationManager.navigate(LoseScreen::class.java.name) }
                        }

                        block()
                    }
                )
            )
        }


    }

}