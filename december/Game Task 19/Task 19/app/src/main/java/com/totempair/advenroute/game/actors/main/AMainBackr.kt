package com.totempair.advenroute.game.actors.main

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.totempair.advenroute.game.actors.button.AButton
import com.totempair.advenroute.game.actors.checkbox.ACheckBox
import com.totempair.advenroute.game.actors.checkbox.ACheckBoxGroup
import com.totempair.advenroute.game.screens.BackrScreen
import com.totempair.advenroute.game.screens.IgraScreen
import com.totempair.advenroute.game.screens.RulesScreen
import com.totempair.advenroute.game.utils.Block
import com.totempair.advenroute.game.utils.TIME_ANIM_SCREEN
import com.totempair.advenroute.game.utils.actor.animDelay
import com.totempair.advenroute.game.utils.actor.animHide
import com.totempair.advenroute.game.utils.actor.animShow
import com.totempair.advenroute.game.utils.actor.setOnClickListener
import com.totempair.advenroute.game.utils.advanced.AdvancedMainGroup
import com.totempair.advenroute.game.utils.gdxGame

var GLOBAL_BACKG: Texture? = null
    private set
var GLOBAL_INDEX = 0
    private set

class AMainBackr(override val screen: BackrScreen): AdvancedMainGroup() {

    private val imgChoose = Image(gdxGame.assetsAll.choose)
    private val imgBackr  = Image(gdxGame.assetsAll.MENU)
    private val btnSelect = AButton(screen, AButton.Type.Select)
    private val listBtn   = List(3) { ACheckBox(screen, ACheckBox.Type.GGG) }

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgChoose()
        addImgBackr()
        addBtn()
        addBtnSelect()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgChoose() {
        addActor(imgChoose)
        imgChoose.setBounds(0f, 1688f, 1080f, 153f)
    }

    private fun addImgBackr() {
        addActor(imgBackr)
        imgBackr.setBounds(101f, 348f, 886f, 1240f)
    }

    private fun addBtnSelect() {
        addActor(btnSelect)
        btnSelect.setBounds(308f, 72f, 465f, 158f)

        btnSelect.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(IgraScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addBtn() {
        val cbg = ACheckBoxGroup()
        listBtn.forEachIndexed { index, box ->
            addActor(box)
            when(index) {
                0 -> {
                    box.checkBoxGroup = cbg
                    GLOBAL_BACKG = gdxGame.assetsAll.B1
                    GLOBAL_INDEX = 0
                    box.check()

                    box.setBounds(76f, 1133f, 480f, 480f)
                    box.setOnCheckListener {
                        if(it) {
                            GLOBAL_BACKG = gdxGame.assetsAll.B1
                            GLOBAL_INDEX = 0
                        }
                    }
                }
                1 -> {
                    box.checkBoxGroup = cbg
                    box.setBounds(547f, 733f, 480f, 480f)
                    box.setOnCheckListener {
                        if (it) {
                            GLOBAL_BACKG = gdxGame.assetsAll.B2
                            GLOBAL_INDEX = 1
                        }
                    }
                }
                2 -> {
                    box.checkBoxGroup = cbg
                    box.setBounds(76f, 308f, 480f, 480f)
                    box.setOnCheckListener {
                        if (it) {
                            GLOBAL_BACKG = gdxGame.assetsAll.B3
                            GLOBAL_INDEX = 2
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