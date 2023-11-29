package com.minotawr.gamers.data.remote

import com.minotawr.gamers.core.BuildConfig
import com.minotawr.gamers.data.base.Result
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseRemoteDataSource {

    protected val apiKey = BuildConfig.API_KEY

    suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call.invoke()
            val responseCode = response.code()

            return if (response.isSuccessful) {
                val body = response.body()

                if (body != null)
                    Result.Success(body)
                else Result.Failed("Response body returns null")
            } else {
                when (responseCode) {
                    401 ->
                        Result.Unauthorized(response.message())

                    400, 500 ->
                        Result.Failed(response.message())

                    else -> Result.Failed("Server is unavailable at the moment.")
                }
            }
        } catch (e: Exception) {
            return if (e is UnknownHostException || e is ConnectException || e is SocketTimeoutException)
                Result.Failed("Connection failed")
            else Result.Failed(e.message ?: "Request failed")
        }
    }

}