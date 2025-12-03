package com.fivematch.fivemaster.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.fivematch.fivemaster.appContext
import com.fivematch.fivemaster.game.actors.button.AButton
import com.fivematch.fivemaster.game.screens.IgraScreen
import com.fivematch.fivemaster.game.screens.MenuScreen
import com.fivematch.fivemaster.game.screens.RulesScreen
import com.fivematch.fivemaster.game.utils.Block
import com.fivematch.fivemaster.game.utils.TIME_ANIM_SCREEN
import com.fivematch.fivemaster.game.utils.actor.animDelay
import com.fivematch.fivemaster.game.utils.actor.animHide
import com.fivematch.fivemaster.game.utils.actor.animShow
import com.fivematch.fivemaster.game.utils.actor.setBounds
import com.fivematch.fivemaster.game.utils.actor.setOnClickListener
import com.fivematch.fivemaster.game.utils.advanced.AdvancedMainGroup
import com.fivematch.fivemaster.game.utils.gdxGame

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val imgText  = Image(gdxGame.assetsAll.musik)
    private val listImg  = List(3) { Actor() }

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addImages()

        val pry = AButton(screen, AButton.Type.PRY)
        addActor(pry)
        pry.setBounds(295f, 97f, 491f, 173f)
        pry.setOnClickListener {
            gdxGame.activity.webViewHelper.loadUrl(gdxGame.activity.getPryvURL(), false)
        }

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgText)
        imgText.setBounds(294f, 586f, 491f, 749f)
    }

    private fun addImages() {
        listImg.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(294f, 1162f, 491f, 173f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(IgraScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(294f, 874f, 491f, 173f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(RulesScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                2 -> {
                    image.setBounds(294f, 586f, 491f, 173f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.exit()
                        }
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