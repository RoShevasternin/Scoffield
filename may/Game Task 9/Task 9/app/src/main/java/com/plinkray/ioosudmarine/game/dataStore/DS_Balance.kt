package com.plinkray.ioosudmarine.game.dataStore

import com.plinkray.ioosudmarine.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Balance(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.Balance

    override val flow = MutableStateFlow(100)

    init { initialize() }

}