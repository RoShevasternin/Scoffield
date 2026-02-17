package com.order.offortute.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.order.offortute.game.screens.MenuScreen
import com.order.offortute.game.screens.RulesScreen
import com.order.offortute.game.screens.SettingsScreen
import com.order.offortute.game.utils.*
import com.order.offortute.game.utils.actor.animDelay
import com.order.offortute.game.utils.actor.animHide
import com.order.offortute.game.utils.actor.animShow
import com.order.offortute.game.utils.actor.setOnClickListener
import com.order.offortute.game.utils.advanced.AdvancedMainGroup
import com.badlogic.gdx.scenes.scene2d.Actor
import com.order.offortute.game.screens.GameScreen

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

//    private val listType = listOf(
//        AButton.Type.Play,
//        AButton.Type.Rules,
//        AButton.Type.Exit,
//    )

    private val imgMenu  = Image(gdxGame.assetsAll.menu)
    private val listBtn  = List(4) { Actor() }
    private val imgFrame = Image(gdxGame.assetsAll.FRAME)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addBtnS()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgFrame)
        imgFrame.setBounds(45f, 268f, 989f, 1384f)

        addActor(imgMenu)
        imgMenu.setBounds(261f, 558f, 558f, 804f)
    }

    private fun addBtnS() {
        val listNames = listOf(
            GameScreen::class.java.name,
            RulesScreen::class.java.name,
            SettingsScreen::class.java.name,
        )

        var ny = 1242f
        listBtn.forEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(261f, ny, 558f, 120f)
            ny -= 108 + 120

            btn.setOnClickListener {
                if (index == 3) gdxGame.navigationManager.exit()
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