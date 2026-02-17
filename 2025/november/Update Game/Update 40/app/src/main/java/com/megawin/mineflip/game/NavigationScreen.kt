package com.megawin.mineflip.game

import android.media.MediaPlayer
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.megawin.mineflip.MainActivity
import com.megawin.mineflip.R

@Composable
fun NavigationScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val onClickWithSound = rememberClickSoundEffect()
    var showSettingsDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {}
    ) { paddingValues ->

        var backgroundImageResId by remember { mutableIntStateOf(R.drawable.bg_splash_040) }
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = backgroundImageResId),
            contentDescription = "$paddingValues",
            contentScale = ContentScale.FillHeight
        )

        val graph =
            navController.createGraph(
                startDestination = Screen.Launch.routeName
                    .replace("{on_40}", "true")
            ) {
                composable(
                    route = Screen.Launch.routeName,
                    arguments = listOf(
                        navArgument("on_40") {
                            type = NavType.BoolType
                        }
                    )
                ) {
                    val on = it.arguments?.getBoolean("on_40") ?: true

                    StartScreen(
                        on,
                        onItemClick = { routeName ->
                            onClickWithSound {
                                navController.navigate(routeName)
                            }
                        },
                        onBackgroundChanged = {
                            backgroundImageResId = R.drawable.bg_app_040
                        },
                        onSettingsClick = {
                            onClickWithSound {
                                showSettingsDialog = true
                            }
                        },
                        onPrivacyClick = {
                            onClickWithSound {
                                val activity = context as MainActivity
                                activity.frgmnt.shouldCloseWebViewOnBack = true
                                activity.frgmnt.firstOpen = false
                                activity.frgmnt.showAndOpenUrl(
                                    context.getString(R.string.B3CaXE)
                                )
                                activity.frgmnt.showWebView()
                                activity.frgmnt.enableOnBackPressed()
                                showSettingsDialog = false
                            }
                        },
                        onExitClick = {
                            onClickWithSound {
                                (context as MainActivity).exit()
                            }
                        }
                    )
                }
                composable(
                    route = Screen.Game.routeName,
                ) {
                    BackHandler(true) {}
                    GameScreen(
                        onBackClick = {
                            onClickWithSound {
                                navController.navigate(
                                    Screen.Launch.routeName
                                        .replace("{on_40}", "false")
                                )
                            }
                        },
                        onSettingsClick = {
                            onClickWithSound {
                                showSettingsDialog = true
                            }
                        },
                        onRestartClick = {
                            onClickWithSound {
                                navController.navigate(
                                    Screen.Game.routeName
                                )
                            }
                        }
                    )
                }
            }
        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.background(Color.Transparent),
        )

        if (showSettingsDialog) {
            SettingsDialog(
                onCloseClick = {
                    onClickWithSound {
                        showSettingsDialog = false
                    }
                }
            )
        }

    }
}

sealed class Screen(val routeName: String) {
    data object Launch: Screen("launch?on={on_40}")
    data object Game: Screen("game")
}

@Composable
fun rememberClickSoundEffect(): ((() -> Unit) -> Unit) {
    val context = LocalContext.current
    val dataStore = remember { AppDataStore(context) }
    val effectsEnabled = dataStore.effectsFlow().collectAsStateWithLifecycle(true)

    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.fx_040)?.apply {
            setOnCompletionListener {
                it.seekTo(0)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
        }
    }

    return remember {
        { onClick: () -> Unit ->
            try {
                if (effectsEnabled.value) mediaPlayer?.start()
            } catch (_: Exception) {

            }
            onClick()
        }
    }
}
