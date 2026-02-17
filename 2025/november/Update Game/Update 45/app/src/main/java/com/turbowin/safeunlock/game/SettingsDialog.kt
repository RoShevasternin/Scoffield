package com.turbowin.safeunlock.game

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turbowin.safeunlock.R
import kotlinx.coroutines.launch

@Composable
fun SettingsDialog(
    onCloseClick: () -> Unit = {},
) {

    val interactionSource = remember { MutableInteractionSource() }

    BackHandler(true) {
        onCloseClick()
    }

    Dialog(
        onDismissRequest = {
            onCloseClick()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x99000000))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onCloseClick()
                },
            color = Color.Transparent
        ) {

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.bg_app_045),
                contentDescription = "",
                contentScale = ContentScale.FillHeight
            )

            val context = LocalContext.current
            val dataStore = remember { AppDataStore(context) }
            val scope = rememberCoroutineScope()

            val musicEnabled = dataStore.musicFlow().collectAsStateWithLifecycle(true)
            val soundEnabled = dataStore.effectsFlow().collectAsStateWithLifecycle(true)

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                TopBar(
                    startIconResId = R.drawable.img_btn_back_045,
                    endIconResId = R.drawable.empty,
                    onStartClick = onCloseClick,
                    title = "OPTIONS",
                    onEndClick = {}
                )


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    RichText(
                        text = "MUSIC:",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                        ),
                        textAlign = TextAlign.Start,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        val enabled = musicEnabled.value
                        Image(
                            painter = painterResource(
                                R.drawable.img_btn_music_off_045
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .size(52.dp)
                                .alpha(
                                    if (!enabled) 1f else 0.5f
                                )
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                ) {
                                    scope.launch {
                                        dataStore.enableMusic(false)
                                    }
                                }
                        )
                        Image(
                            painter = painterResource(
                                R.drawable.img_btn_music_on_045
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .size(52.dp)
                                .alpha(
                                    if (enabled) 1f else 0.5f
                                )
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                ) {
                                    scope.launch {
                                        dataStore.enableMusic(true)
                                    }
                                }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    RichText(
                        text = "SOUND:",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                        ),
                        textAlign = TextAlign.Start,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        val enabled = soundEnabled.value

                        Image(
                            painter = painterResource(
                                R.drawable.img_btn_fx_off_045
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .size(52.dp)
                                .alpha(
                                    if (!enabled) 1f else 0.5f
                                )
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                ) {
                                    scope.launch {
                                        dataStore.enableEffects(false)
                                    }
                                }
                        )
                        Image(
                            painter = painterResource(
                                R.drawable.img_btn_fx_on_045
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .size(52.dp)
                                .alpha(
                                    if (enabled) 1f else 0.5f
                                )
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                ) {
                                    scope.launch {
                                        dataStore.enableEffects(true)
                                    }
                                }
                        )
                    }
                }

            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.NONE,
    device = "spec:width=1080px,height=1920px,dpi=440"
)
@Composable
fun SettingsDialogPreview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        SettingsDialog(onCloseClick = {})
    }
}

