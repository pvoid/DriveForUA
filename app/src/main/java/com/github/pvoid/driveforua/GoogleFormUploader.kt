package com.github.pvoid.driveforua

import android.os.Handler
import android.os.Looper
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.io.IOException

private val client = OkHttpClient()
private val callbackHandler = Handler(Looper.getMainLooper())

interface GoogleFormUploaderCallback {
    fun onSuccess()
    fun onError(code: Int, message: String)
    fun onException(e: IOException)
}

class GoogleFormUploader(
    private val id: String
) {
    private val items = mutableMapOf<String, String>()

    fun add(id: String, data: String) {
        items[id] = data
    }

    fun upload(callback: GoogleFormUploaderCallback) {
        val url = "https://docs.google.com/forms/d/e/${id}/formResponse".toHttpUrl()
        val data = items.asSequence().fold(FormBody.Builder()) { acc, entry ->
            acc.addEncoded("entry.${entry.key}", entry.value)
            acc
        }.build()

        val request = Request.Builder()
            .url(url)
            .post(data)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callbackHandler.post {
                    callback.onException(e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                callbackHandler.post {
                    if (response.isSuccessful) {
                        callback.onSuccess()
                    } else {
                        callback.onError(response.code, response.message)
                    }
                }
            }
        })
    }
}