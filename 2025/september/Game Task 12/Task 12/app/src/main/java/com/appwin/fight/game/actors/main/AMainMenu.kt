package com.appwin.fight.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.appwin.fight.game.screens.MenuScreen
import com.appwin.fight.game.screens.PazzleScreen
import com.appwin.fight.game.screens.RulesScreen
import com.appwin.fight.game.screens.SettingsScreen
import com.appwin.fight.game.utils.*
import com.appwin.fight.game.utils.actor.animDelay
import com.appwin.fight.game.utils.actor.animHide
import com.appwin.fight.game.utils.actor.animShow
import com.appwin.fight.game.utils.actor.setOnClickListener
import com.appwin.fight.game.utils.advanced.AdvancedMainGroup
import com.badlogic.gdx.scenes.scene2d.Actor

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

        addImgMenu()
        addBtnS()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(264f, 687f, 522f, 767f)
    }

    private fun addBtnS() {
        val listNames = listOf(
            PazzleScreen::class.java.name,
            RulesScreen::class.java.name,
            SettingsScreen::class.java.name,
        )

        var ny = 1066f
        listBtn.forEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(264f, ny, 551f, 166f)
            ny -= 48 + 166

            btn.setOnClickListener {
                if (index == 5) gdxGame.navigationManager.exit()
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