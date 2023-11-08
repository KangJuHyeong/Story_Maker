package com.example.teststorymaker

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyStoryDAO {

    @Query("SELECT * FROM story_data")
    fun getAll(): List<MyStoryData>

    // 저장 - 중복 값 충돌 발생 시 새로 들어온 데이터로 교체.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStory(story: MyStoryData)

    // 삭제
    @Delete
    fun deleteStory(story: MyStoryData)
}