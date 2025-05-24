package com.plinko.aviator.slot

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.plinko.aviator.slot.databinding.ActivityMainBinding
import com.plinko.aviator.slot.util.Lottie
import com.plinko.aviator.slot.util.Once
import com.plinko.aviator.slot.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit  = Once()

    private lateinit var binding : ActivityMainBinding

    lateinit var lottie: Lottie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        lottie.showLoader()
    }

    override fun exit() {
        onceExit.once {

            log("exit")
            coroutine.launch(Dispatchers.Main) {
                finishAndRemoveTask()
                delay(100)
                exitProcess(0)
            }
        }
    }

    private fun initialize() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lottie = Lottie(binding)
    }

}