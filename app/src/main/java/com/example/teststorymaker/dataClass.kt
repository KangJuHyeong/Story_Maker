package com.example.teststorymaker

data class StoryResponse(
    val images: List<String>,
    val texts: List<String>
)

data class SubmitInform(
    val name: String,
    val sex : String,
    val age: String,
    val personality : String,
    val name2 : String,
    val subject : String
)
data class InformResponse(
    val result: String
)