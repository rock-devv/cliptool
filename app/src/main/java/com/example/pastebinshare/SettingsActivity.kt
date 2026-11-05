package com.example.pastebinshare

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch

/** Launcher settings screen. */
class SettingsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settings = Settings(this)

        val openLink = findViewById<Switch>(R.id.open_link)
        openLink.isChecked = settings.openLink
        openLink.setOnCheckedChangeListener { _, checked -> settings.openLink = checked }

        val expiryDays = resources.getIntArray(R.array.expiry_days)
        val expiry = findViewById<Spinner>(R.id.expiry)
        expiry.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.expiry_options,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        expiry.setSelection(expiryDays.indexOf(settings.expiryDays).coerceAtLeast(0))
        expiry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                expiryDays.getOrNull(position)?.let { settings.expiryDays = it }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }
}
