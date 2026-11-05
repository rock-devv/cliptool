package com.example.pastebinshare

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Invisible ACTION_PROCESS_TEXT handler: uploads the selected text, copies the
 * resulting link and optionally opens it.
 *
 * The selected text is never replaced, so the activity always finishes with
 * RESULT_CANCELED. When the intent carries no text, the clipboard is read once
 * the window has focus (Android only allows clipboard access to the focused
 * app).
 */
class ShareActivity : Activity() {

    private val scope = MainScope()
    private lateinit var settings: Settings
    private lateinit var provider: PasteProvider
    private var text: String? = null
    private var started = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_CANCELED)
        settings = Settings(this)
        provider = DpasteProvider()
        text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.toString()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!hasFocus || started) return
        started = true

        val value = text ?: clipboardText()
        if (value.isNullOrBlank()) {
            toast(getString(R.string.toast_nothing_to_upload))
            finish()
            return
        }
        upload(value)
    }

    private fun upload(text: String) {
        scope.launch {
            try {
                val url = provider.upload(text, settings.expiryDays)
                copyLink(url)
                toast(getString(R.string.toast_link_copied))
                if (settings.openLink) openLink(url)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                toast(getString(R.string.toast_upload_failed, e.message.orEmpty()))
            } finally {
                finish()
            }
        }
    }

    private fun clipboardText(): String? {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = clipboard.primaryClip ?: return null
        if (clip.itemCount == 0) return null
        return clip.getItemAt(0).coerceToText(this)?.toString()
    }

    private fun copyLink(url: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText(getString(R.string.app_name), url))
    }

    private fun openLink(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: ActivityNotFoundException) {
            toast(getString(R.string.toast_no_browser))
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}
