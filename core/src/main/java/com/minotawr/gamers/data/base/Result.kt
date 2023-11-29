package com.minotawr.gamers.data.base

sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>: Result<T>()

    class Unauthorized<T>(message: String?): Result<T>(message = message)

    class Failed<T>(message: String?, data: T? = null): Result<T>(data, message)

    class Success<T>(data: T?): Result<T>(data)
}