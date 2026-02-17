package com.puzfortunes.twinst.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.puzfortunes.twinst.game.screens.MenuScreen
import com.puzfortunes.twinst.game.screens.PazzleScreen
import com.puzfortunes.twinst.game.screens.RulesScreen
import com.puzfortunes.twinst.game.utils.*
import com.puzfortunes.twinst.game.utils.actor.animDelay
import com.puzfortunes.twinst.game.utils.actor.animHide
import com.puzfortunes.twinst.game.utils.actor.animShow
import com.puzfortunes.twinst.game.utils.actor.setOnClickListener
import com.puzfortunes.twinst.game.utils.advanced.AdvancedMainGroup
import com.badlogic.gdx.scenes.scene2d.Actor
import com.puzfortunes.twinst.game.actors.checkbox.ACheckBox

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

//    private val listType = listOf(
//        AButton.Type.Play,
//        AButton.Type.Rules,
//        AButton.Type.Exit,
//    )

    private val imgMenu = Image(gdxGame.assetsAll.MENU)
    private val listBtn = List(2) { Actor() }

    private val musicCB   = ACheckBox(screen, ACheckBox.Type.MUS)
    private val soundCB   = ACheckBox(screen, ACheckBox.Type.SOU)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addBtnS()

        addMusicCB()
        addSoundCB()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addMusicCB() {
        addActor(musicCB)
        musicCB.apply {
            setBounds(891f, 1723f, 141f, 141f)
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

    private fun addSoundCB() {
        addActor(soundCB)
        soundCB.apply {
            setBounds(48f, 1723f, 141f, 141f)
            if (gdxGame.soundUtil.isPause) check(false)

            setOnCheckListener { gdxGame.soundUtil.isPause = it }

        }
    }

    private fun addImgMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(153f, 708f, 775f, 562f)
    }

    private fun addBtnS() {
        val listNames = listOf(
            PazzleScreen::class.java.name,
            RulesScreen::class.java.name,
        )

        var ny = 1000f
        listBtn.forEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(153f, ny, 774f, 270f)
            ny -= 59 + 270

            btn.setOnClickListener(gdxGame.soundUtil) {
                if (index == 5) gdxGame.navigationManager.exit()
                else {
                    screen.hideScreen {
                        gdxGame.navigationManager.navigate(listNames[index], screen::class.java.name)
                    }
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