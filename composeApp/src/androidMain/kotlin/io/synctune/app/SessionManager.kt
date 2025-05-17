package io.synctune.app

import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class SessionManager(private val firestore: FirebaseFirestore) {

    fun createSession(onSessionCreated: (String) -> Unit) {
        val sessionId = UUID.randomUUID().toString()

        val sessionData = hashMapOf(
            "created_at" to System.currentTimeMillis(),
            "active" to true
        )

        firestore.collection("sessions")
            .document(sessionId)
            .set(sessionData)
            .addOnSuccessListener {
                onSessionCreated(sessionId)
            }
            .addOnFailureListener {
                onSessionCreated("") // or handle error better
            }
    }

    fun joinSession(sessionId: String, onResult: (Boolean) -> Unit) {
        firestore.collection("sessions")
            .document(sessionId)
            .get()
            .addOnSuccessListener { document ->
                onResult(document.exists())
            }
            .addOnFailureListener {
                onResult(false)
            }
    }
}
