package io.synctune.app.api_networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AudiusApiService {

    @GET("v1/tracks/search")
    suspend fun searchTracks(
        @Query("query") query: String,
        @Query("app_name") appName: String = "SyncTune"
    ): AudiusSearchResponse

    @GET("v1/tracks/{id}/stream")
    suspend fun getStreamUrl(
        @Path("id") id: String,
        @Query("app_name") appName: String = "SyncTune"
    ): retrofit2.Response<Void> // you extract `.raw().request.url.toString()`

    companion object {
        fun create(baseUrl: String = "https://audius-discovery-1.cultur3stake.com/"): AudiusApiService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AudiusApiService::class.java)
        }
    }
}