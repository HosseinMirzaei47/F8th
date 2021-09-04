package com.interview.faith

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager


class VolumeChangedReceiver : BroadcastReceiver() {

    private var lastClickTime = 0L
    private var volumeDownClickCount = 0

    override fun onReceive(context: Context?, intent: Intent?) {

        val currentTime = System.currentTimeMillis()

        if (INTENT_ACTION_VOLUME_CHANGED == intent?.action && currentTime - 20L > lastClickTime) {

            val volumePrev = intent.getIntExtra(PREV_VOLUME_STREAM_VALUE, MAX_VOLUME)
            val volume = intent.getIntExtra(VOLUME_STREAM_VALUE, MAX_VOLUME)

            if (volumePrev > volume) {

                if (currentTime - 1000L < lastClickTime)
                    volumeDownClickCount++
                else
                    volumeDownClickCount = 0

                if (volumeDownClickCount == 3) {
                    sendDataToActionReceiver(context = context)
                    volumeDownClickCount = 0
                }
            }
            lastClickTime = currentTime

            if (volume == MAX_VOLUME) {
                changeVolume(context, AudioManager.ADJUST_LOWER)
            } else if (volume == MIN_VOLUME) {
                changeVolume(context, AudioManager.ADJUST_RAISE)
            }
        }
    }

    private fun changeVolume(context: Context?, adjust: Int) {
        val audioManager: AudioManager =
            context?.applicationContext?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustVolume(adjust, AudioManager.FLAG_PLAY_SOUND)
    }

    private fun sendDataToActionReceiver(context: Context?) {
        val serviceIntent = Intent(context, ActionReceiverService::class.java)
        serviceIntent.putExtra(CLICKED_THREE_TIMES_EXTRA, true)
        context?.startService(serviceIntent)
    }

    companion object {
        const val CLICKED_THREE_TIMES_EXTRA = "clicked_three_times"
        const val INTENT_ACTION_VOLUME_CHANGED = "android.media.VOLUME_CHANGED_ACTION"
        const val PREV_VOLUME_STREAM_VALUE = "android.media.EXTRA_PREV_VOLUME_STREAM_VALUE"
        const val VOLUME_STREAM_VALUE = "android.media.EXTRA_VOLUME_STREAM_VALUE"
        const val MAX_VOLUME = 15
        const val MIN_VOLUME = 0
    }
}