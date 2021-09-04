package com.interview.faith

import android.app.Service
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.IBinder
import com.interview.faith.VolumeChangedReceiver.Companion.CLICKED_THREE_TIMES_EXTRA

class ActionReceiverService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getBooleanExtra(CLICKED_THREE_TIMES_EXTRA, true)?.let {
            startMainActivityWithData(it)
        }
        return START_STICKY
    }

    private fun startMainActivityWithData(data: Boolean) {
        val activityIntent = Intent(this, MainActivity::class.java)
        activityIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        activityIntent.putExtra(CLICKED_THREE_TIMES_EXTRA, data)
        startActivity(activityIntent)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}