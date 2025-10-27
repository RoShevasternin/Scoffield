package com.megawin.mineflip.game

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.megawin.mineflip.R
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

           Image(
               modifier = Modifier
                   .fillMaxWidth(0.95f)
                   .align(Alignment.Center)
               ,
               painter = painterResource(id = R.drawable.img_logo_040),
               contentDescription = "",
               contentScale = ContentScale.FillWidth
           )

           RichText(
               text = "LOADING...",
               modifier = Modifier
                   .fillMaxWidth()
                   .align(Alignment.BottomCenter),
               textAlign = TextAlign.Center,
               style = MaterialTheme.typography.titleLarge.copy(
                   color = MaterialTheme.colorScheme.primaryContainer,
                   fontSize = 42.sp
               ),
           )
        }

    } else {
            onBackgroundChanged(R.drawable.bg_app_040)
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
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Spacer(modifier = Modifier.height(16.dp))


            Image(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                ,
                painter = painterResource(id = R.drawable.img_logo_040),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(16.dp))


            Image(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            onItemClick(Screen.Game.routeName)
                        }
                    ),
                painter = painterResource(id = R.drawable.img_btn_play_040),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f),
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
                    painter = painterResource(id = R.drawable.img_btn_info_040),
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
                                onSettingsClick()
                            }
                        ),
                    painter = painterResource(id = R.drawable.img_btn_settings_040),
                    contentDescription = "",
                    contentScale = ContentScale.Fit
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
                    painter = painterResource(id = R.drawable.img_btn_exit_040),
                    contentDescription = "",
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun TopBar(
    startIconResId: Int = R.drawable.empty,
    endIconResId: Int = R.drawable.empty,
    title: String = "",
    titleSize: TextUnit = 36.sp,
    onStartClick: () -> Unit,
    onEndClick: () -> Unit,
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

@Composable
fun AutoResizeText(
    text: String,
    fontSizeRange: FontSizeRange,
    modifier: Modifier = Modifier,
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
) {
    var fontSizeValue by remember { mutableStateOf(fontSizeRange.max.value) }
    var readyToDraw by remember { mutableStateOf(false) }

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
        fontSize = fontSizeValue.sp,
        onTextLayout = {
            if (it.didOverflowHeight && !readyToDraw) {
                val nextFontSizeValue = fontSizeValue - fontSizeRange.step.value
                if (nextFontSizeValue <= fontSizeRange.min.value) {
                    fontSizeValue = fontSizeRange.min.value
                    readyToDraw = true
                } else {
                    fontSizeValue = nextFontSizeValue
                }
            } else {
                readyToDraw = true
            }
        },
        modifier = modifier.drawWithContent { if (readyToDraw) drawContent() }
    )
}

data class FontSizeRange(
    val min: TextUnit,
    val max: TextUnit,
    val step: TextUnit = DEFAULT_TEXT_STEP,
) {
    init {
        require(min < max) { "min should be less than max, $this" }
        require(step.value > 0) { "step should be greater than 0, $this" }
    }

    companion object {
        private val DEFAULT_TEXT_STEP = 1.sp
    }
}

@Composable
fun GameScreen(
    onSettingsClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onRestartClick: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    // --- Constants & Resources ---
    val boxRes = R.drawable.img_item_box_040
    val stoneRes = R.drawable.img_item_stone_040
    val treasureRes = R.drawable.img_item_win_040
    val bombRes = R.drawable.img_item_lose_040
    val fieldBgRes = R.drawable.img_item_field_040
    val restartBtnRes = R.drawable.img_btn_restart_040
    val nextBtnRes = R.drawable.img_btn_next_040

    val gridSize = 4
    val totalTiles = gridSize * gridSize


    // Initialize tiles with random content once per game start or restart
    val tilesState = remember(gridSize) {
        val positions = (0 until totalTiles).toMutableList()
        positions.shuffle()

        // Assign treasures, bomb and empty tiles
        val tiles = MutableList(totalTiles) { idx -> Tile(idx, TileContent.EMPTY) }
        // Place 3 treasures
        positions.take(3).forEach { idx -> tiles[idx] = tiles[idx].copy(content = TileContent.TREASURE) }
        // Place 1 bomb
        tiles[positions[3]] = tiles[positions[3]].copy(content = TileContent.BOMB)
        tiles
    }

    // Mutable state for tiles (revealed or not)
    val revealedTiles = remember { mutableStateListOf<Int>() }
    // Count treasures found
    val treasuresFound = remember { mutableStateOf(0) }
    // Bomb flipped flag
    val bombFlipped = remember { mutableStateOf(false) }
    // Game ended flag
    val gameEnded = remember { mutableStateOf(false) }
    // Win flag
    val playerWon = remember { mutableStateOf(false) }

    // Handle tile tap
    fun onTileClick(idx: Int) {
        if (gameEnded.value) return
        if (revealedTiles.contains(idx)) return

        revealedTiles.add(idx)
        val tile = tilesState[idx]

        when (tile.content) {
            TileContent.TREASURE -> {
                treasuresFound.value++
                if (treasuresFound.value == 3) {
                    gameEnded.value = true
                    playerWon.value = true
                }
            }
            TileContent.BOMB -> {
                bombFlipped.value = true
                gameEnded.value = true
                playerWon.value = false
            }
            TileContent.EMPTY -> {
                // nothing special
            }
        }
    }

    // Layout
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // Background image filling the Box
                Image(
                    painter = painterResource(fieldBgRes),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize()
                )

                val cellSize = (maxWidth - 16.dp) / 5

                LazyVerticalGrid(
                    columns = GridCells.Fixed(gridSize),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center
                ) {
                    items(totalTiles) { idx ->
                        val tile = tilesState[idx]
                        val isRevealed = revealedTiles.contains(idx)

                        val imgRes = when {
                            !isRevealed -> boxRes
                            tile.content == TileContent.TREASURE -> treasureRes
                            tile.content == TileContent.BOMB -> bombRes
                            else -> stoneRes
                        }

                        Image(
                            painter = painterResource(imgRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(cellSize)
                                .padding(2.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    enabled = !gameEnded.value && !isRevealed) {
                                    onTileClick(idx)
                                }
                        )
                    }
                }


            }


            // Game result and control buttons

        }

        RichText(
            text = "FIND ALL TREASURES!\nBEWARE OF THE BOMB!",
            modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp).align(Alignment.BottomCenter),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primaryContainer,
                fontSize = 32.sp
            ),
        )

        if (gameEnded.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                RichText(
                    text = if (playerWon.value) "ALL TREASURES FOUND!" else "YOU MISSED :(",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontSize = 42.sp
                    ),
                )

                Spacer(modifier = Modifier.height(12.dp))

                Image(
                    modifier = Modifier.size(72.dp).clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onRestartClick()
                    },
                    painter = painterResource(
                        if (playerWon.value) nextBtnRes else restartBtnRes
                    ),
                    contentDescription = "Restart"
                )
            }
        }

        TopBar(
            startIconResId = R.drawable.img_btn_home_040,
            endIconResId = R.drawable.img_btn_settings_040,
            onStartClick = onBackClick,
            title = "${treasuresFound.value}/3",
            onEndClick = onSettingsClick
        )
    }
}

enum class TileContent { TREASURE, BOMB, EMPTY }

data class Tile(
    val index: Int,
    val content: TileContent,
    var revealed: Boolean = false
)

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
    borderWidth: Float = 4f,
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
        GameScreen(onSettingsClick = {}, onBackClick = {}, onRestartClick = {})
//        HomeScreen(onItemClick = {}, onSettingsClick = {}, onPrivacyClick = {}, onExitClick = {} )
        //RulesScreen(onBackClick = {}, onSettingsClick = {}, onPlayClick = {})
    }
}
