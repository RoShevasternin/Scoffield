package com.fortunetiger.mystictrail.game

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.ImageView
import androidx.core.view.isVisible
import com.fortunetiger.mystictrail.game.screens.menu.ComponentController
import com.fortunetiger.mystictrail.game.screens.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameController(override val gameFragment: GameFragment) : ComponentController {

    val ballCounterFlow = MutableStateFlow(0)

    companion object {
        const val BALL = 10
    }

    private val minX = 0f

    private val sensorManager by lazy { gameFragment.requireActivity().getSystemService(SENSOR_SERVICE) as SensorManager }

    private val sensorList by lazy { sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).first() }

    private val minY = 0f
    private val sensorEventListener by lazy {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                movePlayer(event.values[1], event.values[0])
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
    }

    val coroutineMain = CoroutineScope(Dispatchers.Main)


    override fun dispose() {
        cancelCoroutinesAll(coroutineMain)
    }


    private val maxX get() = gameFragment.binding.fitViewport.width.toFloat()
    private val maxY get() = gameFragment.binding.fitViewport.height.toFloat()

    private val ballFlow = MutableSharedFlow<ImageView>(replay = 10)

    private var accumulator = 0.1f
    private var accumulatorY = 0.1f
    private var previousIsPositiveX = true
    private var previousIsPositiveY = true


    fun registerSensor() {
        sensorManager.registerListener(sensorEventListener, sensorList, SensorManager.SENSOR_DELAY_GAME)
    }

    private fun movePlayer(x: Float, y: Float) {
        gameFragment.binding.playerImage.rotationY = if (x < 0) 0f else 180f

        accumulator = if (x >= 0 == previousIsPositiveX) accumulator + 0.1f else 0.1f
        previousIsPositiveX = x >= 0
        accumulatorY = if (y >= 0 == previousIsPositiveY) accumulatorY + 0.1f else 0.1f
        previousIsPositiveY = y >= 0

        with(gameFragment.binding) {
            val currentPlayerX = playerImage.x
            val currentPlayerY = playerImage.y

            val newX = when {
                currentPlayerX + playerImage.width < minX -> maxX
                currentPlayerX - playerImage.width > maxX -> minX
                else -> currentPlayerX + (x * accumulator)
            }
            val newY = when {
                currentPlayerY + playerImage.height < minY -> maxY
                currentPlayerY > maxY -> minY
                else -> currentPlayerY + (y * accumulatorY)
            }
            playerImage.x = newX
            playerImage.y = newY

            gameFragment.ballList.onEach { ballImage ->
                if (
                    ballImage.y in playerImage.y..(playerImage.y + playerImage.height) &&
                    ballImage.x in playerImage.x..(playerImage.x + playerImage.width)
                ) {
                    ballImage.isVisible = false



                    ballImage.x = -ballImage.width.toFloat()
                    ballImage.y = -ballImage.height.toFloat()


                    ballCounterFlow.value += 1
                    ballFlow.tryEmit(ballImage)
                }
            }
        }
    }

    fun generateBall() {
        coroutineMain.launch {
            gameFragment.ballList.onEach {


                ballFlow.emit(it)
                delay((701..1001).random().toLong())
            }
        }

        coroutineMain.launch {
            ballFlow.collect { imageView ->
                delay((502..1003).random().toLong())
                imageView.x = Random.nextInt(minX.toInt(), maxX.toInt() - imageView.width).toFloat()



                imageView.y = Random.nextInt(minY.toInt(), maxY.toInt() - imageView.height).toFloat()
                imageView.isVisible = true
            }
        }
    }


    fun updateSensor() {
        coroutineMain.launch {
            ballCounterFlow.collect { ballCount ->
                gameFragment.binding.scoreboardText.text = ballCount.toString()
            }
        }
    }

}