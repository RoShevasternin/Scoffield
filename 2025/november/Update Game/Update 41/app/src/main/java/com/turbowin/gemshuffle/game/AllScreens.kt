package com.turbowin.gemshuffle.game

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.ui.geometry.Offset
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.turbowin.gemshuffle.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

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
                   .align(Alignment.TopCenter)
               ,
               painter = painterResource(id = R.drawable.img_logo_041),
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
                   color = MaterialTheme.colorScheme.onPrimary,
                   fontSize = 42.sp
               ),
           )
        }

    } else {
            onBackgroundChanged(R.drawable.bg_app_041)
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
                    .fillMaxWidth(0.95f)
                ,
                painter = painterResource(id = R.drawable.img_logo_041),
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
                painter = painterResource(id = R.drawable.img_btn_play_041),
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
                    painter = painterResource(id = R.drawable.img_btn_info_041),
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
                    painter = painterResource(id = R.drawable.img_btn_settings_041),
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
                    painter = painterResource(id = R.drawable.img_btn_exit_041),
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

data class Cup(
    val id: Int,
    val hasGem: Boolean = false
)

fun randomLayout(): Int = listOf(3, 4, 6).random()

fun initializeCups(cupCount: Int): List<Cup> {
    val gemIndex = Random.nextInt(cupCount - 1)
    return List(cupCount) { i -> Cup(id = i, hasGem = i == gemIndex) }
}

// ---------- UI State for animation ----------

class CupUiState(
    val cup: Cup,
    val animatableOffset: Animatable<Offset, AnimationVector2D>
)

// ---------- Main Composable ----------

@Composable
fun GameScreen(
    onSettingsClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onRestartClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    var shuffleCount by remember { mutableStateOf((1..10).random()) }
    var shuffleSpeedMillis by remember { mutableStateOf((200..500).random()) }

    var gameEnded by remember { mutableStateOf(false) }
    var gameWon by remember { mutableStateOf(false) }
    var cupLayout by remember { mutableStateOf(randomLayout()) }
    var cups by remember { mutableStateOf(initializeCups(cupLayout)) }

    var isShuffling by remember { mutableStateOf(false) }
    var isRevealingBeforeShuffle by remember { mutableStateOf(false) }
    var isShowingResult by remember { mutableStateOf(false) }

    var playerGuessIndex by remember { mutableStateOf<Int?>(null) }

    // Trigger initial reveal and shuffle once
    LaunchedEffect(Unit) {
        delay(400)
        isRevealingBeforeShuffle = true
        delay(1000)
        isRevealingBeforeShuffle = false
        isShuffling = true
        shuffleCupsAnimated(
            cups = cups,
            times = shuffleCount,
            speed = shuffleSpeedMillis
        ) { newCups ->
            cups = newCups
            isShuffling = false
        }
    }

    // Layout
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                ShuffleField(
                    cups = cups,
                    shuffleSpeedMillis = shuffleSpeedMillis,
                    isRevealingBeforeShuffle = isRevealingBeforeShuffle,
                    isShuffling = isShuffling,
                    isShowingResult = isShowingResult,
                    playerGuessIndex = playerGuessIndex,
                    onCupClick = { index ->
                        if (!isShuffling && playerGuessIndex == null && !isRevealingBeforeShuffle) {
                            playerGuessIndex = index
                            isShowingResult = true
                            val cup = cups[index]

                            gameEnded = true
                            gameWon = cup.hasGem


                        }
                    }
                )
            }
        }

        RichText(
            text = "FIND THE GEM!",
            modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp).align(Alignment.BottomCenter),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 32.sp
            ),
        )

        if (gameEnded) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                RichText(
                    text = if (gameWon) "GOOD JOB!" else "YOU LOSE :(",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
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
                        if (gameWon) R.drawable.img_btn_next_041 else R.drawable.img_btn_restart_041
                    ),
                    contentDescription = "Restart"
                )
            }
        }

        TopBar(
            startIconResId = R.drawable.img_btn_home_041,
            endIconResId = R.drawable.img_btn_settings_041,
            onStartClick = onBackClick,
            title = "",
            onEndClick = onSettingsClick
        )

    }

}

// ---------- ShuffleField with animated cup positions ----------
@Composable
fun ShuffleField(
    cups: List<Cup>,
    shuffleSpeedMillis: Int,
    isRevealingBeforeShuffle: Boolean,
    isShuffling: Boolean,
    isShowingResult: Boolean,
    playerGuessIndex: Int?,
    onCupClick: (Int) -> Unit
) {

    val rows = when (cups.size) {
        3 -> 1
        4 -> 2
        6 -> 2
        else -> 1
    }
    
    val columns = when (cups.size) {
        3 -> 3
        4 -> 2
        6 -> 3
        else -> 3
    }

    val cupSizeDp = 80.dp
    val spacingDp = 16.dp

    val density = LocalDensity.current
    val cupSizePx = with(density) { cupSizeDp.toPx() }
    val spacingPx = with(density) { spacingDp.toPx() }

    val totalGridWidth = columns * cupSizePx + (columns - 1) * spacingPx
    val totalGridHeight = rows * cupSizePx + (rows - 1) * spacingPx

    val initialPositions = remember(cups.size, rows, columns) {
        List(cups.size) { index ->
            val row = index / columns
            val col = index % columns
            Offset(
                x = col * (cupSizePx + spacingPx),
                y = row * (cupSizePx + spacingPx)
            )
        }
    }


    val initialPositions0 = remember(cups.size) {
        List(cups.size) { index ->
            val row = index / columns
            val col = index % columns
            Offset(
                x = col * (cupSizePx + spacingPx),
                y = row * (cupSizePx + spacingPx)
            )
        }
    }

    val cupUiStates = remember {
        mutableStateMapOf<Int, CupUiState>()
    }

    cups.forEachIndexed { index, cup ->
        if (!cupUiStates.containsKey(cup.id)) {
            cupUiStates[cup.id] = CupUiState(
                cup = cup,
                animatableOffset = Animatable(
                    initialValue = initialPositions[index],
                    typeConverter = Offset.VectorConverter
                )
            )
        }
    }

    LaunchedEffect(cups) {

        val currentIds = cups.map { it.id }.toSet()
        cupUiStates.keys.toList().forEach { key ->
            if (key !in currentIds) {
                cupUiStates.remove(key)
            }
        }


        cups.forEachIndexed { newIndex, cup ->
            cupUiStates[cup.id]?.let { cupUiState ->
                val targetPos = initialPositions[newIndex]
                launch {
                    cupUiState.animatableOffset.animateTo(
                        targetPos,
                        animationSpec = tween(shuffleSpeedMillis)
                    )
                }
            }
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.TopStart
    ) {
        val containerWidthPx = with(density) { maxWidth.toPx() }
        val containerHeightPx = with(density) { maxHeight.toPx() }

        val centerOffsetX = (containerWidthPx - totalGridWidth) / 2f
        val centerOffsetY = (containerHeightPx - totalGridHeight) / 2f

        Image(
            painter = painterResource(R.drawable.img_item_field_041),
            contentDescription = "Game Board",
            modifier = Modifier.matchParentSize()
        )
        val correctCupIndex = cups.indexOfFirst { it.hasGem }

        cupUiStates.values.forEach { cupUiState ->
            val offsetPx = cupUiState.animatableOffset.value

            val adjustedOffset = Offset(
                x = offsetPx.x + centerOffsetX,
                y = offsetPx.y + centerOffsetY
            )
            val cupIndex = cups.indexOf(cupUiState.cup)
            val showGem = (isRevealingBeforeShuffle && cupUiState.cup.hasGem) || (isShowingResult && cupUiState.cup.hasGem)
            val isWrongSelectedCup = isShowingResult && playerGuessIndex != correctCupIndex && playerGuessIndex == cupIndex

            Box(
                modifier = Modifier
                    .size(cupSizeDp)
                    .offset { IntOffset(adjustedOffset.x.roundToInt(), adjustedOffset.y.roundToInt()) }
                    .clickable(
                        enabled = !isShuffling && playerGuessIndex == null && !isRevealingBeforeShuffle
                    ) {
                        val index = cups.indexOfFirst { it.id == cupUiState.cup.id }
                        if (index != -1) {
                            onCupClick(index)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                val resId = when {
                    isWrongSelectedCup -> R.drawable.empty
                    showGem -> R.drawable.img_item_win_041
                    else -> R.drawable.img_item_cup_041
                }

                Image(
                    painter = painterResource(resId),
                    contentDescription = "Gem",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}


// ---------- Shuffle animation with animated position swapping ----------

suspend fun shuffleCupsAnimated(
    cups: List<Cup>,
    times: Int,
    speed: Int,
    onUpdate: (List<Cup>) -> Unit
) {
    var currentCups = cups.toMutableList()

    repeat(times) {
        val i = Random.nextInt(currentCups.size)
        var j = Random.nextInt(currentCups.size)
        while (j == i) j = Random.nextInt(currentCups.size)

        // Swap cups in data
        val temp = currentCups[i]
        currentCups[i] = currentCups[j]
        currentCups[j] = temp

        // Update UI
        onUpdate(currentCups.toList())

        // Wait enough time for animation to finish before next swap
        delay(speed.toLong())
    }

    //onUpdate(currentCups.toList())
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
