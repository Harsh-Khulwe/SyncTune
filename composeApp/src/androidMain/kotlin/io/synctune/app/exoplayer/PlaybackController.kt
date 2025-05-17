package io.synctune.app.exoplayer

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration


class PlaybackController(
    private val firestore: FirebaseFirestore,
    private val roomId: String,
    private val exoPlayer: ExoPlayer
) {

    private var listenerRegistration: ListenerRegistration? = null

    fun startListeningToCommands() {
        val docRef = firestore.collection("rooms").document(roomId)

        listenerRegistration = docRef.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null || !snapshot.exists()) return@addSnapshotListener

            val command = snapshot.getString("command")
            val timestamp = snapshot.getDouble("timestamp")?.toLong() ?: 0L
            val streamUrl = snapshot.getString("streamUrl")

            when (command) {
                "play" -> {
                    if (!streamUrl.isNullOrBlank()) {
                        val mediaItem = MediaItem.fromUri(streamUrl)
                        exoPlayer.setMediaItem(mediaItem)
                        exoPlayer.prepare()
                        exoPlayer.seekTo(timestamp)
                        exoPlayer.play()
                    }
                }
                "pause" -> {
                    exoPlayer.pause()
                }
                "resume" -> {
                    exoPlayer.seekTo(timestamp)
                    exoPlayer.play()
                }
                "seek" -> {
                    exoPlayer.seekTo(timestamp)
                }
                "stop" -> {
                    exoPlayer.stop()
                }
            }
        }
    }

    fun stopListeningToCommands() {
        listenerRegistration?.remove()
        listenerRegistration = null
    }
}
