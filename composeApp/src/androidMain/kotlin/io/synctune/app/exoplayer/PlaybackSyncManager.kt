package io.synctune.app.exoplayer

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class PlaybackSyncManager(private val roomId: String) {
    private val firestore = FirebaseFirestore.getInstance()
    private val roomRef = firestore.collection("syncRooms").document(roomId)

    fun sendCommand(command: PlaybackCommand) {
        val commandMap = mapOf(
            "type" to command.type,
            "trackId" to command.trackId,
            "positionMs" to command.positionMs,
            "timestamp" to command.timestamp
        )

        roomRef.update("commands", commandMap)
            .addOnSuccessListener { Log.d("Sync", "Command sent: $command") }
            .addOnFailureListener { Log.e("Sync", "Failed to send command", it) }
    }

    fun listenForCommands(onCommandReceived: (PlaybackCommand) -> Unit): ListenerRegistration {
        return roomRef.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null || !snapshot.exists()) return@addSnapshotListener

            val commandMap = snapshot.get("commands") as? Map<*, *> ?: return@addSnapshotListener

            val command = PlaybackCommand(
                type = commandMap["type"] as? String ?: "",
                trackId = commandMap["trackId"] as? String,
                positionMs = (commandMap["positionMs"] as? Number)?.toLong(),
                timestamp = (commandMap["timestamp"] as? Number)?.toLong() ?: System.currentTimeMillis()
            )

            onCommandReceived(command)
        }
    }

}
