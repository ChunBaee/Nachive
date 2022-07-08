package com.chuno.nachive.write

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chuno.nachive.R
import com.chuno.nachive.databinding.ActivityWriteContentBinding
import com.chuno.nachive.write.util.ImageRvAdapter
import com.chuno.nachive.write.util.WriteViewModel
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class WriteActivity : AppCompatActivity() {
    val REQUEST_GALLERY = 1000

    private lateinit var binding: ActivityWriteContentBinding
    private val viewModel by viewModels<WriteViewModel>()
    private val imageAdapter by lazy {
        ImageRvAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_content)

        binding.view = this

        checkPreference()
        setUi()
        observe()

        Log.d("----", "onCreate: CALLED")
    }

    fun getDate(): String {
        return SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(Calendar.getInstance().time)
            .toString()
    }

    fun getTime() : String {
        return SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Calendar.getInstance().time)
    }

    fun getGalleryData() {
        if (viewModel.writePhotoList.value?.size?.equals(4) == true) {
            Toast.makeText(applicationContext, "사진은 최대 4장까지 올릴 수 있어요!", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
            startActivityForResult(intent, REQUEST_GALLERY)
        }
    }

    fun saveContent() {
        uploadToFirebase()

        //임시저장된 내용 지우기
        deletePreferences()
        viewModel.resetList()
    }

    private fun uploadToFirebase() {
        val pref = getSharedPreferences("NarchiveUserSetting", MODE_PRIVATE)
        val userId = pref.getString("UserID", "")!!
        viewModel.uploadToFirebase(userId, getTime(), binding.writeEdtContent.text.toString())
    }

    private fun deletePreferences() {
        val pref = getSharedPreferences("NarchiveSavedContent", MODE_PRIVATE)
        pref.edit().putString("SavedImages", "").putString("content", "").apply()
    }

    private fun checkPreference() {
        val pref = getSharedPreferences("NarchiveSavedContent", MODE_PRIVATE)
        val savedContent = pref.getString("content", null)
        if(savedContent != null || getFromPreference().size == 0) {
            Toast.makeText(applicationContext, "다이얼로그 띄우기", Toast.LENGTH_SHORT).show()
        }
        if (savedContent != null) {
            binding.writeEdtContent.setText(savedContent)
        }
        viewModel.getFromPreference(getFromPreference())
        Log.d("----", "onStart: ${getFromPreference()}")
    }

    private fun observe() {
        viewModel.writePhotoList.observe(this) {
            Log.d("----", "observe: $it")
            imageAdapter.setUrlList(it)
        }
    }

    private fun setUi() {
        binding.writeRvGalleryImage.hasFixedSize()
        binding.writeRvGalleryImage.adapter = imageAdapter

        imageAdapter.deleteClickListener(object : ImageRvAdapter.DeleteClickListener {
            override fun deleteClickListener(view: View, position: Int) {
                viewModel.deletePhotoList(position)
            }

        })
    }

    private fun saveToPreference() {
        val pref = getSharedPreferences("NarchiveSavedContent", MODE_PRIVATE)
        pref.edit().putString("content", binding.writeEdtContent.text.toString()).apply()
        if (viewModel.writePhotoList.value?.isNotEmpty() == true) {
            pref.edit().putString("SavedImages", convertArrayToJson(viewModel.writePhotoList.value!!).toString()).apply()
        }
    }

    private fun convertArrayToJson(images: MutableList<String>) : JSONArray {
        val jsonArray = JSONArray()
        for(i in images) {
            jsonArray.put(i)
        }
        return jsonArray
    }

    private fun getFromPreference() : MutableList<String>{
        val pref = getSharedPreferences("NarchiveSavedContent", MODE_PRIVATE)
        val savedContent = pref.getString("SavedImages", null)
        var urls = mutableListOf<String>()
        if(savedContent != null) {
            try {
                val json = JSONArray(savedContent)
                for(i in 0 until json.length()) {
                    urls.add(json.optString(i))
                }
            } catch (e : java.lang.Exception) {
                Log.d("ERROR", "getFromPreference: $e")
            }
        }
        return urls
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_GALLERY -> {
                //저장하기
                viewModel.putPhotoList((data!!.data as Uri).toString())
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveToPreference()
    }
}