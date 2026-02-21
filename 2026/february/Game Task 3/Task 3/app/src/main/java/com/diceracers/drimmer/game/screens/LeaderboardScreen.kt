package com.diceracers.drimmer.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.diceracers.drimmer.game.actors.AScrollPane
import com.diceracers.drimmer.game.actors.ATmpGroup
import com.diceracers.drimmer.game.actors.AVerticalGroup
import com.diceracers.drimmer.game.actors.button.AButton
import com.diceracers.drimmer.game.utils.Acts
import com.diceracers.drimmer.game.utils.Block
import com.diceracers.drimmer.game.utils.TIME_ANIM_SCREEN
import com.diceracers.drimmer.game.utils.actor.animDelay
import com.diceracers.drimmer.game.utils.actor.animHide
import com.diceracers.drimmer.game.utils.actor.animShow
import com.diceracers.drimmer.game.utils.advanced.AdvancedScreen
import com.diceracers.drimmer.game.utils.advanced.AdvancedStage
import com.diceracers.drimmer.game.utils.font.FontParameter
import com.diceracers.drimmer.game.utils.gdxGame

class LeaderboardScreen: AdvancedScreen() {

    // Field

    private val listName = listOf(
        "Chirpy",
        "Pecky",
        "Sunny",
        "Fluffy",
        "Pip",
        "Nugget",
        "Bubbles",
        "Cheepy",
        "Goldie",
        "Butter",
        "Sprout",
        "Wiggles",
        "Tiny",
        "Lucky",
        "Popcorn",
        "Daisy",
        "Pebble",
        "Cookie",
        "Twinkle",
        "Mochi",
    ).shuffled()
    private val listRecord = List(listName.size) { (3..99).random() }.sortedDescending()

    // Font

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font70    = fontGenerator_Inter.generateFont(parameter.setSize(70))
    private val font50    = fontGenerator_Inter.generateFont(parameter.setSize(50))

    // Actor

    private val lblRecord = Label("${gdxGame.ds_Record.flow.value}", Label.LabelStyle(font70, Color.WHITE))

    private val imgLEADERBOARD = Image(gdxGame.assetsAll.LEADERBOARD)

    private val imgCook = Image(gdxGame.assetsAll.leaderboard_a)
    private val btnMenu = AButton(this, AButton.Type.MENU)

    private val listImgCook = List(listName.size) { Image(gdxGame.assetsAll.leaderboard_b) }
    private val listLblRecord = List(listName.size) { Label("${listName[it]}: ${listRecord[it]}", Label.LabelStyle(font50, Color.WHITE)) }

    private val verticalGroup = AVerticalGroup(this, space = 23f, isWrap = true)
    private val scrollPane    = AScrollPane(verticalGroup)

    override fun show() {
        setBackBackground(gdxGame.assetsLoader.BACKGROUND2)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        root.color.a = 0f

        addImgLEADERBOARD()
        addLblRecord()
        addBtnMenu()
        //addImgA()
        addScrollPane()

        animShow()
    }

    override fun animShow(blockEnd: Block) {
        //stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    override fun animHide(blockEnd: Block) {
        stageUI.root.children.onEach { it.clearActions() }

        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd() }
    }

    // Actors ------------------------------------------------------------------------
    private fun AdvancedStage.addImgLEADERBOARD() {
        addActor(imgLEADERBOARD)
        imgLEADERBOARD.setBounds(148f, 413f, 785f, 1458f)
    }

    private fun AdvancedStage.addLblRecord() {
        addActor(lblRecord)
        lblRecord.setBounds(531f, 1687f, 32f, 64f)
        lblRecord.setAlignment(Align.center)
    }

    private fun AdvancedStage.addBtnMenu() {
        addActor(btnMenu)
        btnMenu.setBounds(369f, 65f, 342f, 115f)
        btnMenu.setOnClickListener { animHide { gdxGame.navigationManager.back() } }
    }

    private fun AdvancedStage.addImgA() {
        addActor(imgCook)
        imgCook.setBounds(275f, 1547f, 234f, 264f)

        imgCook.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(1.1f, 1.1f, 0.45f, Interpolation.sine),
            Acts.scaleTo(1.0f, 1.0f, 0.25f, Interpolation.sine),
        )))
    }

    private fun AdvancedStage.addScrollPane() {
        addActor(scrollPane)
        scrollPane.setBounds(204f, 480f, 673f, 971f)

        verticalGroup.setSize(673f, 971f)

        listImgCook.forEachIndexed { index, image ->
            val tmpGroup = ATmpGroup(this@LeaderboardScreen)
            tmpGroup.setSize(673f, 119f)

            verticalGroup.addActor(tmpGroup)
            //tmpGroup.debug()

            image.setBounds(0f, 0f, 119f, 119f)
            val lblRecord = listLblRecord[index]
            lblRecord.setBounds(136f, 20f, 537f, 61f)

            tmpGroup.addActors(image, lblRecord)
        }
    }
}