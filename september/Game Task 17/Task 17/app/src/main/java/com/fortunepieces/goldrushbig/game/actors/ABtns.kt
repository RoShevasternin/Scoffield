package com.fortunepieces.goldrushbig.game.actors

import com.fortunepieces.goldrushbig.game.actors.button.AButton
import com.fortunepieces.goldrushbig.game.actors.checkbox.ACheckBox
import com.fortunepieces.goldrushbig.game.screens.PazzleScreen
import com.fortunepieces.goldrushbig.game.utils.advanced.AdvancedGroup
import com.fortunepieces.goldrushbig.game.utils.advanced.AdvancedScreen
import com.fortunepieces.goldrushbig.game.utils.gdxGame

class ABtns(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val boxMusic = ACheckBox(screen, ACheckBox.Type.Mus)
    private val boxSound = ACheckBox(screen, ACheckBox.Type.Sound)

    private val btnPlay = AButton(screen, AButton.Type.Play)

    var playBlock = { gdxGame.navigationManager.navigate(PazzleScreen::class.java.name, screen::class.java.name) }

    override fun addActorsOnGroup() {
        addMusicCB()
        addSoundCB()

        addActor(btnPlay)
        btnPlay.setBounds(236f, 0f, 172f, 172f)
        btnPlay.setOnClickListener {
            screen.hideScreen {
                playBlock()
            }
        }
    }

    private fun addMusicCB() {
        addActor(boxMusic)
        boxMusic.apply {
            setBounds(0f, 0f, 172f, 172f)
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
        addActor(boxSound)
        boxSound.apply {
            setBounds(472f, 0f, 172f, 172f)
            if (gdxGame.soundUtil.isPause) check(false)

            setOnCheckListener { gdxGame.soundUtil.isPause = it }

        }
    }

}