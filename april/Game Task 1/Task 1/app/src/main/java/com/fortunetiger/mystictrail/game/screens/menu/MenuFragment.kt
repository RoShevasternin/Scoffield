package com.fortunetiger.mystictrail.game.screens.menu

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fortunetiger.mystictrail.R
import com.fortunetiger.mystictrail.databinding.FragmentMenuBinding
import com.fortunetiger.mystictrail.game.Disposabler
import com.fortunetiger.mystictrail.game.screens.MainActivityController
import kotlinx.coroutines.launch
import kotlin.system.exitProcess
import com.fortunetiger.mystictrail.game.Lauout.MenuKasjdj as LM

var player: MediaPlayer? = null

class MenuFragment : Fragment(), Disposabler {

    override val contrOller by lazy { MenuFragmentController(this) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        player?.let {
            it.isLooping = true
            it.start()
        }

        contrOller.coroutine.launch {
            with(viewportFit) {
                initialize()
                setUpPlayButton()
                setUpExitButton()
            }
        }

        with(binding) {
            playButton.setOnClickListener {
                MainActivityController.navConKksakdkatroller.navigate(R.id.gameFragment)
            }
            exitButton.setOnClickListener {
                requireActivity().finishAndRemoveTask()
                exitProcess(0)
            }
        }
    }

    private fun FitViewport.setUpPlayButton() {
        setBoundsLASdksad(binding.playButton, LM.PLAY_X, LM.PLAY_Y, LM.PLAY_W, LM.PLAY_H)
    }


    override fun onDetach() {
        super.onDetach()
        dispose()
    }

    lateinit var binding: FragmentMenuBinding


    val viewportFit by lazy { FitViewport(binding.fitViewport) }


    private fun FitViewport.setUpExitButton() {
        setBoundsLASdksad(binding.exitButton, LM.KSakd, LM.EXIT_Y, LM.EXIT_W, LM.EXIT_H)
    }

}