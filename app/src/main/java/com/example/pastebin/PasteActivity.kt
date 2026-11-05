package com.example.pastebin

import android.app.Activity
import android.os.Bundle

/** Invisible PROCESS_TEXT handler (Phases 2-4). Always finishes with RESULT_CANCELED. */
class PasteActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_CANCELED)
        finish()
    }
}
