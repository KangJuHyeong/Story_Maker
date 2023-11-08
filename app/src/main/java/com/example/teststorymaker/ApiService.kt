package com.example.teststorymaker

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("your_endpoint_here")
    suspend fun getStoryData(@Query("story_id") storyId: String): Call<StoryResponse>

    @POST("api/endpoint")
    suspend fun sendData(@Body data: SubmitInform): Response<InformResponse>
}