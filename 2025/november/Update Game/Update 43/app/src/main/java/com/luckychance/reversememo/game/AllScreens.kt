package com.luckychance.reversememo.game

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luckychance.reversememo.R
import kotlinx.coroutines.delay

@Composable
fun StartScreen(
    enabled: Boolean = true,
    onItemClick: (routeName: String) -> Unit,
    onBackgroundChanged: (Int) -> Unit = {},
    onSettingsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onExitClick: () -> Unit = {}
) {

    var splashOn by remember { mutableStateOf(enabled) }
    val validate by remember { mutableStateOf(true) }
    var buttonEnabled by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    if (splashOn && validate) {
        LaunchedEffect(Unit) {
            delay(5000L)
            buttonEnabled = true
            splashOn = false
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.BottomCenter
        ) {


        }

    } else {
        onBackgroundChanged(R.drawable.bg_app_043)
        HomeScreen(
            onItemClick,
            onSettingsClick,
            onPrivacyClick,
            onExitClick
        )
    }
}

@Composable
fun HomeScreen(
    onItemClick: (routeName: String) -> Unit,
    onSettingsClick: () -> Unit,
    onPrivacyClick: () -> Unit = {},
    onExitClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    TopBar(
        endIconResId = R.drawable.img_btn_settings_043,
        title = "",
        onEndClick = onSettingsClick
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        onItemClick(Screen.Game.routeName)
                    }
                ),
            painter = painterResource(id = R.drawable.img_btn_play_043),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            onPrivacyClick()
                        }
                    ),
                painter = painterResource(id = R.drawable.img_btn_info_043),
                contentDescription = "",
                contentScale = ContentScale.FillHeight
            )

            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            onExitClick()
                        }
                    ),
                painter = painterResource(id = R.drawable.img_btn_exit_043),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
        }
    }

}

@Composable
fun TopBar(
    startIconResId: Int = R.drawable.empty,
    endIconResId: Int = R.drawable.empty,
    title: String = "",
    titleSize: TextUnit = 36.sp,
    onStartClick: () -> Unit = {},
    onEndClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .aspectRatio(1f)
                .padding(horizontal = 16.dp)
                .align(Alignment.TopStart)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) {
                    onStartClick()
                },
            painter = painterResource(id = startIconResId),
            contentDescription = "",
        )
        RichText(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = titleSize
            ),
            borderColor = MaterialTheme.colorScheme.secondary
        )
        Image(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .aspectRatio(1f)
                .padding(horizontal = 16.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) {
                    onEndClick()
                }
                .align(Alignment.TopEnd),
            painter = painterResource(id = endIconResId),
            contentDescription = "",
        )
    }
}

// ---------- Main Composable ----------
@Composable
fun GameScreen(
    onSettingsClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onRestartClick: () -> Unit = {}
) {
    val allItems = (0..11).toList()
    val gridSize = 3
    val gridItemCount = gridSize * gridSize

    // Game state
    var showItems by remember { mutableStateOf(true) }
    var showAnswers by remember { mutableStateOf(false) }
    var showQuestion by remember { mutableStateOf(false) }
    var gameEnded by remember { mutableStateOf(false) }
    var gameWon by remember { mutableStateOf(false) }

    // Initialize grid
    val gameState = remember {
        val selectedItems = allItems.shuffled().take(gridItemCount)
        val missingIndex = (0 until gridItemCount).random()
        val missingItem = selectedItems[missingIndex]
        val options = (allItems - missingItem).shuffled().take(3) + missingItem

        mutableStateOf(
            GameState(
                items = selectedItems,
                missingIndex = missingIndex,
                missingItem = missingItem,
                options = options.shuffled()
            )
        )
    }

    // Reveal timer logic
    LaunchedEffect(Unit) {
        delay(3000)
        showItems = false
        delay(1500)
        showAnswers = true
        showQuestion = true
        showItems = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                startIconResId = R.drawable.img_btn_home_043,
                endIconResId = R.drawable.img_btn_settings_043,
                onStartClick = onBackClick,
                title = "",
                onEndClick = onSettingsClick
            )

            RichText(
                text = "WATCH CLOSELY AND\nREVEAL THE MISSING ITEM",
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.tertiaryContainer,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 32.sp
                ),
                borderColor = MaterialTheme.colorScheme.secondary
            )

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                val tileSize = maxWidth / gridSize

                Image(
                    painter = painterResource(id = R.drawable.img_item_field_043),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

                Box(modifier = Modifier.fillMaxSize(0.8f),
                    contentAlignment = Alignment.Center) {
                    // Background field


                    LazyVerticalGrid(
                        columns = GridCells.Fixed(gridSize),
                        modifier = Modifier.fillMaxSize().background(Color.White.copy(0.5f), RoundedCornerShape(16.dp)).align(Alignment.Center)
                    ) {
                        items(gridItemCount) { index ->
                            val item = gameState.value.items[index]
                            val isMissing = index == gameState.value.missingIndex
                            val drawableRes = when {
                                showQuestion && isMissing -> R.drawable.img_item_sign_043
                                showItems  -> getDrawableId(item)
                                else -> R.drawable.img_item_tile_043
                            }

                            Image(
                                painter = painterResource(id = drawableRes),
                                contentDescription = null,
                                modifier = Modifier.aspectRatio(1f).fillMaxWidth(1f).padding(12.dp)
                            )
                        }
                    }
                }
            }

            // Show answer options
            if (showAnswers) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White.copy(0.5f), RoundedCornerShape(16.dp)),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    gameState.value.options.forEach { option ->
                        Image(
                            painter = painterResource(id = getDrawableId(option)),
                            contentDescription = null,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(8.dp)
                                .weight(1f)
                                .clickable {
                                    gameEnded = true
                                    gameWon = option == gameState.value.missingItem
                                }
                        )
                    }
                }
            }
        }

        if (gameEnded) {
            GameEndOverlay(
                gameWon = gameWon,
                onRestartClick = onRestartClick
            )
        }
    }
}

@DrawableRes
fun getDrawableId(index: Int): Int {
    return when (index) {
        0 -> R.drawable.img_item_0_043
        1 -> R.drawable.img_item_1_043
        2 -> R.drawable.img_item_2_043
        3 -> R.drawable.img_item_3_043
        4 -> R.drawable.img_item_4_043
        5 -> R.drawable.img_item_5_043
        6 -> R.drawable.img_item_6_043
        7 -> R.drawable.img_item_7_043
        8 -> R.drawable.img_item_8_043
        9 -> R.drawable.img_item_9_043
        10 -> R.drawable.img_item_10_043
        11 -> R.drawable.img_item_11_043
        else -> R.drawable.img_item_tile_043
    }
}

data class GameState(
    val items: List<Int>,
    val missingIndex: Int,
    val missingItem: Int,
    val options: List<Int>
)

@Composable
fun GameEndOverlay(gameWon: Boolean, onRestartClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (gameWon) "CORRECT!" else "WRONG :(",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 42.sp
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Image(
            painter = painterResource(
                id = if (gameWon) R.drawable.img_btn_next_043 else R.drawable.img_btn_restart_043
            ),
            contentDescription = "Restart",
            modifier = Modifier
                .size(72.dp)
                .clickable(interactionSource = interactionSource, indication = null) {
                    onRestartClick()
                }
        )
    }
}

@Composable
fun RichText(
    text: String,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    borderWidth: Float = 2f,
    borderColor: Color = MaterialTheme.colorScheme.secondary,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = color,
            maxLines = maxLines,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            style = style,
            fontSize = fontSize,
            onTextLayout = onTextLayout,
            modifier = textModifier
        )
        Text(
            text = text,
            color = borderColor,
            maxLines = maxLines,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            fontSize = fontSize,
            style = style.copy(
                color = borderColor,
                drawStyle = Stroke(
                    width = borderWidth,
                )
            ),
            onTextLayout = onTextLayout,
            modifier = textModifier
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.NONE,
    device = "spec:width=1080px,height=1920px,dpi=440"
)
@Composable
fun GameScreenPreview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
//        StartScreen(onItemClick = {}, onSettingsClick = {}, onExitClick = {})
        GameScreen(onSettingsClick = {}, onBackClick = {})
//        HomeScreen(onItemClick = {}, onSettingsClick = {}, onPrivacyClick = {}, onExitClick = {} )
        //RulesScreen(onBackClick = {}, onSettingsClick = {}, onPlayClick = {})
    }
}
