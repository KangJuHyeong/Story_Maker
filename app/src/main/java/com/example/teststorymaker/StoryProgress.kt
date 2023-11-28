package com.example.teststorymaker

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.teststorymaker.databinding.ActivityStoryProgressBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException


class StoryProgress : AppCompatActivity() {
    lateinit var binding: ActivityStoryProgressBinding
    private var mediaPlayer: MediaPlayer? = null
    private var mp3Url = "@raw/song1.mp3"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
//        val pageSize = intent.getIntExtra("pageSize", 0)
//        val storyId = intent.getIntExtra("storyId", -1)
//
//        if(pageSize != 0){
//            binding.storyText.text = readTextFromFile(storyId, currentPage)
//            readImageFromFile(storyId, currentPage)
//        }
//        binding.previousBtn.setOnClickListener {
//            if(currentPage > 0){
//                currentPage--
//                binding.storyText.text = readTextFromFile(storyId, currentPage)
//                readImageFromFile(storyId, currentPage)
//            }
//        }
//        binding.nextBtn.setOnClickListener {
//            if(currentPage != pageSize-1){
//                currentPage++
//                binding.storyText.text = readTextFromFile(storyId, currentPage)
//                readImageFromFile(storyId, currentPage)
//            }
//        }

        val pageList : ArrayList<ContentItem>? = intent.getParcelableArrayListExtra("page")
        var currentPage = 0
        binding.previousBtn.isEnabled = false
        if (pageList != null) {
            Log.d("success","intent success")

            Glide.with(applicationContext)
                .load(pageList[currentPage].image)
                .into(binding.storyImage)
            binding.storyText.text = pageList[currentPage].detail

            binding.previousBtn.setOnClickListener {
                currentPage--
                Glide.with(applicationContext)
                    .load(pageList[currentPage].image)
                    .into(binding.storyImage)
                binding.storyText.text = pageList[currentPage].detail
                if(currentPage == 0)
                    binding.previousBtn.isEnabled = false
                binding.nextBtn.isEnabled = true

            }
            binding.nextBtn.setOnClickListener {
                currentPage++
                Glide.with(applicationContext)
                    .load(pageList[currentPage].image)
                    .into(binding.storyImage)
                binding.storyText.text = pageList[currentPage].detail
                if(currentPage == pageList.size-1)
                    binding.nextBtn.isEnabled = false
                binding.previousBtn.isEnabled = true
            }
        }
        else{
            Log.d("fail","intent fail")
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.song1)
//        try {
//                mediaPlayer?.create(R.raw.song1)
//                mediaPlayer?.prepareAsync() // 비동기적으로 준비
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
        binding.playBtn.setOnClickListener {
            // MP3 파일 로딩
            if(mediaPlayer!!.isPlaying){
                mediaPlayer!!.pause()
            }
            else{
                mediaPlayer?.start()
                // MediaPlayer 이벤트 리스너 설정
                mediaPlayer?.setOnPreparedListener {
                    // MediaPlayer가 준비되면 재생
                    Log.d("play", "success")
                }
            }

        }

    }

    private fun readTextFromFile(storyId: Int, pageNum: Int): String? {
        val file = File(applicationContext.filesDir, "${storyId}story_text${pageNum}.txt")
        if (!file.exists()) {
            // 파일이 존재하지 않을 경우 예외 처리
            return null
        }

        try {
            val reader = BufferedReader(FileReader(file))
            val stringBuilder = StringBuilder()
            var line: String?

            // 파일에서 한 줄씩 읽어오기
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line).append("\n")
            }

            reader.close()
            return stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    private fun readImageFromFile(storyId: Int, pageNum: Int){
        val file = File(applicationContext.filesDir, "${storyId}story_image${pageNum}.jpg")
        if(file.exists()){
            Glide.with(this)
                .load(file)
                .into(binding.storyImage)
        }
    }
}