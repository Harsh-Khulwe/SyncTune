package io.synctune.app.api_networking

data class AudiusTrack(
    val id: String,
    val title: String,
    val genre: String?,
    val artwork: Artwork?,
    val user: AudiusUser,
    val stream_url: String? = null
)

data class Artwork(
    val _150x150: String?,
    val _480x480: String?,
    val _1000x1000: String?
)

data class AudiusUser(
    val name: String,
    val handle: String
)

data class AudiusSearchResponse(
    val data: List<AudiusTrack>
)

