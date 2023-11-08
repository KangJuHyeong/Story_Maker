package com.example.teststorymaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teststorymaker.databinding.RowBinding

class MyStoriesAdapter (val items:ArrayList<MyStoryData>)
    : RecyclerView.Adapter<MyStoriesAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: RowBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(
            LayoutInflater.from(parent.context),parent
            , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.recyclerTextView.text=items[position].text

    }

}