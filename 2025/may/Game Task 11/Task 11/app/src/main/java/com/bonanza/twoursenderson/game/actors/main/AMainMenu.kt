package com.bonanza.twoursenderson.game.actors.main

import com.bonanza.twoursenderson.game.actors.button.AButton
import com.bonanza.twoursenderson.game.screens.GameScreen
import com.bonanza.twoursenderson.game.screens.MenuScreen
import com.bonanza.twoursenderson.game.screens.RulesScreen
import com.bonanza.twoursenderson.game.utils.*
import com.bonanza.twoursenderson.game.utils.actor.animDelay
import com.bonanza.twoursenderson.game.utils.actor.animHide
import com.bonanza.twoursenderson.game.utils.actor.animShow
import com.bonanza.twoursenderson.game.utils.actor.setBounds
import com.bonanza.twoursenderson.game.utils.advanced.AdvancedMainGroup

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val listType = listOf(
        AButton.Type.Play,
        AButton.Type.Rules,
        AButton.Type.Exit,
    )

    private val listBtn = List(3) { AButton(screen, listType[it]) }

    override fun addActorsOnGroup() {
        color.a = 0f

        addBtnS()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnS() {
        val listNames = listOf(
            GameScreen::class.java.name,
            RulesScreen::class.java.name,
        )

        var ny = 759f
        listBtn.forEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(777f, ny, 446f, 217f)
            ny -= 50 + 217

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