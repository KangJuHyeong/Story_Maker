package com.example.teststorymaker

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("prompts/getStory")
    fun getStoryData(@Field("id") storyId: String): Call<StoryResponse>

    @GET("prompts/getList")
    fun getStories(): Call<List<AllStoryDataResponse>>


    @FormUrlEncoded
    @POST("prompts/getPrompt")
    suspend fun sendData2(@FieldMap params: Map<String, String>): Response<InformResponse>

    @FormUrlEncoded
    @POST("prompts/save-token")
    fun sendToken(@Field("token") token: String): Call<TokenResponse>

}