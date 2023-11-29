package com.example.teststorymaker

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.teststorymaker.databinding.ActivityStoryProgressBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class StoryProgress : AppCompatActivity() {
    private var isMusicPlaying = false
    private var mediaPlayer: MediaPlayer? = null

    lateinit var binding: ActivityStoryProgressBinding
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
                if (isMusicPlaying) {
                    // If music is playing, stop playback
                    stopMusic()
                }

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
                if (isMusicPlaying) {
                    // If music is playing, stop playback
                    stopMusic()
                }
            }
            binding.playBtn.setOnClickListener{
                if (isMusicPlaying) {
                    // If music is playing, stop playback
                    stopMusic()
                } else {
                    // If music is not playing, start playback
                    startMusic(pageList[currentPage].music)
                }

            }
        }
        else{
            Log.d("fail","intent fail")
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
    private fun startMusic(musicUrl: String) {
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            mediaPlayer?.setDataSource(musicUrl)
            mediaPlayer?.prepare()

            // 반복재생을 설정합니다.
            mediaPlayer?.isLooping = true

            mediaPlayer?.start()

            isMusicPlaying = true
            binding.playBtn.text = "중지" // 버튼 텍스트를 "중지"로 변경
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null

        isMusicPlaying = false
        binding.playBtn.text = "재생" // 버튼 텍스트를 "재생"으로 변경
    }
}