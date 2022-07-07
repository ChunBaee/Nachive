package com.chuno.nachive.write

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chuno.nachive.R
import com.chuno.nachive.databinding.ActivityWriteContentBinding
import com.chuno.nachive.write.data.WriteRvGalleryData
import com.chuno.nachive.write.util.ImageRvAdapter
import java.text.SimpleDateFormat
import java.util.*

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteContentBinding

    private val imageAdapter by lazy {
        ImageRvAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_content)

        binding.view = this

        setUi()
    }

    fun getDate(): String {
        return SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(Calendar.getInstance().time)
            .toString()
    }

    private fun setUi() {
        binding.writeRvGalleryImage.hasFixedSize()
        binding.writeRvGalleryImage.adapter = imageAdapter
        imageAdapter.setUrlList(examList())
    }

    private fun examList(): MutableList<WriteRvGalleryData> {
        var list = mutableListOf<WriteRvGalleryData>()
        list.add(WriteRvGalleryData("https://w.namu.la/s/4396b04782e7968e1a494d46dc5bcd7d6d3f5979b64c0a434960bff3228040eb1bc08794fab9695fb976ac47fdcaf21048c75b32c557a22e539bd7901a32dd9255710f71ec958c6f5e33adf8f0e30d89fa2c102899defb135c85c2398673e473"))
        list.add(WriteRvGalleryData("https://w.namu.la/s/4396b04782e7968e1a494d46dc5bcd7d6d3f5979b64c0a434960bff3228040eb1bc08794fab9695fb976ac47fdcaf21048c75b32c557a22e539bd7901a32dd9255710f71ec958c6f5e33adf8f0e30d89fa2c102899defb135c85c2398673e473"))
        list.add(WriteRvGalleryData("https://w.namu.la/s/4396b04782e7968e1a494d46dc5bcd7d6d3f5979b64c0a434960bff3228040eb1bc08794fab9695fb976ac47fdcaf21048c75b32c557a22e539bd7901a32dd9255710f71ec958c6f5e33adf8f0e30d89fa2c102899defb135c85c2398673e473"))
        return list
    }
}