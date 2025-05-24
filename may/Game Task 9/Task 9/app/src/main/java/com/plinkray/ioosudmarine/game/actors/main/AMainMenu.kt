package com.plinkray.ioosudmarine.game.actors.main

import com.plinkray.ioosudmarine.game.actors.button.AButton
import com.plinkray.ioosudmarine.game.screens.GameScreen1
import com.plinkray.ioosudmarine.game.screens.GameScreen2
import com.plinkray.ioosudmarine.game.screens.GameScreen3
import com.plinkray.ioosudmarine.game.screens.MenuScreen
import com.plinkray.ioosudmarine.game.screens.RulesScreen
import com.plinkray.ioosudmarine.game.utils.Block
import com.plinkray.ioosudmarine.game.utils.TIME_ANIM_SCREEN
import com.plinkray.ioosudmarine.game.utils.actor.animDelay
import com.plinkray.ioosudmarine.game.utils.actor.animHide
import com.plinkray.ioosudmarine.game.utils.actor.animShow
import com.plinkray.ioosudmarine.game.utils.advanced.AdvancedMainGroup
import com.plinkray.ioosudmarine.game.utils.gdxGame

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val listBtnType = listOf(
        AButton.Type.L1,
        AButton.Type.L2,
        AButton.Type.L3,
        AButton.Type.Rules,
        AButton.Type.Exit,
    )

    private val listBtn = List(5) { AButton(screen, listBtnType[it]) }

    override fun addActorsOnGroup() {
        color.a = 0f

        addListBtn()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addListBtn() {
        val listScreenName = listOf(
            GameScreen1::class.java.name,
            GameScreen2::class.java.name,
            GameScreen3::class.java.name,
            RulesScreen::class.java.name,
            "EXIT",
        )

        var ny = 1405f
        listBtn.forEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(190f, ny, 700f, 150f)

            when(index) {
                2 -> ny = 661f
                3 -> ny = 289f
                else -> ny -= 36 + 150
            }

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