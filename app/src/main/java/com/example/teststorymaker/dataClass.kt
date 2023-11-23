package com.example.teststorymaker

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class StoryResponse(
    val _id: String,
    val title: String,
    val representative_image: String,
    val contents: List<ContentItem>
)

data class ContentItem(
    val image: String,
    val detail: String
)

data class AllStoryDataResponse(
    val id: String,
    val title: String,
    //img URL
    val image: String

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

data class TokenResponse(
    @SerializedName("message")
    val result: String
)