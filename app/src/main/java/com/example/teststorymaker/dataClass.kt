package com.example.teststorymaker

import com.google.gson.annotations.SerializedName

data class StoryResponse(
    val images: List<String>,
    val texts: List<String>
)

data class SubmitInform(
    @SerializedName("name")
    val name: String,
    @SerializedName("sex")
    val sex : String,
    @SerializedName("age")
    val age: String,
    @SerializedName("personality")
    val personality : String,
    @SerializedName("name2")
    val name2 : String,
    @SerializedName("subject")
    val subject : String
)
data class InformResponse(
    @SerializedName("message")
    val result: String
)