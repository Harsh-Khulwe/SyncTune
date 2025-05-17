package io.synctune.app.exoplayer

data class PlaybackCommand(
    val type: String, // "play", "pause", "seek", "load"
    val trackId: String? = null,
    val positionMs: Long? = null,
    val timestamp: Long = System.currentTimeMillis() // for sync accuracy
)

