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
        binding= ActivityMyStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initRecyclerView()
    }
    fun initRecyclerView(){
//        binding.myStoriesRecyclerView.layoutManager= LinearLayoutManager(this,
//            LinearLayoutManager.VERTICAL,false)
        binding.myStoriesRecyclerView.layoutManager= GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        val adapter = MyStoriesAdapter(data)
        binding.myStoriesRecyclerView.adapter=adapter
        adapter.itemClickListener = object :MyStoriesAdapter.OnItemClickListener{
            override fun OnItemClick(data: MyStoryData, position: Int) {
                val intent = Intent(this@MyStories, StoryProgress::class.java)

                CoroutineScope(Dispatchers.IO).launch {
                    val call = RetrofitClient.apiService.getStoryData(data.storyID.toString())
                    call.enqueue(object : Callback<StoryResponse> {
                        override fun onResponse(
                            call: Call<StoryResponse>,
                            response: Response<StoryResponse>
                        ) {
                            if (response.isSuccessful) {
                                val storyResponse: StoryResponse? = response.body()
                                try {
                                    // Retrofit을 사용하여 리스트를 받아옴
                                    val textList = storyResponse?.texts
                                    val imageList = storyResponse?.images

                                    val file = File(
                                        applicationContext.filesDir,
                                        "${data.storyID}story_text0.txt"
                                    )
                                    if (!file.exists()) {
                                        // 이미지를 내부 저장소에 저장
                                        if (textList != null && imageList != null) {
                                            for ((index, text) in textList.withIndex()) {
                                                saveTextToFile(text, index, data.storyID)
                                            }
                                            for ((index, imageUrl) in imageList.withIndex()) {
                                                downloadAndSaveImage(imageUrl, index, data.storyID)
                                            }
                                        }
                                    } else {
                                        if (textList != null) {
                                            intent.putExtra("pageSize", textList.size)
                                            intent.putExtra("storyId", data.storyID)
                                        }
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

                        override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                            // 네트워크 호출이 실패한 경우
                            t.printStackTrace()
                        }
                    })
                }
                startActivity(intent)
            }

        }

    }

    fun initData() {
        topNavFragment= TopNavFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_nav,topNavFragment)
            .commit()
//        CoroutineScope(Dispatchers.IO).launch {
//            val call = RetrofitClient.apiService.getStories()
//            call.enqueue(object : Callback<AllStoryDataResponse> {
//                override fun onResponse(call: Call<AllStoryDataResponse>, response: Response<AllStoryDataResponse>) {
//                    if (response.isSuccessful) {
//                        val storyResponse: AllStoryDataResponse? = response.body()
//                        try {
//                            // Retrofit을 사용하여 리스트를 받아옴
//                            val textList = storyResponse?.texts
//                            val imageList = storyResponse?.images
//                            val storyIdList=storyResponse?.story_ids
//
//                            val db=MyRoomDB.getInstance(applicationContext)
//
//
//                            if (textList != null && imageList != null && storyIdList != null) {
//                                // textList, imageList, storyIdList의 크기가 같다고 가정
//                                for (index in textList.indices) {
//                                    val text = textList[index]
//                                    val image = imageList[index]
//                                    val storyId = storyIdList[index]
//
//                                    db?.MyStoryDAO()?.insertStory(MyStoryData(text, storyId.toInt(),image))
//                                }
//                            }
//
//
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                        }
//                    } else {
//                        // 서버 응답이 실패한 경우
//                        val errorBody = response.errorBody()?.string()
//                        // 에러 메시지 등을 처리
//                    }
//                }
//                override fun onFailure(call: Call<AllStoryDataResponse>, t: Throwable) {
//                    // 네트워크 호출이 실패한 경우
//                    t.printStackTrace()
//                }
//            })
//        }

        CoroutineScope(Dispatchers.IO).launch{
            val db=MyRoomDB.getInstance(applicationContext)
            db?.MyStoryDAO()?.insertStory(MyStoryData("afsd", 1,"https://www.eastflag.co.kr/wp-content/uploads/2021/03/webpack_image_uri.png"))
            val list=db!!.MyStoryDAO().getAll()
            for(now in list) {
                data.add(MyStoryData(now.text, now.storyID,now.imgURL))
            }
        }


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