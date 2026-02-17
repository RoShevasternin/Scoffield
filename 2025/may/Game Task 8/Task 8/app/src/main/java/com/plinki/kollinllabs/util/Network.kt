package com.plinki.kollinllabs.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object Network {

    suspend fun requestGET(url: String): JSONObject? = withContext(Dispatchers.IO) {
        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod  = "GET"
            connection.connectTimeout = 5000  // 5 секунд таймаут на з'єднання
            connection.readTimeout    = 5000  // 5 секунд таймаут на читання
            connection.doInput        = true

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream: InputStream = connection.inputStream
                val response = inputStream.readBytes().toString(Charsets.UTF_8) // Читаємо відповідь у String
                inputStream.close()

                val json = JSONObject(response)
                log("Network JSON: $json")
                json
            } else {
                log("Network = HTTP Error: ${connection.responseCode}")
                null
            }
        } catch (e: Exception) {
            log("Network = Exception: ${e.message}")
            null
        }
    }

}