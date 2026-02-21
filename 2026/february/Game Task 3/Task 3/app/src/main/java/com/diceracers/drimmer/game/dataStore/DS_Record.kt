package com.diceracers.drimmer.game.dataStore

import com.diceracers.drimmer.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Record(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.Record

    override val flow = MutableStateFlow(0)

    init {
        initialize()
    }

}