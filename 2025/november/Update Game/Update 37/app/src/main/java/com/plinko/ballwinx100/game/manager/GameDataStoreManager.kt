package com.plinko.ballwinx100.game.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.plinko.ballwinx100.game.manager.util.TimeFormatter
import com.plinko.ballwinx100.util.AbstractDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

object GameDataStoreManager : AbstractDataStore() {
    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "GAME_DATA_STORE")

    private val timeFormatter = TimeFormatter()

    object TopTimes : AbstractDataStore.DataStoreElement<String>() {
        override val key = stringPreferencesKey("top_times")
    }

    object Money : AbstractDataStore.DataStoreElement<Int>() {
        override val key = intPreferencesKey("money")
    }

    object LevelMoney : AbstractDataStore.DataStoreElement<Int>() {
        override val key = intPreferencesKey("level_money")
    }

    object BallCount : AbstractDataStore.DataStoreElement<Int>() {
        override val key = intPreferencesKey("ball_count")
    }

    object BallPrice : AbstractDataStore.DataStoreElement<Int>() {
        override val key = intPreferencesKey("ball_price")
    }

    suspend fun ballPrice(): Int {
        val price = (BallPrice.get() ?: 1)
        return  if(price <= 0) 1 else price
    }

    suspend fun updateBallPrice(amount: Int) {
        BallPrice.update { amount }
    }

    suspend fun ballCount(): Int {
        val count = (BallCount.get() ?: 5)
        return  if(count <= 0) 5 else count
    }

    suspend fun updateBallCount(amount: Int) {
        BallCount.update { amount }
    }

    suspend fun levelMoney(): Int {
        val money = (LevelMoney.get() ?: 0)
        return  if(money <= 0) 0 else money
    }

    suspend fun addLevelMoney(amount: Int) {
        LevelMoney.update { current ->
            current?.plus(amount) ?: amount
        }
    }

    suspend fun removeLevelMoney() {
        LevelMoney.update { 0 }
    }

    suspend fun money(): Int {
        val money = (Money.get() ?: 150)
        return  if(money <= 0) 0 else if(money > 9999) 9999 else money
    }

    suspend fun updateMoney(amount: Int) {
        Money.update { amount }
    }

    suspend fun addMoney(amount: Int) {
        Money.update { current ->
            current?.plus(amount) ?: amount
        }
    }

    fun moneyFlow(dataStore: DataStore<Preferences>): Flow<Int> {
        val flow =  dataStore.data
            .filter { it.contains(Money.key) }
            .map { prefs ->
                val money = prefs[Money.key] ?: 0
                if(money <= 0) 0 else if(money > 9999) 9999 else money
            }

        return flow
    }

    fun ballCountFlow(dataStore: DataStore<Preferences>): Flow<Int> {
        val flow = dataStore.data
            .map { prefs ->
                prefs[BallCount.key] ?: 5
            }

        return flow
    }

    fun ballPriceFlow(dataStore: DataStore<Preferences>): Flow<Int> {
        val flow = dataStore.data
            .map { prefs ->
                prefs[BallPrice.key] ?: 1
            }

        return flow
    }

    fun ballPriceAndCountFlow(dataStore: DataStore<Preferences>): Flow<Pair<Int,Int>> {
        val flow = dataStore.data
            .map { prefs ->
                (prefs[BallCount.key] ?: 5) to (prefs[BallPrice.key] ?: 1)
            }

        return flow
    }

}

