package io.synctune.app.api_networking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AudiusViewModel(
    private val repository: AudiusRepository
) : ViewModel() {

    private val _tracks = MutableStateFlow<List<AudiusTrack>>(emptyList())
    val tracks: StateFlow<List<AudiusTrack>> = _tracks

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun searchTracks(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.searchRoyaltyFreeSongs(query)
                _tracks.value = result
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getStreamingUrl(trackId: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val url = repository.getStreamingUrl(trackId)
                onResult(url)
            } catch (e: Exception) {
                onResult(null)
                _error.value = "Failed to load streaming URL"
            }
        }
    }
}
