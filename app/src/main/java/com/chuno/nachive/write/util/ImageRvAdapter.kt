package com.chuno.nachive.write.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chuno.nachive.R
import com.chuno.nachive.databinding.ItemWriteRvImageBinding

class ImageRvAdapter : RecyclerView.Adapter<ImageRvAdapter.ImageRvViewHolder>() {
    private var urlList = mutableListOf<String>()
    private lateinit var deleteClickListener : DeleteClickListener

    interface DeleteClickListener {
        fun deleteClickListener (view : View, position : Int)
    }
    fun deleteClickListener (deleteClickListener: DeleteClickListener) {
        this.deleteClickListener = deleteClickListener
    }

    inner class ImageRvViewHolder(val binding : ItemWriteRvImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : String) {
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
        holder.binding.writeBtnGalleryDelete.setOnClickListener {
            deleteClickListener.deleteClickListener(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return urlList.size
    }

    fun setUrlList(list : MutableList<String>) {
        urlList = list
        notifyDataSetChanged()
    }
}