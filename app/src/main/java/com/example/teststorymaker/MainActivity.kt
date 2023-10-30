package com.example.teststorymaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}