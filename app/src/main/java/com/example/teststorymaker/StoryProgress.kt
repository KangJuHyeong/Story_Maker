package com.example.teststorymaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide.init
import com.example.teststorymaker.databinding.ActivityStoryProgressBinding

class StoryProgress : AppCompatActivity() {
    lateinit var binding: ActivityStoryProgressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryProgressBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_story_progress)
        init()
    }
    fun init(){
        val storyArray = intent.getStringArrayExtra("storyArray")

        // null 체크 후 사용
        if (storyArray != null) {
            // 배열에 데이터가 있다면 처리
            binding.storyText.text = storyArray.get(0).toString()
        }
    }
}