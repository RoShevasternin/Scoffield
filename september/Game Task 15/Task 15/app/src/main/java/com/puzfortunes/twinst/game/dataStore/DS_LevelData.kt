package com.puzfortunes.twinst.game.dataStore//package com.puzfortunes.twinst.game.dataStore
//
//import com.puzfortunes.twinst.game.manager.DataStoreManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.serialization.builtins.ListSerializer
//
//class DS_LevelData(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataLevel>>(
//    serializer   = ListSerializer(DataLevel.serializer()),
//    deserializer = ListSerializer(DataLevel.serializer()),
//) {
//
//    override val dataStore = DataStoreManager.DataLevel
//
//    override val flow = MutableStateFlow(List(12) { DataLevel(it.inc(), isLock = it != 0) })
//
//    init { initialize() }
//
//}