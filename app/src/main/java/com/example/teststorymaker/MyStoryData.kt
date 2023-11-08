package com.example.teststorymaker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story_data")
data class MyStoryData(
    var text: String,
    @PrimaryKey
    var storyID: Int) {
}