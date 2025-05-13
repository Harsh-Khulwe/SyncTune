package io.synctune.app.api_networking

class AudiusRepository(private val apiService: AudiusApiService) {

    suspend fun searchRoyaltyFreeSongs(query: String): List<AudiusTrack> {
        return try {
            val response = apiService.searchTracks(query)
            response.data
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getStreamingUrl(trackId: String): String? {
        return try {
            val response = apiService.getStreamUrl(trackId)
            if (response.isSuccessful) {
                // The actual streaming URL is the final resolved URL
                response.raw().request().url().toString()
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}