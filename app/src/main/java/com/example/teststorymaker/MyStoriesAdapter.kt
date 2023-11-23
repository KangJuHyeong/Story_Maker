package com.example.teststorymaker

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.teststorymaker.databinding.RowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyStoriesAdapter (val items:ArrayList<MyStoryData>)
    : RecyclerView.Adapter<MyStoriesAdapter.ViewHolder>(){
    interface OnItemClickListener{
        fun OnItemClick(data: MyStoryData, position:Int)
    }
    var itemClickListener:OnItemClickListener?=null
    inner class ViewHolder(val binding: RowBinding)
        : RecyclerView.ViewHolder(binding.root){
        val context = binding.root.context
        init{
            binding.recyclerImageView.setOnClickListener{
                itemClickListener?.OnItemClick(items[adapterPosition],adapterPosition)
            }
        }
        }

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

        val imageUrl = items[position].imgURL
        val imageView = holder.binding.recyclerImageView
        Glide.with(holder.context)
//            .load("https://r.tourboxtech.com/file/202305/9.png")
            .load(imageUrl)
//                .load("https://cdn.leonardo.ai/users/e47e8679-3b88-47d4-8d21-549d83dc76a9/generations/fe978785-0d0a-45f5-84d4-daf9a830b0bc/Leonardo_Vision_XL_Create_an_image_for_a_childrens_storybook_j_0.jpg?w=512")
            .into(imageView)
    }

}