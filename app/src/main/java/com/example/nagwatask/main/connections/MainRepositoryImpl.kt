package com.example.nagwatask.main.connections

import com.example.nagwatask.main.connections.Api
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val retrofitService: Api)  {

    fun getAllVideos() = retrofitService.getAllVids()
}