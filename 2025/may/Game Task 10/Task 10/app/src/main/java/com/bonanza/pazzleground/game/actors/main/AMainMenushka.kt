package com.bonanza.pazzleground.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.bonanza.pazzleground.game.actors.button.AButton
import com.bonanza.pazzleground.game.actors.checkbox.ACheckBox
import com.bonanza.pazzleground.game.screens.MenushkaScreen
import com.bonanza.pazzleground.game.screens.PazzleScreen
import com.bonanza.pazzleground.game.screens.RulesScreen
import com.bonanza.pazzleground.game.utils.*
import com.bonanza.pazzleground.game.utils.actor.animDelay
import com.bonanza.pazzleground.game.utils.actor.animHide
import com.bonanza.pazzleground.game.utils.actor.animShow
import com.bonanza.pazzleground.game.utils.actor.setBounds
import com.bonanza.pazzleground.game.utils.advanced.AdvancedMainGroup

class AMainMenushka(override val screen: MenushkaScreen): AdvancedMainGroup() {

    private val listType = listOf(
        AButton.Type.Play,
        AButton.Type.Rules,
        AButton.Type.Exit,
    )

    private val imgPOZ = Image(gdxGame.assetsAll.ROZOVI)
    private val listBtn = List(3) { AButton(screen, listType[it]) }

    private val musicCB   = ACheckBox(screen, ACheckBox.Type.MUSIC)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgWelcome()
        addBtnS()
        addMusicCB()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgWelcome() {
        addActor(imgPOZ)
        imgPOZ.setBounds(-25f, 90f, 807f, 977f)
    }

    private fun addBtnS() {
        val listNames = listOf(
            PazzleScreen::class.java.name,
            RulesScreen::class.java.name,
        )

        var ny = 1296f
        listBtn.forEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(628f, ny, 346f, 166f)
            ny -= 27 + 166

            btn.setOnClickListener {
                if (index == 2) gdxGame.navigationManager.exit()
                else {
                    screen.hideScreen {
                        gdxGame.navigationManager.navigate(listNames[index], screen::class.java.name)
                    }
                }
            }
        }
    }

    private fun addMusicCB() {
        addActor(musicCB)
        musicCB.apply {
            setBounds(462f, 1703f, 156f, 162f)
            if (gdxGame.musicUtil.currentMusic?.isPlaying == false) check(false)

            setOnCheckListener {
                if (it) {
                    gdxGame.musicUtil.currentMusic?.pause()
                } else {
                    gdxGame.musicUtil.currentMusic?.play()
                }
            }

        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}