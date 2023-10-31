package com.example.teststorymaker

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teststorymaker.databinding.ActivityStoryInformationBinding


class StoryInformation : AppCompatActivity() {


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
        binding.informSubmitBtn.setOnClickListener{
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("로딩 중...")
            progressDialog.show()
        }

    }

}