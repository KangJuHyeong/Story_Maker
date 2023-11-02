package com.example.teststorymaker

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("your_endpoint_here")
    fun getStoryData(@Query("story_id") storyId: String): Call<StoryResponse>
}