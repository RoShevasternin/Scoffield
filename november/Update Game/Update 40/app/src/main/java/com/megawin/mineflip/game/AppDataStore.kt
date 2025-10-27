package com.megawin.mineflip.game

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.megawin.mineflip.AbstractDataStore
import com.megawin.mineflip.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDataStore(private val context: Context) : AbstractDataStore() {

    inner class Keyz: DataStoreElement<String>(context) {
        override val key = stringPreferencesKey("Norvegia")
    }

    inner class Links: DataStoreElement<String>(context) {
        override val key = stringPreferencesKey("ismadagaskar")
    }

    inner class SettingsMusic : DataStoreElement<Boolean>(context) {
        override val key = booleanPreferencesKey("music")
    }

    inner class SettingsSound : DataStoreElement<Boolean>(context) {
        override val key = booleanPreferencesKey("sound")
    }

    suspend fun keys() = Keyz().get()
    suspend fun links() = Links().get()

    suspend fun updateKeyz(block: suspend (String?) -> String) {
        Keyz().update(block)
    }

    suspend fun updateLink(block: suspend (String?) -> String) {
        Links().update(block)
    }

    fun musicFlow(): Flow<Boolean> {
        val flow =  context.dataStore.data
            .map { prefs ->
                prefs[SettingsMusic().key] ?: true
            }

        return flow
    }

    fun effectsFlow(): Flow<Boolean> {
        val flow =  context.dataStore.data
            .map { prefs ->
                prefs[SettingsSound().key] ?: true
            }
        return flow
    }

    suspend fun enableMusic(enabled: Boolean) {
        SettingsMusic().update { enabled }
    }

    suspend fun enableEffects(enabled: Boolean) {
        SettingsSound().update { enabled }
    }

}

