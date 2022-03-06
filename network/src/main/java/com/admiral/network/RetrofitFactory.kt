package com.admiral.network

import android.content.Context
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class RetrofitFactory(
    apiConfig: IApiConfig,
    context: Context,
    gson: Gson,
    okHttpTimeout: Long = OKHTTP_TIMEOUT_S,
    okhttpCacheSize: Long = OKHTTP_CACHE_SIZE
) {

    companion object {
        private const val OKHTTP_TIMEOUT_S = 15L
        private const val OKHTTP_CACHE_SIZE = 10 * 1024 * 1024L // 10 MB
    }

    private val okHttpClient = makeOkHttp(
        okHttpTimeout,
        okhttpCacheSize,
        context
    )

    val retrofit: Retrofit =
        makeRetrofit(apiConfig.baseUrl, okHttpClient, gson)

    private fun makeOkHttp(
        okHttpTimeout: Long,
        okhttpCacheSize: Long,
        context: Context
    ): OkHttpClient =
        makeOkHttpBuilder(okHttpTimeout)
            .cache(Cache(context.cacheDir, okhttpCacheSize))
            .followRedirects(true)
            .followSslRedirects(true)
            .build()

    private fun makeRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun makeOkHttpBuilder(
        okHttpTimeout: Long
    ): OkHttpClient.Builder {
        @Suppress("DEPRECATION")
        return OkHttpClient.Builder()
            .connectTimeout(okHttpTimeout, TimeUnit.SECONDS)
            .readTimeout(okHttpTimeout, TimeUnit.SECONDS)
            .writeTimeout(okHttpTimeout, TimeUnit.SECONDS).apply {
                if (BuildConfig.DEBUG) {
                    val logger = HttpLoggingInterceptor()
                    logger.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(logger)
                }
            }
    }
}