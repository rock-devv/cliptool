package com.example.pastebinshare

import android.app.Activity
import android.os.Bundle

/** Invisible PROCESS_TEXT handler. Always finishes with RESULT_CANCELED. */
class ShareActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_CANCELED)
        finish()
    }
}
