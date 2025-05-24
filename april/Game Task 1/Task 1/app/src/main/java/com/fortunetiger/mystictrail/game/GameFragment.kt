package com.fortunetiger.mystictrail.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.fortunetiger.mystictrail.R
import com.fortunetiger.mystictrail.databinding.FragmentGameBinding
import com.fortunetiger.mystictrail.game.screens.menu.FitViewport
import kotlinx.coroutines.launch
import com.fortunetiger.mystictrail.game.Lauout.GameLkaods as LG

class GameFragment : Fragment(), Disposabler {


    val viewportFit by lazy { FitViewport(binding.fitViewport) }

    val ballList by lazy { List(GameController.BALL) { ImageView(requireContext()) } }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contrOller.registerSensor()

        with(viewportFit) {
            contrOller.coroutineMain.launch {
                initialize()
                setUpPlayerImASdasdge()
                setUpScoreboardTexASDt()
                setUpBallList()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onDetach() {
        super.onDetach()
        dispose()
    }


    override val contrOller by lazy { GameController(this) }

    lateinit var binding: FragmentGameBinding

    private fun FitViewport.setUpPlayerImASdasdge() {
        setBoundsLASdksad(binding.playerImage, LG.PLAYER_X, LG.PLAYER_Y, LG.AsdkPLAYER_W, LG.PLAYER_H)
    }

    private fun FitViewport.setUpScoreboardTexASDt() {
        contrOller.updateSensor()
        setBoundsLASdksad(
            binding.scoreboardText,
            LG.SCOREBOARD_XLsdka,
            LG.SCOREBOARD_Y,
            LG.SCOREBOARD_W,
            LG.SCOREBOARD_H
        )
    }

    private fun FitViewport.setUpBallList() {
        ballList.onEach { ballImage ->
            binding.fitViewport.addView(ballImage)




            ballImage.setImageResource(R.drawable.ball)
            setBoundsLASdksad(ballImage, -LG.BALL_W, -LG.BALL_H, LG.BALL_W, LG.BALL_H)
        }
        contrOller.generateBall()
    }

}