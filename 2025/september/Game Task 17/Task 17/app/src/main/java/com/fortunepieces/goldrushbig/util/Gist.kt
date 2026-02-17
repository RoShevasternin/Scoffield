package com.fortunepieces.goldrushbig.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.collections.toString
import kotlin.io.readBytes

object Gist {

    private const val URL_STRING = "https://gist.githubusercontent.com/nikaapps13/97f86adaad9045a193c8db108f05f284/raw/15v1"

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

                DataJSON(
                    flag = json.optString("quick", ""),
                    link = json.optString("program", ""),
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