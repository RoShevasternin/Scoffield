package com.piriamidone.twispinton.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.piriamidone.twispinton.game.LibGDXGame
import com.piriamidone.twispinton.game.actors.checkbox.ACheckBox
import com.piriamidone.twispinton.game.utils.actor.setBounds
import com.piriamidone.twispinton.game.utils.actor.setOnClickListener
import com.piriamidone.twispinton.game.utils.advanced.AdvancedScreen
import com.piriamidone.twispinton.game.utils.advanced.AdvancedStage
import com.piriamidone.twispinton.game.utils.region

class LevelScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var STATIC_LEVEL = 0
    }

    private val levelImg = Image(game.alllAssets.level)
    private val musicCB  = ACheckBox(this, ACheckBox.Static.Type.MUSIC)

    override fun show() {
        setBackgrounds(game.loadAssets.background.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addLevel()
        addBackActor()
        addMusicCB()
        addBtns()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addLevel() {
        addActor(levelImg)
        levelImg.setBounds(0f, 0f, 985f, 1833f)
    }

    private fun AdvancedStage.addBackActor() {
        val back = Actor()
        addActor(back)
        back.apply {
            setBounds(122f, 1671f, 156f, 162f)
            setOnClickListener(game.soundUtil) { animHideScreen { game.navigationManager.back() } }
        }
    }

    private fun AdvancedStage.addMusicCB() {
        addActor(musicCB)
        musicCB.apply {
            setBounds(803f, 1671f, 156f, 162f)
            if (game.musicUtil.music?.isPlaying == false) check(false)

            setOnCheckListener { isCheck ->
                if (isCheck) game.musicUtil.music?.pause() else game.musicUtil.music?.play()
            }

        }
    }

    private fun AdvancedStage.addBtns() {
        listOf(
            Vector2(90f, 1244f),
            Vector2(657f, 1217f),
            Vector2(274f, 882f),
            Vector2(741f, 681f),
        ).onEachIndexed { index, pos ->
            val a = Actor()
            addActor(a)
            a.setBounds(pos, Vector2(244f, 253f))

            a.setOnClickListener(game.soundUtil) {
                STATIC_LEVEL = index

                animHideScreen {
                    game.navigationManager.navigate(PuzzleScreen::class.java.name, LevelScreen::class.java.name)
                }
            }
        }
    }


}