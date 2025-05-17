package io.synctune.app.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.synctune.app.api_networking.AudiusViewModel
import io.synctune.app.exoplayer.ExoplayerManager

@Composable
fun AudioPlayerScreen(
    context: Context,
    viewModel: AudiusViewModel,
    exoPlayerManager: ExoplayerManager
) {
    val tracks by viewModel.tracks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column {
        Text("Search Results")
        LazyColumn {
            items(tracks) { track ->
                Text(track.title)
                Button(onClick = {
                    viewModel.getStreamingUrl(track.id) { url ->
                        url?.let {
                            exoPlayerManager.initializePlayer()
                            exoPlayerManager.playStream(it)
                        }
                    }
                }) {
                    Text("Play")
                }
            }
        }
    }
}