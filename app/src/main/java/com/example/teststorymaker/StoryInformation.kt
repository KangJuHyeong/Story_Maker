package com.example.teststorymaker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.teststorymaker.MainActivity.Companion.preferences
import com.example.teststorymaker.databinding.ActivityStoryInformationBinding


class StoryInformation : AppCompatActivity() {

    private lateinit var viewModel: StoryInformViewModel
    private lateinit var binding: ActivityStoryInformationBinding
    private lateinit var topNavFragment: TopNavFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()

    }
    fun initLayout(){
        topNavFragment= TopNavFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_nav,topNavFragment)
            .commit()

        val repository= StoryInformRepository()
        val viewModelFactory= StoryInformViewModelFactory(repository)
        viewModel=ViewModelProvider(this,viewModelFactory).get(StoryInformViewModel::class.java)

        val status=preferences.getString("status","0")
        if(status == "0"){
            val fragment=supportFragmentManager.beginTransaction()
            val informFragment=InformFragment()
            fragment.replace(binding.StoryInformFrameLayout.id,informFragment)
            fragment.commit()
        }else {
            val fragment=supportFragmentManager.beginTransaction()
            val informFragment=WaitingFragment()
            fragment.replace(binding.StoryInformFrameLayout.id,informFragment)
            fragment.commit()
        }
        Log.d("status",status.toString())


    }

}