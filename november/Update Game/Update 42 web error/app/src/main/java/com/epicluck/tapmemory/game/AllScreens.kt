package com.epicluck.tapmemory.game

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.epicluck.tapmemory.R
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

            RichText(
                text = "PLEASE WAIT...",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 38.sp
                ),
            )
        }

    } else {
        onBackgroundChanged(R.drawable.bg_app_042)
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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth(0.95f),
            painter = painterResource(id = R.drawable.img_logo_042),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )


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
            painter = painterResource(id = R.drawable.img_btn_play_042),
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
                painter = painterResource(id = R.drawable.img_btn_info_042),
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
                painter = painterResource(id = R.drawable.img_btn_settings_042),
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
                painter = painterResource(id = R.drawable.img_btn_exit_042),
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

// ---------- Main Composable ----------

@Composable
fun GameScreen(
    onSettingsClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onRestartClick: () -> Unit = {}
) {
    val allItemIds = listOf(
        R.drawable.img_item_item_0_042,
        R.drawable.img_item_item_1_042,
        R.drawable.img_item_item_2_042,
        R.drawable.img_item_item_3_042,
        R.drawable.img_item_item_4_042,
        R.drawable.img_item_item_5_042
    )
    val rows = 4
    val columns = rows
    val gridSize = rows * columns
    val originalSequence = remember { generateRandomSequence(allItemIds, (3..5).random()) }
    val showSequence = remember { mutableStateOf(true) }
    val gridItems = remember {
        (
                originalSequence + generateRandomDistractors(
                    allItemIds,
                    originalSequence,
                    gridSize - originalSequence.size
                )
        ).shuffled()
    }

    val revealedIndices = remember { mutableStateListOf<Int>() }
    val tappedSequence = remember { mutableStateListOf<Int>() }

    var gameEnded by remember { mutableStateOf(false) }
    var gameWon by remember { mutableStateOf(false) }

    // Hide sequence and flip tiles after delay
    LaunchedEffect(Unit) {
        delay(3000)
        showSequence.value = false
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val tileSize = maxWidth / 5

        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                startIconResId = R.drawable.img_btn_home_042,
                endIconResId = R.drawable.img_btn_settings_042,
                onStartClick = onBackClick,
                title = "",
                onEndClick = onSettingsClick
            )


            // Top Row (target sequence if shown, or user's taps)
            SequenceRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(tileSize + 8.dp),
                sequence = if (showSequence.value) originalSequence else tappedSequence,
                tileSize = tileSize
            )


            // Grid
            TileGrid(
                rows = rows,
                columns = columns,
                items = gridItems,
                showIcons = showSequence.value,
                revealedIndices = revealedIndices
            ) { index, itemResId ->
                if (!revealedIndices.contains(index)) {
                    revealedIndices.add(index)
                    tappedSequence.add(itemResId)

                    if (tappedSequence.size == originalSequence.size) {
                        gameEnded = true
                        gameWon = tappedSequence.toList() == originalSequence
                    }
                }
            }

            RichText(
                text = "WATCH CLOSELY!\nREVEAL IN ORDER!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 24.sp
                ),
            )
        }

        if (gameEnded) {
            GameEndOverlay(gameWon = gameWon, onRestartClick = onRestartClick)
        }
    }
}

@Composable
fun SequenceRow(
    modifier: Modifier = Modifier,
    sequence: List<Int>, tileSize: Dp
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val rows = sequence.chunked(6)
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { itemResId ->
                    Image(
                        painter = painterResource(id = itemResId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(tileSize)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun TileGrid(
    rows: Int,
    columns: Int,
    items: List<Int>,
    showIcons: Boolean,
    revealedIndices: List<Int>,
    onTileClick: (Int, Int) -> Unit
) {
    val tilePadding = 4.dp
    val totalPadding = tilePadding * (columns + 1) // Padding between tiles

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth() // Use full width
            .aspectRatio(1f)// Make it square
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        val gridSize = maxWidth
        val tileSize = (gridSize - totalPadding) / 5

        // Background field image
        Image(
            painter = painterResource(id = R.drawable.img_item_field_042),
            contentDescription = "Field background",
            modifier = Modifier
                .fillMaxSize()
        )

        // Tile Grid over background
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (rowIndex in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (colIndex in 0 until columns) {
                        val index = rowIndex * columns + colIndex
                        if (index < items.size) {
                            val itemResId = items[index]
                            val revealed = revealedIndices.contains(index)

                            Box(
                                modifier = Modifier
                                    .size(tileSize)
                                    .background(
                                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.33f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(4.dp)
                                    .clickable(enabled = !revealed && !showIcons) {
                                        onTileClick(index, itemResId)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = if (showIcons || revealed)
                                            itemResId
                                        else
                                            R.drawable.img_item_tile_042
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.size(tileSize))
                        }
                    }
                }
            }
        }
    }
}


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
                id = if (gameWon) R.drawable.img_btn_next_042 else R.drawable.img_btn_restart_042
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

fun generateRandomSequence(allItemIds: List<Int>, count: Int): List<Int> {
    return allItemIds.shuffled().take(count)
}

fun generateRandomDistractors(all: List<Int>, used: List<Int>, count: Int): List<Int> {
    val available = all - used
    return (1..count).map { available.random() }
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
