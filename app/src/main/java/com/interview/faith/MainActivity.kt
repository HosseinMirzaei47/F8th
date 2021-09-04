package com.interview.faith

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import com.interview.faith.VolumeChangedReceiver.Companion.CLICKED_THREE_TIMES_EXTRA

class MainActivity : Activity() {

    var lastTimeButtonClicked = 0L
    var volumeDownClickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val wasClickedThreeTimes = intent.extras?.getBoolean(CLICKED_THREE_TIMES_EXTRA) ?: false

        if (wasClickedThreeTimes) {
            setTheme(android.R.style.Theme_Material_NoActionBar)
            setContentView(R.layout.activity_main)
            stopService(Intent(this, BackgroundSoundService::class.java))
        } else {
            startService()
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            val currentTime = System.currentTimeMillis()

            if (currentTime - 1000L < lastTimeButtonClicked)
                volumeDownClickCount++
            else
                volumeDownClickCount = 0

            if (volumeDownClickCount == 3) {
                volumeDownClickCount = 0
                startService()
                finish()
            }
            lastTimeButtonClicked = currentTime
        }
        return false
    }

    private fun startService() {
        val soundService = Intent(this, BackgroundSoundService::class.java)
        startService(soundService)
    }

}