package com.example.teststorymaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.teststorymaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var topNavFragment: TopNavFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }
    fun initLayout(){
        topNavFragment= TopNavFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_nav,topNavFragment)
            .commit()

        binding.mainBtn1.setOnClickListener {
            val intent = Intent(this, StoryInformation::class.java)
            startActivity(intent)
        }
        binding.mainBtn2.setOnClickListener{
            val intent = Intent(this, MyStories::class.java)
            startActivity(intent)
        }
    }
}