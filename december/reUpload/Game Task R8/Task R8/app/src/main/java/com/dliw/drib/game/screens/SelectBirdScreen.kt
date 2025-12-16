package com.dliw.drib.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.dliw.drib.game.LibGDXGame
import com.dliw.drib.game.actors.checkbox.ACheckBox
import com.dliw.drib.game.utils.TIME_ANIM
import com.dliw.drib.game.utils.actor.animHide
import com.dliw.drib.game.utils.actor.setOnClickListener
import com.dliw.drib.game.utils.advanced.AdvancedScreen
import com.dliw.drib.game.utils.advanced.AdvancedStage
import com.dliw.drib.game.utils.region

class SelectBirdScreen(override val game: LibGDXGame): AdvancedScreen() {

    companion object {
        var indexBird = 0
            private set
    }

    private val panelImg = Image(game.allAssets.panel)
    private val play     = Actor()
    private val exit     = Actor()
    private val birdImg  = Image(game.allAssets.birds[indexBird])
    private val left     = Actor()
    private val right    = Actor()
    private val musicCB  = ACheckBox(this, ACheckBox.Static.Type.MUSIC)
    private val soundCB  = ACheckBox(this, ACheckBox.Static.Type.SOUND)


    override fun show() {
        setBackBackground(game.startAssets.BACKGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addPlay()
        addExit()
        addBird()
        addBtns()
        addMusicCB()
        addSoundCB()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActors(panelImg)
        panelImg.setBounds(302f, 83f, 1315f, 964f)
    }

    private fun AdvancedStage.addPlay() {
        addActors(play)
        play.setBounds(708f, 310f, 503f, 174f)
        play.setOnClickListener(game.soundUtil) {
            stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(CountryScreen::class.java.name, SelectBirdScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addExit() {
        addActors(exit)
        exit.setBounds(745f, 203f, 434f, 107f)
        exit.setOnClickListener(game.soundUtil) { game.navigationManager.exit() }
    }

    private fun AdvancedStage.addBird() {
        addActor(birdImg)
        birdImg.setBounds(776f, 495f, 400f, 400f)
    }

    private fun AdvancedStage.addBtns() {
        addActors(left, right)
        left.setBounds(428f, 622f, 190f, 190f)
        left.setOnClickListener(game.soundUtil) {
            indexBird = if (indexBird-1 >= 0) indexBird-1 else 2
            birdImg.drawable = TextureRegionDrawable(game.allAssets.birds[indexBird])
        }
        right.setBounds(1288f, 622f, 190f, 190f)
        right.setOnClickListener(game.soundUtil) {
            indexBird = if (indexBird+1 <= 2) indexBird+1 else 0
            birdImg.drawable = TextureRegionDrawable(game.allAssets.birds[indexBird])
        }
    }

    private fun AdvancedStage.addMusicCB() {
        addActor(musicCB)
        musicCB.apply {
            setBounds(1682f, 966f, 81f, 81f)
            if (game.musicUtil.music?.isPlaying == true) check(false)

            setOnCheckListener { isCheck ->
                if (isCheck) game.musicUtil.music?.play() else game.musicUtil.music?.pause()
            }

        }
    }

    private fun AdvancedStage.addSoundCB() {
        addActor(soundCB)
        soundCB.apply {
            setBounds(1777f, 966f, 81f, 81f)
            if (game.soundUtil.isPause.not()) check(false)

            setOnCheckListener { isCheck -> game.soundUtil.isPause = !isCheck }
        }
    }


}