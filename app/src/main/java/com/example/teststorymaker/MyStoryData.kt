package com.example.teststorymaker

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story_data")
data class MyStoryData(
    var text: String,
//    var imageArray: ArrayList<Image>,
    @PrimaryKey
    var storyID: Int) {
}