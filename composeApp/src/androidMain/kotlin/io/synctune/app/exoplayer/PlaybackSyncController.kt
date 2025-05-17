package io.synctune.app.exoplayer

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.*

class PlaybackSyncController(private val sessionId: String) {

    private val firestore = FirebaseFirestore.getInstance()
    private val sessionRef = firestore.collection("playback_sessions").document(sessionId)

    fun sendPlayCommand(trackId: String, positionMs: Long) {
        val command = mapOf(
            "command" to "play",
            "trackId" to trackId,
            "position" to positionMs,
            "timestamp" to System.currentTimeMillis()
        )
        sessionRef.set(command, SetOptions.merge())
    }

    fun sendPauseCommand(positionMs: Long) {
        val command = mapOf(
            "command" to "pause",
            "position" to positionMs,
            "timestamp" to System.currentTimeMillis()
        )
        sessionRef.set(command, SetOptions.merge())
    }

    fun sendSeekCommand(positionMs: Long) {
        val command = mapOf(
            "command" to "seek",
            "position" to positionMs,
            "timestamp" to System.currentTimeMillis()
        )
        sessionRef.set(command, SetOptions.merge())
    }

    fun sendChangeTrackCommand(trackId: String) {
        val command = mapOf(
            "command" to "change_track",
            "trackId" to trackId,
            "position" to 0L,
            "timestamp" to System.currentTimeMillis()
        )
        sessionRef.set(command, SetOptions.merge())
    }
}
