package com.lffq.recyclerviewtest.network

import android.media.MediaRouter
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("/v1/search")
    fun search(@Query("language") lang: String,
               @Query("category") category: String = "game",
               @Query("apiKey") key: String = "h-tB0hluUIVptfEegLeuZNcmqpwKWZ27YT-QmKF0LLE2flih"): Observable<Latest>

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): NewsService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.currentsapi.services")
                .build()

            return retrofit.create(NewsService::class.java);
        }
    }
}