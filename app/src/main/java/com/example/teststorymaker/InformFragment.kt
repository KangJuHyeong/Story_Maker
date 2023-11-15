package com.example.teststorymaker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.teststorymaker.databinding.FragmentInformBinding


class InformFragment : Fragment() {

    var binding: FragmentInformBinding?=null
    val model: StoryInformViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentInformBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.informSubmitBtn.setOnClickListener{
            //서버에 전송
            lateinit var data: SubmitInform
            binding?.apply{
                val name =informName.text.toString()
                val sex=informSex.text.toString()
                val age=informAge.text.toString()
                val personality= informPersonality.text.toString()
                val name2= informName2.text.toString()
                val subject = informSubject.text.toString()
                data = SubmitInform(name, sex,age, personality, name2 , subject )

            }
//            baseurl 채우면 밑에 주석 치우기
            model.sendInform(data)

//            MainActivity.preferences.setString("status","1")
//            requireActivity().supportFragmentManager.beginTransaction()
//            val fragment=requireActivity().supportFragmentManager.beginTransaction()
//            fragment.addToBackStack(null)
//            val waitingFragment=WaitingFragment()
//            fragment.replace(R.id.StoryInformFrameLayout,waitingFragment)
//            fragment.commit()
        }
    }
    override fun onDestroyView(){
        super.onDestroyView()
        binding=null
    }

}