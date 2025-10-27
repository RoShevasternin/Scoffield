package com.plinko.ballwinx100.game.actors

import com.plinko.ballwinx100.game.actors.checkbox.ACheckBox
import com.plinko.ballwinx100.game.utils.advanced.AdvancedGroup
import com.plinko.ballwinx100.game.utils.advanced.AdvancedScreen

private var isMusic = true
private var isSound = true

class ASetering(override val screen: AdvancedScreen): AdvancedGroup() {

    private val musicCB = ACheckBox(screen, ACheckBox.Static.Type.MUSIC)
    private val soundCB = ACheckBox(screen, ACheckBox.Static.Type.EFFECTS)

    override fun addActorsOnGroup() {
        addActors(musicCB, soundCB)
        musicCB.apply {
            setBounds(
                0f,
                musicCB.height + 140f,
                musicCB.width,
                musicCB.height
            )
            if (isMusic) uncheck(false) else check(false)
            setOnCheckListener {
                isMusic = it.not()
                screen.game.musicUtil.music?.apply { if (isMusic) play() else pause()  }
            }
        }
        soundCB.apply {
            setBounds(
                0f,
                0f,
                soundCB.width,
                soundCB.height
            )
            if (isSound) uncheck(false) else check(false)
            setOnCheckListener {
                isSound = it.not()
                screen.game.soundUtil.isPause = isSound.not()
            }
        }
    }

    override fun getWidth(): Float {
        return musicCB.width
    }

    override fun getHeight(): Float {
        return musicCB.height + soundCB.height + 140f
    }

}