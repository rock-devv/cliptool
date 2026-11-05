package com.example.pastebinshare

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.io.InterruptedIOException
import java.util.concurrent.TimeUnit

/**
 * Uploads pastes to dpaste.com with its public API.
 *
 * See https://dpaste.com/api/v2/: a form-encoded POST with `content` and
 * `expiry_days` returns 201 and the new item's URL as the response body.
 */
class DpasteProvider(
    private val client: OkHttpClient = defaultClient(),
    private val endpoint: String = ENDPOINT,
) : PasteProvider {

    override suspend fun upload(text: String, expiryDays: Int): String = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(endpoint)
            .header("User-Agent", USER_AGENT)
            .post(
                FormBody.Builder()
                    .add("content", text)
                    .add("expiry_days", expiryDays.toString())
                    .build()
            )
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw PasteException("server returned HTTP ${response.code}")
                }
                val url = response.body?.string()?.trim().orEmpty()
                    .ifEmpty { response.header("Location").orEmpty() }
                if (!url.startsWith("https://")) {
                    throw PasteException("server did not return a link")
                }
                url
            }
        } catch (e: InterruptedIOException) {
            throw PasteException("upload timed out", e)
        } catch (e: IOException) {
            throw PasteException("network unavailable", e)
        }
    }

    companion object {
        const val ENDPOINT = "https://dpaste.com/api/v2/"

        private const val USER_AGENT = "cliptool/1.0 (Android)"

        private fun defaultClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)
            .build()
    }
}
