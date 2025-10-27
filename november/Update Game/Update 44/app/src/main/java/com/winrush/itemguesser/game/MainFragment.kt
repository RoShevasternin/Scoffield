package com.winrush.itemguesser.game

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.winrush.itemguesser.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        lifecycle.addObserver(object : DefaultLifecycleObserver {
            private var mediaPlayer: MediaPlayer? = null
            private var musicJob: Job? = null
            private var musicEnabled: Boolean = true

            override fun onStart(owner: LifecycleOwner) {
                musicJob = lifecycleScope.launch {

                    AppDataStore(requireContext()).musicFlow()
                        .collect { enabled ->
                            musicEnabled = enabled
                            handleMusicPlayback()
                        }
                }
            }

            override fun onStop(owner: LifecycleOwner) {
                mediaPlayer?.pause()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                musicJob?.cancel()
                mediaPlayer?.release()
                mediaPlayer = null
            }

            private fun handleMusicPlayback() {
                if (musicEnabled && lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    // Release old player if it exists and is invalid
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(requireContext(), musicId()).apply {
                            isLooping = true
                        }
                    }
                    try {
                        mediaPlayer?.start()
                    } catch (e: IllegalStateException) {
                        mediaPlayer?.release()
                        mediaPlayer = null
                    }
                } else {
                    mediaPlayer?.pause()
                }
            }

        })
    }

    private fun musicId() = R.raw.music_044

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(strategy())

            setContent {
                AppTheme(darkTheme = false, dynamicColor = false) {
                    NavigationScreen()
                }
            }
        }
    }

    private fun strategy() =
        ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
}
