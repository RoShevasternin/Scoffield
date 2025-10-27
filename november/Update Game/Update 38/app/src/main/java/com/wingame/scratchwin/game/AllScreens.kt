package com.wingame.scratchwin.game

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wingame.scratchwin.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@Composable
fun StartScreen(
    enabled: Boolean = true,
    onItemClick: (routeName: String) -> Unit,
    onBackgroundChanged: (Int) -> Unit = {},
    onSettingsClick: () -> Unit,
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
               text = "WIN GAME\n\nLOADING...",
               modifier = Modifier
                   .fillMaxWidth()
                   .align(Alignment.Center),
               textAlign = TextAlign.Center,
               style = MaterialTheme.typography.titleLarge.copy(
                   color = MaterialTheme.colorScheme.onPrimary,
                   fontSize = 42.sp
               ),
           )
        }

    } else {
            onBackgroundChanged(R.drawable.bg_app_038)
            HomeScreen(
                onItemClick,
                onSettingsClick,
                onExitClick
            )
    }
}

@Composable
fun HomeScreen(
    onItemClick: (routeName: String) -> Unit,
    onSettingsClick: () -> Unit,
    onExitClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.weight(0.4f))

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {
                                onItemClick(Screen.Game.routeName)
                            }
                        ),
                    buttonText = "START",
                    fontMaxSize = 40.sp
                )

                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {
                                onSettingsClick()
                            }
                        ),
                    buttonText = "OPTIONS",
                    fontMaxSize = 40.sp
                )

                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {
                                onExitClick()
                            }
                        ),
                    buttonText = "EXIT",
                    fontMaxSize = 40.sp
                )

            }

            Spacer(modifier = Modifier.weight(0.4f))

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
    val context = LocalContext.current
    val dataStore = remember { AppDataStore(context) }
    val imageIds = listOf(
        R.drawable.img_item_0_038,
        R.drawable.img_item_1_038,
        R.drawable.img_item_2_038,
        R.drawable.img_item_3_038,
        R.drawable.img_item_4_038,
        R.drawable.img_item_5_038
    )
    val interactionSource = remember { MutableInteractionSource() }

    val gridRows = remember { listOf(1, 2, 3).random() }
    val gridCols = 3
    val totalTiles = gridRows * gridCols

    var scratchCount by remember { mutableIntStateOf(0) }
    var isGameWon by remember { mutableStateOf(false) }
    var isGameOver by remember { mutableStateOf(false) }
    var revealedTiles by remember { mutableStateOf(List(totalTiles) { false }) }
    val winningImageId = remember { mutableIntStateOf(-1) }

    val tileImages = remember {
        val result = MutableList(totalTiles) { 0 }
        val shouldWin = (0..99).random() < 95 // 95% chance to win

        if (shouldWin) {
            val winImage = imageIds.random()
            winningImageId.intValue = winImage
            val winIndexes = result.indices.shuffled().take(3)
            winIndexes.forEach { result[it] = winImage }

            val remainingIndexes = result.indices.filterNot { it in winIndexes }
            val nonWinImages = imageIds.filter { it != winImage }.toMutableList()

            for ((fillIndex, i) in remainingIndexes.withIndex()) {
                result[i] = nonWinImages[fillIndex % nonWinImages.size]
            }

            result.shuffle()
        } else {
            do {
                val candidate = MutableList(totalTiles) { imageIds.random() }
                val counts = candidate.groupingBy { it }.eachCount()
                if (counts.values.none { it >= 3 }) {
                    result.clear()
                    result.addAll(candidate)
                    break
                }
            } while (true)

            winningImageId.intValue = -1
        }

        result
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            startIconResId = R.drawable.img_btn_home_038,
            endIconResId = R.drawable.img_btn_settings_038,
            onStartClick = onBackClick,
            title = "",
            onEndClick = onSettingsClick
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RichText(
                text = "SCRATCH GREY TILES TO WIN",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                ),
                textAlign = TextAlign.Center,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (row in 0 until gridRows) {
                Row {
                    for (col in 0 until gridCols) {
                        val index = row * gridCols + col
                        if (index < tileImages.size) {
                            key(index) {
                                ScratchTile(
                                    imageResId = tileImages[index],
                                    isRevealed = revealedTiles[index],
                                    onReveal = {
                                        if (!isGameOver && !revealedTiles[index]) {
                                            revealedTiles = revealedTiles.toMutableList().apply {
                                                this[index] = true
                                            }
                                            scratchCount++

                                            val revealedImageIds = tileImages.zip(revealedTiles)
                                                .filter { it.second }
                                                .map { it.first }

                                            val winCount = revealedImageIds.count { it == winningImageId.intValue }

                                            if (winCount == 3) {
                                                isGameWon = true
                                                isGameOver = true
                                            } else if (scratchCount == totalTiles) {
                                                isGameOver = true
                                            }
                                        }
                                    },
                                    scratchEnabled = !isGameOver
                                )
                            }
                        }
                    }
                }
            }
            }
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (isGameOver) {

                val endText = when {
                    isGameWon -> "YOU WON!"
                    else -> "YOU LOSE :("
                }

                RichText(
                    text = endText,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    ),
                    textAlign = TextAlign.Center,
                )

                val buttonText = when {
                    isGameWon -> "NEXT"
                    else -> "RESTART"
                }

                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {
                                onRestartClick()
                            }
                        ),
                    buttonText = buttonText,
                    fontMaxSize = 32.sp
                )
            }
        }
    }
}

@Composable
fun ScratchTile(
    imageResId: Int,
    isRevealed: Boolean,
    onReveal: () -> Unit,
    scratchEnabled: Boolean
) {
    val brushRadius = 24f
    val scratchThreshold = 30
    var scratchPoints by remember { mutableIntStateOf(0) }
    val revealed = remember { mutableStateOf(isRevealed) }

    val scratchedPath = remember { Path() }
    val redrawTrigger = remember { mutableIntStateOf(0) }

    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(100.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        if (!revealed.value) {
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .pointerInput(scratchEnabled) {
                        if (!scratchEnabled) return@pointerInput

                        coroutineScope {
                            while (true) {
                                awaitPointerEventScope {
                                    val event = awaitPointerEvent()
                                    val position = event.changes.firstOrNull()?.position
                                    if (position != null) {
                                        scratchedPath.addOval(
                                            Rect(
                                                offset = Offset(
                                                    position.x - brushRadius,
                                                    position.y - brushRadius
                                                ),
                                                size = Size(brushRadius * 2, brushRadius * 2)
                                            )
                                        )
                                        scratchPoints++
                                        redrawTrigger.value++

                                        if (scratchPoints >= scratchThreshold && !revealed.value) {
                                            revealed.value = true
                                            onReveal()
                                        }

                                        event.changes.forEach { it.consume() }
                                    }
                                }
                            }
                        }
                    }
            ) {
                // Draw scratch overlay into a transparent layer and erase path from it
                drawIntoCanvas { canvas ->
                   canvas.saveLayer(bounds = size.toRect(), paint = Paint())
                    // 1. Draw gray overlay
                    drawRect(Color.Gray)

                    // 2. Erase scratched areas
                    drawPath(
                        path = scratchedPath,
                        color = Color.Transparent,
                        blendMode = BlendMode.Clear
                    )

                    canvas.restore()
                }

                redrawTrigger.value
            }
        }
    }
}



@Composable
fun ScratchTile1(
    imageResId: Int,
    isRevealed: Boolean,
    onReveal: () -> Unit,
    scratchEnabled: Boolean
) {
    val brushRadius = 40f
    val scratchedPath = remember { Path() }
    var scratchPoints by remember { mutableStateOf(0) }
    val revealed = remember { mutableStateOf(isRevealed) }

    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = painterResource(imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (!revealed.value) {
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .pointerInput(scratchEnabled) {
                        detectDragGestures { change, _ ->
                            if (!scratchEnabled) return@detectDragGestures

                            scratchedPath.addOval(
                                Rect(
                                    Offset(
                                        change.position.x - brushRadius,
                                        change.position.y - brushRadius
                                    ),
                                    Size(brushRadius * 2, brushRadius * 2)
                                )
                            )
                            scratchPoints++
                            if (scratchPoints > 30) {
                                revealed.value = true
                                onReveal()
                            }
                        }
                    }
            ) {
                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        isAntiAlias = true
                    }
                    // Save compositing layer
                    canvas.saveLayer(Rect(Offset.Zero, size), paint)

                    // Gray overlay
                    drawRect(
                        color = Color.Gray,
                        topLeft = Offset.Zero,
                        size = size
                    )

                    // Clear path
                    drawPath(
                        path = scratchedPath,
                        color = Color.Transparent,
                        style = Stroke(width = brushRadius * 2),
                        blendMode = BlendMode.Clear
                    )

                    // Apply compositing
                    canvas.restore()
                }
            }
        }
    }
}


@Composable
fun AppButton(
    modifier: Modifier,
    fontMaxSize: TextUnit = 64.sp,
    buttonText: String,
    horizontalPadding: Dp = 16.dp
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.img_bg_button_038),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        AutoResizeText(
            text = buttonText,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding),
            fontSizeRange = FontSizeRange(
                min = 9.sp,
                max = fontMaxSize,
            ),
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary,
            ),
            textAlign = TextAlign.Center
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
        StartScreen(onItemClick = {}, onSettingsClick = {}, onExitClick = {})
        //GameScreen()
//        HomeScreen(onItemClick = {}, onSettingsClick = {}, onPrivacyClick = {}, onExitClick = {} )
        //RulesScreen(onBackClick = {}, onSettingsClick = {}, onPlayClick = {})
    }
}
