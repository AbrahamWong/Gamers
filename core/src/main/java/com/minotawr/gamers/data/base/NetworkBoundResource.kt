package com.minotawr.gamers.data.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = flow {
        emit(Result.Loading())

        val localData = getLocalData().first()
        if (shouldFetch(localData)) {
            val response = fetchRemoteData()
            when (response) {
                is Result.Success -> {
                    if (isValidResponse(response)) {
                        response.data?.let { saveResponse(it) }
                        emitAll(
                            getLocalData().map {
                                Result.Success(it)
                            }
                        )
                    } else {
                        emitAll(
                            getLocalData().map {
                                Result.Failed(response.message, it)
                            }
                        )
                    }
                }

                is Result.Unauthorized -> {
                    emit(Result.Unauthorized(response.message))
                }

                else -> {
                    emitAll(
                        getLocalData().map {
                            Result.Failed(response.message, it)
                        }
                    )
                }
            }
        } else {
            emitAll(
                getLocalData().map {
                    Result.Success(it)
                }
            )
        }
    }

    abstract suspend fun getLocalData(): Flow<ResultType>

    abstract fun shouldFetch(localData: ResultType?): Boolean

    abstract suspend fun fetchRemoteData(): Result<RequestType>

    abstract suspend fun saveResponse(remoteData: RequestType)

    open fun isValidResponse(response: Result<RequestType>): Boolean =
        response.data != null

    fun asFlow() = result
}