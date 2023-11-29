package com.example.teststorymaker

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.teststorymaker.databinding.ActivityStoryProgressBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class StoryProgress : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var isMusicPlaying = false
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var textToSpeech: TextToSpeech
    lateinit var binding: ActivityStoryProgressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        textToSpeech = TextToSpeech(this, this)
        init()

    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // TTS 초기화 성공
            val result = textToSpeech.setLanguage(Locale.KOREAN)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported.")
            }
        } else {
            Log.e("TTS", "Initialization failed.")
        }
    }
    private fun speakText(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
    override fun onDestroy() {
        // 액티비티 종료 시 TTS 리소스 해제
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
        super.onDestroy()
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
                if (textToSpeech.isSpeaking) {
                    textToSpeech.stop()
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
                if (textToSpeech.isSpeaking) {
                    textToSpeech.stop()
                }
            }
            val params = Bundle()
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "complete")
            binding.playBtn.setOnClickListener{
                if (textToSpeech.isSpeaking) {
                    textToSpeech.stop()
                    binding.playBtn.setBackgroundResource(R.drawable.baseline_play_circle_24)
                }else{
                    binding.playBtn.setBackgroundResource(R.drawable.baseline_stop_circle_24)
                    textToSpeech.stop()
                    textToSpeech.speak(pageList[currentPage].detail, TextToSpeech.QUEUE_FLUSH, params, "complete")
                }
                if (isMusicPlaying) {
                    // If music is playing, stop playback
                    stopMusic()
                } else {
                    // If music is not playing, start playback
                    startMusic(pageList[currentPage].music)
                }

            }
            textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener(){
                override fun onStart(p0: String?) {
                }
                override fun onDone(p0: String?) {
                    if(p0 == "complete"){
                        stopMusic()
                        binding.playBtn.setBackgroundResource(R.drawable.baseline_play_circle_24)
                    }
                }
                override fun onError(p0: String?) {
                }
            })
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
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null

        isMusicPlaying = false
    }
}