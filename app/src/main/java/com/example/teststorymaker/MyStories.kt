package com.example.teststorymaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teststorymaker.databinding.ActivityMyStoriesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        binding.myStoriesRecyclerView.adapter=MyStoriesAdapter(data)
    }

    fun initData() {
        topNavFragment= TopNavFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_nav,topNavFragment)
            .commit()
//        testData
        CoroutineScope(Dispatchers.IO).launch{
            val db=MyRoomDB.getInstance(this@MyStories)
            val list=db!!.MyStoryDAO().getAll()
            for(now in list) {
                data.add(MyStoryData(now.text, now.storyID))
            }
        }


    }
}