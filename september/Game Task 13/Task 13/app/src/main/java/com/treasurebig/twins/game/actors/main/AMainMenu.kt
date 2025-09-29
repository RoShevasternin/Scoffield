package com.treasurebig.twins.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.treasurebig.twins.game.actors.button.AButton
import com.treasurebig.twins.game.screens.GameScreen
import com.treasurebig.twins.game.screens.MenuScreen
import com.treasurebig.twins.game.screens.RulesScreen
import com.treasurebig.twins.game.screens.SettingsScreen
import com.treasurebig.twins.game.utils.*
import com.treasurebig.twins.game.utils.actor.animDelay
import com.treasurebig.twins.game.utils.actor.animHide
import com.treasurebig.twins.game.utils.actor.animShow
import com.treasurebig.twins.game.utils.actor.setBounds
import com.treasurebig.twins.game.utils.actor.setOnClickListener
import com.treasurebig.twins.game.utils.advanced.AdvancedMainGroup

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

//    private val listType = listOf(
//        AButton.Type.Play,
//        AButton.Type.Rules,
//        AButton.Type.Exit,
//    )

    private val imgMenu = Image(gdxGame.assetsAll.menu)
    private val listBtn = List(3) { Actor() }

    override fun addActorsOnGroup() {
        color.a = 0f

        addActor(imgMenu)
        imgMenu.setBounds(755f, 201f, 434f, 797f)

        addBtnS()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnS() {
        val listNames = listOf(
            GameScreen::class.java.name,
            RulesScreen::class.java.name,
            SettingsScreen::class.java.name,
        )

        var ny = 755f
        listBtn.forEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(755f, ny, 434f, 243f)
            ny -= 34 + 243

            btn.setOnClickListener(gdxGame.soundUtil) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(listNames[index], screen::class.java.name)
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