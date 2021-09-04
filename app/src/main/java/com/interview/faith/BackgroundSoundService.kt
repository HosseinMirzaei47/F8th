package com.interview.faith

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.interview.faith.VolumeChangedReceiver.Companion.INTENT_ACTION_VOLUME_CHANGED

class BackgroundSoundService : Service() {

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        registerVolumeChangedReceiver()
        return START_STICKY
    }

    private fun registerVolumeChangedReceiver() {
        broadcastReceiver = VolumeChangedReceiver()
        val filter = IntentFilter()
        filter.addAction(INTENT_ACTION_VOLUME_CHANGED)
        registerReceiver(broadcastReceiver, filter)
    }

    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, ActionReceiverService::class.java))
        unregisterReceiver(broadcastReceiver)
    }

}