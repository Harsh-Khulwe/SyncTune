package io.synctune.app.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.synctune.app.api_networking.AudiusTrack
import io.synctune.app.api_networking.AudiusViewModel

@Composable
fun TrackSearchScreen(viewModel: AudiusViewModel) {
    val query = remember { mutableStateOf("") }
    val trackList by viewModel.tracks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query.value,
            onValueChange = {
                query.value = it
                viewModel.searchTracks(it)
            },
            label = { Text("Search Royalty-Free Music") },
            modifier = Modifier.fillMaxWidth()
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (error != null) {
            Text("Error: $error", color = Color.Red)
        } else {
            LazyColumn {
                items(trackList) { track ->
                    TrackListItem(track = track, onClick = {
                        viewModel.getStreamingUrl(track.id) { url ->
                            if (url != null) {
                                // TODO: start streaming + sync with peer here
                            }
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun TrackListItem(track: AudiusTrack, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = track.title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = track.user.name, color = Color.Gray)
        }
    }
}
