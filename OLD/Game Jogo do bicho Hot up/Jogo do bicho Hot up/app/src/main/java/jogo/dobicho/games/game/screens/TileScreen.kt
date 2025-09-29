package jogo.dobicho.games.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import jogo.dobicho.games.game.LibGDXGame
import jogo.dobicho.games.game.actors.AResult
import jogo.dobicho.games.game.actors.ASplashAnimal
import jogo.dobicho.games.game.actors.ATileGroup
import jogo.dobicho.games.game.actors.ATileGroup2
import jogo.dobicho.games.game.actors.ATileGroup3
import jogo.dobicho.games.game.actors.ATileGroup4
import jogo.dobicho.games.game.actors.ATileGroup5
import jogo.dobicho.games.game.actors.ATileGroup6
import jogo.dobicho.games.game.actors.AbsTile
import jogo.dobicho.games.game.actors.checkbox.ACheckBox
import jogo.dobicho.games.game.actors.progress.ATimer
import jogo.dobicho.games.game.utils.GameColor
import jogo.dobicho.games.game.utils.TIME_ANIM_SCREEN_ALPHA
import jogo.dobicho.games.game.utils.actor.animHide
import jogo.dobicho.games.game.utils.actor.animShow
import jogo.dobicho.games.game.utils.actor.disable
import jogo.dobicho.games.game.utils.actor.enable
import jogo.dobicho.games.game.utils.actor.setOnClickListener
import jogo.dobicho.games.game.utils.advanced.AdvancedGroup
import jogo.dobicho.games.game.utils.advanced.AdvancedScreen
import jogo.dobicho.games.game.utils.advanced.AdvancedStage
import jogo.dobicho.games.game.utils.font.FontParameter
import jogo.dobicho.games.game.utils.region
import jogo.dobicho.games.util.log

private var indexer = -1

class TileScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val parameter = FontParameter()

    private val animal = ASplashAnimal(this, true)
    private val btn    = Image(game.gameAssets.BTN_MENU).apply { color.a = 0f }
    private val tile: AdvancedGroup   = listOf(
        ATileGroup(this),
        ATileGroup2(this),
        ATileGroup3(this),
        ATileGroup4(this),
        ATileGroup5(this),
        ATileGroup6(this),
    )[ if ((indexer+1) > 5) (0..5).random() else ++indexer ].apply { color.a = 0f }

    private val result    = AResult(this, fontBold.generateFont(parameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(72)))
    private val coinPanel = Image(game.gameAssets.COIN_PANEL).apply { color.a = 0f }
    private val coinLbl   = Label("0", Label.LabelStyle(fontBold.generateFont(parameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(51)), Color.WHITE)).apply { color.a = 0f }
    private val timer     = ATimer(this).apply { color.a = 0f }
    private val cbPause   = ACheckBox(this, ACheckBox.Static.Type.PAUSE)

    // Field
    private var coinCount = 0

    override fun show() {
        setBackgrounds(game.splashAssets.BACGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addAnimal()
        addBtn()
        addTileGroup()
        addCoinPanel()
        addTimer()
        addPause()
        addAndFillActor(result)
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addAnimal() {
        addAndFillActor(animal)
        animal.animAnimateAnimal()
        animal.animHideBullAndBab()
    }

    private fun AdvancedStage.addCoinPanel() {
        addActors(coinPanel, coinLbl)
        coinPanel.apply {
            setBounds(385f, 1735f, 310f, 131f)
            animShow(TIME_ANIM_SCREEN_ALPHA)
        }
        coinLbl.apply {
            setBounds(526f, 1783f, 99f, 45f)
            setAlignment(Align.center)
            animShow(TIME_ANIM_SCREEN_ALPHA)
        }
    }

    private fun AdvancedStage.addTimer() {
        addActor(timer)
        timer.apply {
            setBounds(332f, 1442f, 448f, 99f)
            animShow(TIME_ANIM_SCREEN_ALPHA) { startTimer {
                result.animShowLose(coinCount)
            } }
        }
    }

    private fun AdvancedStage.addPause() {
        addActor(cbPause)
        cbPause.apply {
            setBounds(890f, 1764f, 117f, 117f)
            animShow(TIME_ANIM_SCREEN_ALPHA)

            setOnCheckListener {
                timer.isPause = it
                if (it) tile.disable() else tile.enable()
            }
        }
    }

    private fun AdvancedStage.addTileGroup() {
        animal.addBeetween { addActor(tile) }
        tile.setBounds(50f, 569f, 980f, 800f)
        tile.animShow(TIME_ANIM_SCREEN_ALPHA)

        (tile as AbsTile).apply {
            winBlock = {
                result.animShowWin(coinCount)
            }
            pairBlock = {
                coinCount+=(5..30).random()
                coinLbl.setText("$coinCount")
            }
        }
    }

    private fun AdvancedStage.addBtn() {
        addActor(btn)
        btn.setBounds(73f, 1764f, 117f, 117f)
        btn.animShow(TIME_ANIM_SCREEN_ALPHA)
        btn.setOnClickListener(game.soundUtil) {
            animal.animToStart {}
            animal.animShowBullAndBab()
            btn.animHide(TIME_ANIM_SCREEN_ALPHA)
            coinPanel.animHide(TIME_ANIM_SCREEN_ALPHA)
            coinLbl.animHide(TIME_ANIM_SCREEN_ALPHA)
            timer.animHide(TIME_ANIM_SCREEN_ALPHA)
            cbPause.animHide(TIME_ANIM_SCREEN_ALPHA)

            tile.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
        }
    }


}