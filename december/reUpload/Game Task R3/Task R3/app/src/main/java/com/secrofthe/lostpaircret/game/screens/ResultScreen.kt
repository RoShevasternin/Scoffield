package com.secrofthe.lostpaircret.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.secrofthe.lostpaircret.game.LibGDXGame
import com.secrofthe.lostpaircret.game.screens.common.SettingsScreen
import com.secrofthe.lostpaircret.game.screens.template_screen.MenuScreen
import com.secrofthe.lostpaircret.game.screens.template_screen.RulesScreen
import com.secrofthe.lostpaircret.game.utils.TIME_ANIM_T
import com.secrofthe.lostpaircret.game.utils.actor.animHide
import com.secrofthe.lostpaircret.game.utils.actor.setOnClickListener
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedScreen
import com.secrofthe.lostpaircret.game.utils.advanced.AdvancedStage
import com.secrofthe.lostpaircret.game.utils.region

class ResultScreen(override val game: LibGDXGame): AdvancedScreen() {

    companion object {
        var isWin = true
    }

    override fun show() {
        setBackgrounds(if (isWin) game.allAssets.BW.region else game.allAssets.BF.region)
        super.show()
        if (isWin) game.soundUtil.apply { play(good) } else game.soundUtil.apply { play(pipe_f) }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        var ny = 1071f

        listOf(
            BParaScreen::class.java.name,
            SettingsScreen::class.java.name,
            RulesScreen::class.java.name,
        ).onEach { scr ->
            addActor(Actor().apply {
                setBounds(315f, ny, 435f, 177f)
                ny -= 62f + 177f

                setOnClickListener(game.soundUtil) {
                    stageUI.root.animHide(TIME_ANIM_T) { game.navigationManager.navigate(scr, MenuScreen::class.java.name) }
                }
            })
        }

        val xBtn = Actor()
        addActor(xBtn)
        xBtn.apply {
            setBounds(783f, 395f, 276f, 276f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM_T) { game.navigationManager.back() }
            }
        }

    }

}