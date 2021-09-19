package com.example.nagwatask.main.connections

import com.example.nagwatask.main.dataclass.Video
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Api  {
    @GET("movies")
    fun getAllVids(): Single<List<Video>>
}