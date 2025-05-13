package io.synctune.app.networking

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper

object SyncPlaybackHelper {
    fun prepareAndSyncPlayback(context: Context, url: String, startTime: Long) {
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(context, Uri.parse(url))
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            val delay = startTime - System.currentTimeMillis()
            Handler(Looper.getMainLooper()).postDelayed({
                mediaPlayer.start()
            }, delay.coerceAtLeast(0))
        }
    }
}
