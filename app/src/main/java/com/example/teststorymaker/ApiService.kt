package com.example.teststorymaker

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("prompts/getStory")
    fun getStoryData(@Query("_id") storyId: String): Call<List<StoryResponse>>

    @GET("prompts/getList")
    fun getStories(): Call<List<AllStoryDataResponse>>


    @FormUrlEncoded
    @POST("prompts/getPrompt")
    suspend fun sendData2(@FieldMap params: Map<String, String>): Response<InformResponse>

    @FormUrlEncoded
    @POST("your_token_endpoint_here")
    fun sendToken(@Field("token") token: String): Call<TokenResponse>

}