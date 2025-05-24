package com.plinko.castles.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.plinko.castles.game.LibGDXGame
import com.plinko.castles.game.actors.AClick
import com.plinko.castles.game.box2d.WorldUtil
import com.plinko.castles.game.box2d.bodies.BBrick
import com.plinko.castles.game.box2d.bodiesGroup.BGBorders
import com.plinko.castles.game.box2d.bodiesGroup.BGCatapulta
import com.plinko.castles.game.utils.HEIGHT_UI
import com.plinko.castles.game.utils.TIME_ANIM
import com.plinko.castles.game.utils.WIDTH_UI
import com.plinko.castles.game.utils.actor.animHide
import com.plinko.castles.game.utils.actor.animShow
import com.plinko.castles.game.utils.actor.setOnClickListener
import com.plinko.castles.game.utils.advanced.AdvancedBox2dScreen
import com.plinko.castles.game.utils.advanced.AdvancedStage
import com.plinko.castles.game.utils.region

class CatapultaGameScreen(override val game: LibGDXGame): AdvancedBox2dScreen(WorldUtil()) {

    // Actor
    private val backImg    = Image(game.allAssets.back)
    private val clickGroup = AClick(this)

    // BodyGroup
    private val bgBorders   = BGBorders(this)
    private val bgCatapulta = BGCatapulta(this)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.startAssets.CBACA.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun dispose() {
        bgBorders.destroy()
        super.dispose()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBack()
        addClickGroup()

        createBG_Borders()
        createBG_Catapulta()

        createB_Custle()

        clickGroup.showGroup()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addBack() {
        addActor(backImg)
        backImg.apply {
            setBounds(33f, 914f, 125f, 125f)
            setOnClickListener(game.soundUtil) { stageUI.root.animHide(TIME_ANIM) { game.navigationManager.back() } }
        }
    }

    private fun AdvancedStage.addClickGroup() {
        addActor(clickGroup)
        clickGroup.apply {
            setBounds(685f, 437f, 550f, 643f)

            catapultBlock = {
                bgCatapulta.coff = coff
                bgCatapulta.launch()
            }
            showBlock     = { bgCatapulta.prepare() }

        }
    }

    // ---------------------------------------------------
    // create BodyGroup
    // ---------------------------------------------------

    private fun createBG_Borders() {
        bgBorders.create(0f, 0f, WIDTH_UI, HEIGHT_UI)
    }

    private fun createBG_Catapulta() {
        bgCatapulta.create(177f, 187f, 283f, 208f)
    }

    // ---------------------------------------------------
    // create Body
    // ---------------------------------------------------

    private fun createB_Custle() {
        var nx = 1335f
        var ny = 602f
        val size  = 83
        val sizeF = 83f

        game.allAssets.castles[CatapultaMenuScreen.CASTLE_INDEX].region.split(size, size).flatten().onEachIndexed { index, region ->
            val img = BBrick(this, region)
            img.create(nx,ny,sizeF,sizeF)
            nx += size
            if(index.inc()%6==0) {
                nx = 1335f
                ny -= size
            }
        }
    }

}