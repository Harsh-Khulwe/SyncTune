package io.synctune.app.data

import java.lang.Exception

sealed class Result<out T> {
    data class Success<out T> (val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
}