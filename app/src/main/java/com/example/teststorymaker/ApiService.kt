package com.example.teststorymaker

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("your_endpoint_here")
    suspend fun getStoryData(@Query("story_id") storyId: String): Call<StoryResponse>

    @POST("prompts/getPrompt")
    suspend fun sendData(@Body data: SubmitInform): Response<InformResponse>

    @FormUrlEncoded
    @POST("prompts/getPrompt")
    suspend fun sendData2(@FieldMap params: Map<String, String>): Response<InformResponse>

    @FormUrlEncoded
    @POST("prompts/getPrompt")
    suspend fun sendData3(@Field("name") name:String,@Field("sex") sex:String,
                          @Field("age") age:String,@Field("personality") personality:String,
                          @Field("name2") name2:String,@Field("subject") subject:String
    ): Response<InformResponse>
}