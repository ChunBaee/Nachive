package com.chuno.nachive.write.util

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chuno.nachive.R
import com.chuno.nachive.databinding.ItemWriteRvImageBinding
import com.chuno.nachive.write.data.WriteRvGalleryData

class ImageRvAdapter : RecyclerView.Adapter<ImageRvAdapter.ImageRvViewHolder>() {
    private var urlList = mutableListOf<WriteRvGalleryData>()

    inner class ImageRvViewHolder(val binding : ItemWriteRvImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : WriteRvGalleryData) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageRvViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemWriteRvImageBinding>(layoutInflater, R.layout.item_write_rv_image, parent,false)
        return ImageRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageRvViewHolder, position: Int) {
        holder.bind(urlList[position])
    }

    override fun getItemCount(): Int {
        return urlList.size
    }

    fun setUrlList(list : MutableList<WriteRvGalleryData>) {
        urlList = list
        notifyDataSetChanged()
    }
}