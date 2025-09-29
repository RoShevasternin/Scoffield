package lucky.jogodobicho.fan.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lucky.jogodobicho.fan.game.LibGDXGame
import lucky.jogodobicho.fan.game.actors.AResultGroup
import lucky.jogodobicho.fan.game.actors.ATimer
import lucky.jogodobicho.fan.game.actors.background.ABackgroundGame
import lucky.jogodobicho.fan.game.actors.playable.APlayGround3
import lucky.jogodobicho.fan.game.actors.playable.APlayGround4
import lucky.jogodobicho.fan.game.utils.Layout
import lucky.jogodobicho.fan.game.utils.Time_ANIMATION
import lucky.jogodobicho.fan.game.utils.actor.animShow
import lucky.jogodobicho.fan.game.utils.actor.animShow_Suspend
import lucky.jogodobicho.fan.game.utils.actor.disable
import lucky.jogodobicho.fan.game.utils.actor.enable
import lucky.jogodobicho.fan.game.utils.actor.setBounds
import lucky.jogodobicho.fan.game.utils.actor.setOnClickListener
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedGroup
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedScreen
import lucky.jogodobicho.fan.game.utils.advanced.AdvancedStage
import lucky.jogodobicho.fan.game.utils.region
import lucky.jogodobicho.fan.game.utils.runGDX
import lucky.jogodobicho.fan.util.log
import java.util.Random

class A44Screen(override val game: LibGDXGame) : AdvancedScreen() {

    private val backgroundGame = ABackgroundGame(this).apply { color.a = 0f }
    private val imgBACKmenu    = Image(game.gameAssets.THREE_POINTS)
    private val imgSETTmenu    = Image(game.gameAssets.SIX_TEETH)
    private val timer          = ATimer(this)
    private val chooseRightImg = Image(game.gameAssets.CHOOSE_RIGHT)
    private val checkerjaotImg = Image(game.gameAssets.CHECK_MARK).apply { color.a = 0f }
    private val playGround     = if (Random().nextBoolean()) APlayGround3(this) else APlayGround4(this)
    private val result         = AResultGroup(this).apply { color.a = 0f }

    override fun show() {
        setBackgrounds(game.splashAssets.MAIN_BACKGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addAndFillActor(backgroundGame)
                backgroundGame.tmpGroup.apply {
                    addChooseRightImg()
                    addPlayGround()
                    addTimer()
                    addCheckImg()

                    addResult()

                    addActors(
                        imgBACKmenu.apply {
                            setBounds(Layout.General.three_points)
                            setOnClickListener(game.soundUtil) { animHideScreen { game.navigationManager.back() } }
                        },
                        imgSETTmenu.apply {
                            setBounds(Layout.General.six_teeth)
                            setOnClickListener(game.soundUtil) { navigateGo(A33Screen::class.java.name) }
                        }
                    )
                }
            }
            delay(500)
            backgroundGame.animShow_Suspend(Time_ANIMATION)
            checkerjaotImg.animShow_Suspend(Time_ANIMATION*3)

            runGDX {
                timer.startTimer {
                    result.apply {
                        enable()
                        setResult(false)
                        animShow(Time_ANIMATION)
                        screen.game.soundUtil.apply { play(violin_lose) }
                    }
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedGroup.addTimer() {
        addActor(timer)
        timer.setBounds(338f, 1599f, 404f, 96f)
    }

    private fun AdvancedGroup.addChooseRightImg() {
        addActor(chooseRightImg)
        chooseRightImg.setBounds(21f, 357f, 1039f, 1206f)
    }

    private fun AdvancedGroup.addCheckImg() {
        addActor(checkerjaotImg)
        checkerjaotImg.setBounds(435f, 323f, 240f, 240f)
        checkerjaotImg.setOnClickListener(game.soundUtil) {
            playGround.checkIsWin(
                win = {
                    result.setResult(true)
                    screen.game.soundUtil.apply { play(success_fanfare) } },
                lose = {
                    result.setResult(false)
                    screen.game.soundUtil.apply { play(violin_lose) }},
            )
            result.apply {
                enable()
                animShow(Time_ANIMATION)
            }
        }
    }

    private fun AdvancedGroup.addPlayGround() {
        addAndFillActor(playGround)
    }

    private fun AdvancedGroup.addResult() {
        addAndFillActor(result)
        result.disable()
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun navigateGo(id: String) {
        animHideScreen { game.navigationManager.navigate(id, this::class.java.name) }
    }


}