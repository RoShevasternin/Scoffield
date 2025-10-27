package com.turbowin.safeunlock.game

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turbowin.safeunlock.R
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
        onBackgroundChanged(R.drawable.bg_app_045)
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
        startIconResId = R.drawable.img_btn_info_045,
        endIconResId = R.drawable.img_btn_settings_045,
        title = "",
        onEndClick = onSettingsClick,
        onStartClick = onPrivacyClick
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(
            text = "BROKE THE CODE\nIN 5 ATTEMPTS",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 36.sp
            )
        )

        Image(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        onItemClick(Screen.Game.routeName)
                    }
                ),
            painter = painterResource(id = R.drawable.img_btn_play_045),
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
                            onExitClick()
                        }
                    ),
                painter = painterResource(id = R.drawable.img_btn_exit_045),
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

@Composable
fun GameScreen(
    onSettingsClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onRestartClick: () -> Unit = {}
) {
    val maxAttempts = 5
    val codeLength = 3
    val digits = (0..9).map { it.toString()[0] }

    var targetCode by remember { mutableStateOf("") }
    var guesses = remember { mutableStateListOf<List<Char>>() }
    var evaluations = remember { mutableStateListOf<List<TileState>>() }
    var currentGuess by remember { mutableStateOf(mutableListOf<Char>()) }
    var gameOver by remember { mutableStateOf(false) }
    var isWin by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (targetCode.isBlank()) {
            targetCode = List(codeLength) { digits.random() }.joinToString("")
        }
    }

    fun evaluateGuess(guess: List<Char>): List<TileState> {
        val result = MutableList(codeLength) { TileState.Default }
        val used = BooleanArray(codeLength)

        // First pass: correct digit and position
        for (i in 0 until codeLength) {
            if (guess[i] == targetCode[i]) {
                result[i] = TileState.Correct
                used[i] = true
            }
        }

        // Second pass: digit exists but wrong position
        for (i in 0 until codeLength) {
            if (result[i] == TileState.Default) {
                val index = targetCode.indices.firstOrNull {
                    guess[i] == targetCode[it] && !used[it]
                }
                if (index != null) {
                    result[i] = TileState.Present
                    used[index] = true
                } else {
                    result[i] = TileState.Absent
                }
            }
        }

        return result
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        TopBar(
            startIconResId = R.drawable.img_btn_back_045,
            endIconResId = R.drawable.img_btn_settings_045,
            onStartClick = onBackClick,
            title = "",
            onEndClick = onSettingsClick
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Top bar


            Spacer(modifier = Modifier.height(16.dp))

            // Grid of guesses
            val spacing = 8.dp
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            val totalSpacing = spacing * (codeLength - 1)
            val tileSize = (screenWidth - 32.dp - totalSpacing) / maxAttempts

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                repeat(maxAttempts) { attempt ->
                    val guess = when {
                        attempt < guesses.size -> guesses[attempt]
                        attempt == guesses.size -> currentGuess
                        else -> emptyList()
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
                        repeat(codeLength) { index ->
                            val digit = guess.getOrNull(index)?.toString() ?: ""
                            val eval = evaluations.getOrNull(attempt)?.getOrNull(index)
                            val color = when (eval) {
                                TileState.Correct -> Color(0xFFFFD700) // gold
                                TileState.Present -> Color(0xFF90EE90)
                                TileState.Absent -> Color.Gray
                                else -> Color.LightGray
                            }

                            Box(
                                modifier = Modifier
                                    .size(tileSize)
                                    .background(color, RoundedCornerShape(6.dp))
                                    .border(1.dp, Color.DarkGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(digit, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Number Pad
            DigitKeyboard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onDigitClick = { digit ->
                    if (currentGuess.size < codeLength) {
                        currentGuess = currentGuess.toMutableList().apply { add(digit) }
                    }
                },
                onBackspaceClick = {
                    currentGuess = currentGuess.dropLast(1).toMutableList()
                },
                onSubmitClick = {
                    if (currentGuess.size == codeLength) {
                        val eval = evaluateGuess(currentGuess)
                        isWin = eval.all { it == TileState.Correct }
                        guesses.add(currentGuess.toList())
                        evaluations.add(eval)
                        currentGuess.clear()
                        gameOver = isWin || guesses.size == maxAttempts
                    }
                },
                currentGuessLength = currentGuess.size,
                gameOver = gameOver
            )

        }

        if (gameOver) {
            GameEndOverlay(
                gameWon = isWin,
                targetCode = targetCode,
                onRestartClick = onRestartClick
            )
        }
    }
}


private enum class TileState {
    Default, Present, Correct, Absent
}


@Composable
fun DigitKeyboard(
    modifier: Modifier,
    onDigitClick: (Char) -> Unit,
    onBackspaceClick: () -> Unit,
    onSubmitClick: () -> Unit,
    currentGuessLength: Int,
    gameOver: Boolean
) {
    val rows = listOf(
        listOf('1', '2', '3', '4', '5', '6'),
        listOf('7', '8', '9', '0', '⌫'),
        listOf('⏎')
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        rows.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                row.forEach { key ->
                    val label = key.toString()
                    val isEnabled = !gameOver

                    val onClick = when (key) {
                        '⌫' -> onBackspaceClick
                        '⏎' -> if (currentGuessLength == 3) onSubmitClick else return@forEach
                        else -> {
                            { onDigitClick(key) }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isEnabled) Color(0xFFDEB887) else Color.Gray)
                            .clickable(enabled = isEnabled, onClick = onClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(label, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun GameEndOverlay(
    gameWon: Boolean,
    targetCode: String = "",
    onRestartClick: () -> Unit) {
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
            text = if (gameWon) "YOU BROKE THE CODE!" else "CODE WAS: $targetCode",
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
                id = if (gameWon) R.drawable.img_btn_next_045 else R.drawable.img_btn_restart_045
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
