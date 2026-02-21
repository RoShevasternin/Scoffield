package com.junglesort.questern.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.junglesort.questern.game.actors.AItemLevel
import com.junglesort.questern.game.actors.ATmpGroup
import com.junglesort.questern.game.actors.button.AImageButton
import com.junglesort.questern.game.utils.*
import com.junglesort.questern.game.utils.actor.*
import com.junglesort.questern.game.utils.advanced.AdvancedScreen

class LevelScreen: AdvancedScreen() {

    private val group   = ATmpGroup(this)
    private val btnBack = AImageButton(this, AImageButton.Type.BACK)

    override fun show() {
        stageUI.root.color.a = 0f
        //setBackBackground(drawerUtil.getTexture(com.badlogic.gdx.graphics.Color.valueOf("9A433E")))
        super.show()
        animShowScreen()
    }

    override fun Group.addActorsOnStageUI() {
        group.setSize(WIDTH_UI, HEIGHT_UI)
        stageUI2.root.addActorAligned(group, HAlign.CENTER, VAlign.CENTER)
        group.apply {
            addAndFillActor(Image(gdxGame.assetsAll.LEVELS))
            addListLvl()
        }

        stageUI2.root.addBtnBack()
    }

    override fun animHideScreen(blockEnd: Block) {
        stageUI2.root.children.onEach { it.clearActions() }

        stageUI2.root.animHide(TIME_ANIM_SCREEN)
        stageUI2.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI2.root.children.onEach { it.clearActions() }

        stageUI2.root.animShow(TIME_ANIM_SCREEN)
        stageUI2.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------

    private fun Group.addBtnBack() {
        btnBack.setSize(179f, 179f)
        addActorWithConstraints(btnBack) {
            startToStartOf = this@addBtnBack
            topToTopOf     = this@addBtnBack

            marginStart = 71f
            marginTop   = 72f
        }

        btnBack.setOnClickListener {
            this@LevelScreen.animHideScreen { gdxGame.navigationManager.back() }
        }

    }

    private fun Group.addListLvl() {
        val lvl = gdxGame.ds_key.flow.value

        repeat(8) { index ->
            val item = AItemLevel(this@LevelScreen, index)
            item.setBounds(listPos[index].x, listPos[index].y, 179f, 179f)
            addActor(item)

            if (index.inc() > lvl) item.closed()

            item.setOnClickListener(gdxGame.soundUtil) {
                this@LevelScreen.animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name, LevelScreen::class.java.name) }
            }
        }
    }

    private val listPos = listOf(
        Vector2(390f, 1388f),
        Vector2(646f, 1209f),
        Vector2(414f, 1018f),
        Vector2(196f, 854f),
        Vector2(384f, 675f),
        Vector2(634f, 523f),
        Vector2(544f, 305f),
        Vector2(345f, 164f),
    )


}