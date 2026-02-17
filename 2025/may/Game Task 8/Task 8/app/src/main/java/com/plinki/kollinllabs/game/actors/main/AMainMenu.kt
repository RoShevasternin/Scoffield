package com.plinki.kollinllabs.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.plinki.kollinllabs.game.actors.button.AButton
import com.plinki.kollinllabs.game.screens.GameScreen
import com.plinki.kollinllabs.game.screens.MenuScreen
import com.plinki.kollinllabs.game.screens.RulesScreen
import com.plinki.kollinllabs.game.utils.Acts
import com.plinki.kollinllabs.game.utils.Block
import com.plinki.kollinllabs.game.utils.TIME_ANIM_SCREEN
import com.plinki.kollinllabs.game.utils.actor.animDelay
import com.plinki.kollinllabs.game.utils.actor.animHide
import com.plinki.kollinllabs.game.utils.actor.animShow
import com.plinki.kollinllabs.game.utils.actor.setBounds
import com.plinki.kollinllabs.game.utils.advanced.AdvancedMainGroup
import com.plinki.kollinllabs.game.utils.gdxGame

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val listBtnType = listOf(
        AButton.Type.Play,
        AButton.Type.Rules,
        AButton.Type.Exit,
    )

    private val listBtn = List(3) { AButton(screen, listBtnType[it]) }

    override fun addActorsOnGroup() {
        color.a = 0f

        addListBtn()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addListBtn() {
        val listScreenName = listOf(
            GameScreen::class.java.name,
            RulesScreen::class.java.name,
            "EXIT",
        )

        var ny = 1085f
        listBtn.forEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(190f, ny, 700f, 150f)
            ny -= 80 + 150

            btn.setOnClickListener {
                if (listScreenName[index] == "EXIT") screen.hideScreen { gdxGame.navigationManager.exit() }
                else screen.hideScreen {
                    gdxGame.navigationManager.navigate(listScreenName[index], screen::class.java.name)
                }
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }

        blockEnd.invoke()
    }

    override fun animHideMain(blockEnd: Block) {
        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }

        blockEnd.invoke()
    }

}