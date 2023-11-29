package com.minotawr.gamers.data.base

import com.minotawr.gamers.data.local.AuthLocalDataSource
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundProcessResource<ResultType, RequestType>(
    private val authLocalDataSource: AuthLocalDataSource
) {

    private val result = flow {
        emit(Result.Loading())

        when (val response = fetchData()) {
            is Result.Success -> {
                if (isValidResponse(response)) {
                    val data = callbackResponse(response.data!!)
                    emit(Result.Success(data))
                } else {
                    emit(Result.Failed(response.message))
                }
            }

            is Result.Unauthorized -> {
                authLocalDataSource.logout()
                emit(Result.Unauthorized(response.message))
            }

            else -> {
                emit(Result.Failed(response.message))
            }
        }
    }


    abstract suspend fun fetchData(): Result<RequestType>

    abstract suspend fun callbackResponse(remoteData: RequestType): ResultType

    open fun isValidResponse(response: Result<RequestType>): Boolean =
        response.data != null

    fun asFlow() = result
}