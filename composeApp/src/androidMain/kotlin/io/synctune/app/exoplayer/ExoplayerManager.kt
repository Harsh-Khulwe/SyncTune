package io.synctune.app.exoplayer

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class ExoplayerManager(private val context: Context) {

    private var exoPlayer: ExoPlayer? = null

    fun initializePlayer() {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build()
        }
    }

    fun playStream(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }
    }

    fun pause() {
        exoPlayer?.pause()
    }

    fun resume() {
        exoPlayer?.play()
    }

    fun releasePlayer() {
        exoPlayer?.release()
        exoPlayer = null
    }

    fun getPlayer(): ExoPlayer? = exoPlayer
}