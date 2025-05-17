package io.synctune.app

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SessionScreen(
    onHostSession: (String) -> Unit,
    onJoinSession: (String) -> Unit
) {
    var sessionInput by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val firestore = FirebaseFirestore.getInstance()
    val sessionManager = remember { SessionManager(firestore) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Welcome to SyncTune", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Button(
            onClick = {
                isLoading = true
                sessionManager.createSession { sessionId ->
                    isLoading = false
                    if (sessionId.isNotEmpty()) {
                        onHostSession(sessionId)
                    } else {
                        Toast.makeText(context, "Failed to create session", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            enabled = !isLoading
        ) {
            Text("🎧 Host a Session")
        }

        Divider()

        OutlinedTextField(
            value = sessionInput,
            onValueChange = { sessionInput = it },
            label = { Text("Enter Session ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                isLoading = true
                sessionManager.joinSession(sessionInput.trim()) { success ->
                    isLoading = false
                    if (success) {
                        onJoinSession(sessionInput.trim())
                    } else {
                        Toast.makeText(context, "Invalid Session ID", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            enabled = sessionInput.isNotBlank() && !isLoading
        ) {
            Text("🔗 Join Session")
        }
    }
}
