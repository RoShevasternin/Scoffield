package com.hexofun.rundeo.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.hexofun.rundeo.game.LibGDXGame
import com.hexofun.rundeo.game.actors.button.AButton
import com.hexofun.rundeo.game.actors.checkbox.ACheckBox
import com.hexofun.rundeo.game.box2d.AbstractBody
import com.hexofun.rundeo.game.box2d.BodyId
import com.hexofun.rundeo.game.box2d.WorldUtil
import com.hexofun.rundeo.game.box2d.bodies.BHexagon
import com.hexofun.rundeo.game.box2d.bodies.BStar
import com.hexofun.rundeo.game.box2d.bodies.BTriangle
import com.hexofun.rundeo.game.box2d.bodiesGroup.BGBorders
import com.hexofun.rundeo.game.utils.HEIGHT_UI
import com.hexofun.rundeo.game.utils.TIME_ANIM
import com.hexofun.rundeo.game.utils.WIDTH_UI
import com.hexofun.rundeo.game.utils.actor.animHide
import com.hexofun.rundeo.game.utils.actor.setOnClickListener
import com.hexofun.rundeo.game.utils.advanced.AdvancedBox2dScreen
import com.hexofun.rundeo.game.utils.advanced.AdvancedStage
import com.hexofun.rundeo.game.utils.font.FontParameter
import com.hexofun.rundeo.game.utils.region
import com.hexofun.rundeo.game.utils.runGDX
import com.hexofun.rundeo.game.utils.toB2
import com.hexofun.rundeo.game.utils.toUI
import com.hexofun.rundeo.util.log

class HexagonGameScreen(override val game: LibGDXGame): AdvancedBox2dScreen(WorldUtil()) {

    private val fParameters = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(64)
    private val font64      = fontGenerator_Akshar_Regular.generateFont(fParameters)

    private val backgrounds = listOf(
        game.startAssets.BACKGROUND,
        game.allAssets._2,
        game.allAssets._3,
        game.allAssets._4,
    )

    // Actor
    private val aMenuBtn   = AButton(this, AButton.Static.Type.MENU)
    private val aPauseCBox = ACheckBox(this, ACheckBox.Static.Type.PAUSE)
    private val upImg      = Image(game.allAssets.up)
    private val rotateImg  = Image(game.allAssets.rotate)
    private val lebal      = Label("0", Label.LabelStyle(font64, Color.BLACK))

    // Body
    private val bHexagon      = BHexagon(this)
    private val bTriangleList = List(3) { BTriangle(this) }
    private val bStarList     = List(3) { BStar(this) }

    // BodyGroup
    private val bgBorders  = BGBorders(this)

    // Field
    private var counter = 0

    override fun show() {
        setBackBackground(backgrounds[HexagonMapScreen.indexMap].region)
        super.show()
    }

    override fun dispose() {
        bgBorders.destroy()
        super.dispose()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createBG_Borders()
        createB_Hexagon()
        createB_Triangle()
        createB_Star()

        addMenu()
        addPause()
        addUpImg()
        addRotateImg()
        addLabel()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addMenu() {
        addActor(aMenuBtn)
        aMenuBtn.apply {
            setBounds(1550f, 903f, 130f, 133f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() }
            }
        }
    }

    private fun AdvancedStage.addPause() {
        addActor(aPauseCBox)
        aPauseCBox.apply {
            setBounds(1736f, 903f, 130f, 133f)
            setOnCheckListener { isCheck ->
                isWorldPause = isCheck
            }
        }
    }

    private fun AdvancedStage.addUpImg() {
        addActor(upImg)
        upImg.apply {
            setBounds(1753f, 25f, 114f, 117f)
            setOnClickListener(game.soundUtil) {
                bHexagon.body?.apply {
                    applyLinearImpulse(Vector2(0f, 200f), worldCenter, true)
                }
            }
        }
    }

    private fun AdvancedStage.addRotateImg() {
        addActor(rotateImg)
        rotateImg.apply {
            setBounds(46f, 25f, 114f, 117f)
            setOnClickListener(game.soundUtil) {
                bHexagon.body?.applyAngularImpulse(50f,true)
            }
        }
    }

    private fun AdvancedStage.addLabel() {
        val clp = Image(game.allAssets.panel)
        addActor(clp)
        clp.setBounds(736f, 10f, 378f, 94f)
        addActor(lebal)
        lebal.setBounds(925f, 21f, 168f, 70f)
    }

    // ---------------------------------------------------
    // create BodyGroup
    // ---------------------------------------------------

    private fun createBG_Borders() {
        bgBorders.create(0f, 0f, WIDTH_UI, HEIGHT_UI)
    }

    // ---------------------------------------------------
    // create Body
    // ---------------------------------------------------

    private fun createB_Hexagon() {
        bHexagon.create(874f, 656f, 171f, 171f)

        bHexagon.beginContactBlockArray.add(AbstractBody.ContactBlock {
            when(it.id) {
                BodyId.BORDERS      -> {
                    game.soundUtil.apply { play(cartoon_jump) }

                }
                BodyId.TRIANGLE      -> {
                    game.soundUtil.apply { play(cartoon_jump) }

                    val velocity  = bHexagon.body?.linearVelocity ?: Vector2()
                    val direction = velocity.nor()
                    val impulse   = direction.scl(500f)
                    bHexagon.body?.apply { applyLinearImpulse(impulse, worldCenter, true) }
                }
                BodyId.FunWorld.STAR -> {
                    runGDX {
                        game.soundUtil.apply { play(star) }

                        it.body?.setTransform(getStarPos().toB2, 0f)
                        counter++
                        lebal.setText(counter)
                    }
                }
            }
        })
    }

    private fun createB_Triangle() {
        var nx = 283f
        bTriangleList.onEach { triangle ->
            triangle.create(nx, 146f, 238f, 205f)
            nx += 316+205
        }
    }

    private val starSize = Vector2(163f, 153f)

    private fun createB_Star() {
        bStarList.onEach { triangle ->
            triangle.create(getStarPos(), starSize)
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private val tmpVectir = Vector2()

    private fun getStarPos() = tmpVectir.set(
        (80..1750).random().toFloat(),
        (360..930).random().toFloat(),
    )

}