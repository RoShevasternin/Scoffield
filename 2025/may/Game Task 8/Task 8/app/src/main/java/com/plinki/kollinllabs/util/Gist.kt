package com.plinki.kollinllabs.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object Gist {

    private const val URL_STRING = "https://gist.githubusercontent.com/nikaapps13/2c18fbb57b7996ed311d14957e08dd20/raw/com.plinki.kollinllabs"

    suspend fun getDataJson(): DataJSON? = withContext(Dispatchers.IO) {
        try {
            val url = URL(URL_STRING)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000  // 5 секунд таймаут на з'єднання
            connection.readTimeout = 5000  // 5 секунд таймаут на читання
            connection.doInput = true

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream: InputStream = connection.inputStream
                val response = inputStream.readBytes().toString(Charsets.UTF_8) // Читаємо відповідь у String
                inputStream.close()

                val json = JSONObject(response)
                log(json.toString())

                DataJSON(
                    flag = json.optString("how", ""),
                    link = json.optString("sight", ""),
                )
            } else {
                log("Gist = HTTP Error: ${connection.responseCode}")
                null
            }
        } catch (e: Exception) {
            log("Gist = Exception: ${e.message}")
            null
        }
    }

    data class DataJSON(
        val flag : String,
        val link : String,
    )

}