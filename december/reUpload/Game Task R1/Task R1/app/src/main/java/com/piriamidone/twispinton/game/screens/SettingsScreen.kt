package com.piriamidone.twispinton.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.piriamidone.twispinton.game.LibGDXGame
import com.piriamidone.twispinton.game.actors.checkbox.ACheckBox
import com.piriamidone.twispinton.game.utils.actor.setOnClickListener
import com.piriamidone.twispinton.game.utils.advanced.AdvancedScreen
import com.piriamidone.twispinton.game.utils.advanced.AdvancedStage
import com.piriamidone.twispinton.game.utils.region

class SettingsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val panelImg = Image(game.alllAssets.phara_panel)
    private val settImg  = Image(game.alllAssets.sett)

    private val musicCB = ACheckBox(this, ACheckBox.Static.Type.CBox)
    private val soundCB = ACheckBox(this, ACheckBox.Static.Type.CBox)



    override fun show() {
        setBackgrounds(game.loadAssets.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanelImg()
        addSett()
        addMusicCB()
        addSoundCB()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanelImg() {
        addActor(panelImg)
        panelImg.setBounds(0f, 0f, 966f, 1603f)

        val back = Actor()
        addActor(back)
        back.apply {
            setBounds(730f, 341f, 171f, 175f)
            setOnClickListener(game.soundUtil) { animHideScreen { game.navigationManager.back() } }
        }
    }

    private fun AdvancedStage.addSett() {
        addActor(settImg)
        settImg.setBounds(368f, 995f, 344f, 757f)
    }

    private fun AdvancedStage.addMusicCB() {
        addActor(musicCB)
        musicCB.apply {
            setBounds(342f, 1172f, 402f, 162f)
            if (game.musicUtil.music?.isPlaying == true) check(false)

            setOnCheckListener { isCheck ->
                if (isCheck) game.musicUtil.music?.play() else game.musicUtil.music?.pause()
            }

        }
    }


    private fun AdvancedStage.addSoundCB() {
        addActor(soundCB)
        soundCB.apply {
            setBounds(342f, 810f, 402f, 162f)
            if (game.soundUtil.isPause.not()) check(false)

            setOnCheckListener { isCheck -> game.soundUtil.isPause = !isCheck }
        }
    }

}