package com.example.teststorymaker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.activityViewModels
import com.example.teststorymaker.databinding.FragmentWaitingBinding


class WaitingFragment : Fragment() {

    var binding: FragmentWaitingBinding?=null
    val model: StoryInformViewModel by activityViewModels()
    val img: Int = R.drawable.img
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentWaitingBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply {


        }
    }
    override fun onDestroyView(){
        super.onDestroyView()
        binding=null
    }

}