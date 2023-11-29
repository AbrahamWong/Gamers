package com.minotawr.gamers.di

import com.minotawr.gamers.core.BuildConfig
import com.minotawr.gamers.data.remote.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideConverterFactory() }
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get()) }

    single { provideRetrofit(get(), get()) }

    single { get<Retrofit>().create(ApiService::class.java) }
}

fun provideConverterFactory() = GsonConverterFactory.create()

fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)

fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) =
    OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()

fun provideRetrofit(converterFactory: GsonConverterFactory, client: OkHttpClient) =
    Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(client)
        .addConverterFactory(converterFactory)
        .build()
