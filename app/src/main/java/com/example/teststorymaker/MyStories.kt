package com.example.teststorymaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teststorymaker.databinding.ActivityMainBinding
import com.example.teststorymaker.databinding.ActivityMyStoriesBinding

class MyStories : AppCompatActivity() {
    lateinit var binding: ActivityMyStoriesBinding
    var data: ArrayList<dataMyStories> = ArrayList()
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
        data.add(dataMyStories("text1",1))
        data.add(dataMyStories("text2",2))
        data.add(dataMyStories("text3",3))
        data.add(dataMyStories("text4",4))
        data.add(dataMyStories("text5",3))
        data.add(dataMyStories("text6",4))

    }
}