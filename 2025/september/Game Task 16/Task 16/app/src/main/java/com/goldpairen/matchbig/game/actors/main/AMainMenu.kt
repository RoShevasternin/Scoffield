package com.goldpairen.matchbig.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.goldpairen.matchbig.game.actors.button.AButton
import com.goldpairen.matchbig.game.screens.GameScreen
import com.goldpairen.matchbig.game.screens.MenuScreen
import com.goldpairen.matchbig.game.screens.RulesScreen
import com.goldpairen.matchbig.game.screens.SettingsScreen
import com.goldpairen.matchbig.game.utils.*
import com.goldpairen.matchbig.game.utils.actor.animDelay
import com.goldpairen.matchbig.game.utils.actor.animHide
import com.goldpairen.matchbig.game.utils.actor.animShow
import com.goldpairen.matchbig.game.utils.actor.setBounds
import com.goldpairen.matchbig.game.utils.actor.setOnClickListener
import com.goldpairen.matchbig.game.utils.advanced.AdvancedMainGroup

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

//    private val listType = listOf(
//        AButton.Type.Play,
//        AButton.Type.Rules,
//        AButton.Type.Exit,
//    )

    private val imgMenu = Image(gdxGame.assetsAll.MENU)
    private val listBtn = List(3) { Actor() }

    override fun addActorsOnGroup() {
        color.a = 0f

        addActor(imgMenu)
        imgMenu.setBounds(126f, 429f, 828f, 1062f)

        addBtnS()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnS() {
        val listNames = listOf(
            GameScreen::class.java.name,
            SettingsScreen::class.java.name,
            RulesScreen::class.java.name,
        )

        var ny = 1034f
        listBtn.forEachIndexed { index, btn ->
            addActor(btn)
            btn.setBounds(283f, ny, 537f, 149f)
            ny -= 68 + 149

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