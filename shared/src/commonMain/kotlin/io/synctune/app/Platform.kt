package io.synctune.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform