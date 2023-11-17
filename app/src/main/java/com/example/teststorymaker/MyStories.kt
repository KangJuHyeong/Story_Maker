package com.example.teststorymaker

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.teststorymaker.RetrofitClient.apiService
import com.example.teststorymaker.databinding.ActivityMyStoriesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException

class MyStories : AppCompatActivity() {
    lateinit var binding: ActivityMyStoriesBinding
    var data: ArrayList<MyStoryData> = ArrayList()
    private lateinit var topNavFragment: TopNavFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initRecyclerView()
    }

    fun initRecyclerView() {
//        binding.myStoriesRecyclerView.layoutManager= LinearLayoutManager(this,
//            LinearLayoutManager.VERTICAL,false)
        binding.myStoriesRecyclerView.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        val adapter = MyStoriesAdapter(data)
        binding.myStoriesRecyclerView.adapter = adapter
        adapter.itemClickListener = object : MyStoriesAdapter.OnItemClickListener {
            override fun OnItemClick(data: MyStoryData, position: Int) {
                val call = RetrofitClient.apiService.getStoryData(data.storyID.toString())
                call.enqueue(object : Callback<List<StoryResponse>> {
                    override fun onResponse(
                        call: Call<List<StoryResponse>>,
                        response: Response<List<StoryResponse>>
                    ) {
                        if (response.isSuccessful) {
                            val storyResponse: List<StoryResponse>? = response.body()
                            try {
                                val intent = Intent(this@MyStories, StoryProgress::class.java)
                                val file = File(
                                    applicationContext.filesDir,
                                    "${data.storyID}story_text0.txt"
                                )
                                if (!file.exists()) {
                                    // 이미지를 내부 저장소에 저장
                                    for((index,now) in storyResponse!!.withIndex()){
                                        saveTextToFile(now.text, index, data.storyID)
                                        downloadAndSaveImage(now.image, index, data.storyID)
                                    }
                                } else {
                                    if (storyResponse != null) {
                                        intent.putExtra("pageSize", storyResponse.size)
                                        intent.putExtra("storyId", data.storyID)
                                    }
                                }
                                startActivity(intent)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            // 서버 응답이 실패한 경우
                            val errorBody = response.errorBody()?.string()
                            // 에러 메시지 등을 처리
                        }
                    }

                    override fun onFailure(call: Call<List<StoryResponse>>, t: Throwable) {
                        // 네트워크 호출이 실패한 경우
                        t.printStackTrace()
                    }
                })

            }

        }

    }

    fun initData() {
        topNavFragment = TopNavFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_nav, topNavFragment)
            .commit()
        val call = RetrofitClient.apiService.getStories()
        call.enqueue(object : Callback<List<AllStoryDataResponse>> {
            override fun onResponse(
                call: Call<List<AllStoryDataResponse>>,
                response: Response<List<AllStoryDataResponse>>
            ) {
                if (response.isSuccessful) {
                    val storyResponse: List<AllStoryDataResponse>? = response.body()
                    try {
                        val db = MyRoomDB.getInstance(applicationContext)
                        db!!.MyStoryDAO().deleteAll()
                        if (storyResponse != null) {
                            for (now in storyResponse) {
                                val text = now.title
                                val image = now.content
                                val storyId = now.id

                                db?.MyStoryDAO()
                                    ?.insertStory(MyStoryData(text, storyId.toInt(), image))

                            }
                        }
                        val list = db!!.MyStoryDAO().getAll()

                        for (now in list) {
                            data.add(MyStoryData(now.text, now.storyID, now.imgURL))
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    // 서버 응답이 실패한 경우
                    val errorBody = response.errorBody()?.string()
                    // 에러 메시지 등을 처리
                }
            }

            override fun onFailure(call: Call<List<AllStoryDataResponse>>, t: Throwable) {
                // 네트워크 호출이 실패한 경우
                Log.d("서버","안열림")
                t.printStackTrace()
            }
        })

    }

    private fun saveTextToFile(text: String, index: Int, storyId: Int) {
        val file = File(applicationContext.filesDir, "story_text.txt")

        try {
            // 파일에 데이터 쓰기
            val writer = FileWriter(file)
            writer.use {
                it.write(text)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun downloadAndSaveImage(imageUrl: String, index: Int, storyId: Int) {
        try {
            // Glide나 Picasso 등을 사용하여 이미지 다운로드
            val bitmap = Glide.with(applicationContext)
                .asBitmap()
                .load(imageUrl)
                .submit()
                .get()

            // 내부 저장소에 이미지 저장
            saveImageToFile(bitmap, index, storyId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveImageToFile(bitmap: Bitmap, index: Int, storyId: Int) {
        val fileName = "${storyId}story_image${index}.jpg"
        val file = File(applicationContext.filesDir, fileName)

        try {
            // 이미지를 파일에 저장
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}