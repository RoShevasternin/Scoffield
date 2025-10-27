package com.bonusprize.chestopen.tool

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class Network(
    urlString: String
) {

    private val okHttpClient    = OkHttpClient.Builder().build()
    private val qistRequest     = Request.Builder().url(urlString).build()

    suspend fun getGistJSON(
        onSuccess: (JSONObject) -> Unit,
        onFailed: () -> Unit
    ) {
        try {
            withContext(Dispatchers.IO) {
                val response = okHttpClient.newCall(qistRequest).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string() ?: ""
                    val json = JSONObject(responseBody)
                    withContext(Dispatchers.Main) {
                        onSuccess(json)
                    }
                } else {
                    onFailed()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onFailed()
            }
        }
    }

}