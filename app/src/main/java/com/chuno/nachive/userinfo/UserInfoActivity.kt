package com.chuno.nachive.userinfo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.chuno.nachive.R
import com.chuno.nachive.databinding.ActivityUserInfoBinding
import com.chuno.nachive.userinfo.util.UserInfoViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.typeOf

class UserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserInfoBinding
    private val viewModel by viewModels<UserInfoViewModel>()

    private lateinit var uri: Uri

    private val REQUEST_GALLERY = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)

        binding.view = this
        binding.viewModel = viewModel

        //상태바 색깔 흰색으로 변경
        window.statusBarColor = resources.getColor(R.color.white)

        observe()
        checkUserNickname()

        binding.userInfoBtnSetProfile.isClickable = false

    }

    private fun observe() {
        viewModel.isNickEmpty.observe(this) {
            binding.userInfoBtnSetProfile.setBackgroundResource(it)
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).setType("image/*")
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    fun saveUserInfoToFirebase() {
        if (binding.userInfoProfileName.text.isNotEmpty()) {
            Toast.makeText(applicationContext, "눌림", Toast.LENGTH_SHORT).show()
            upLoadToFirebase()
        } else {
            return
        }
    }

    //이 부분은 추후에 Repository로 옮겨질 예정입니다 (MVVM 형태를 유지하기 위하여)
    private fun upLoadToFirebase() {
        val path = "Image/${getUserId()}/Profile/${getCurTime()}"

        //사진 올리는 로직
        val mStorage =
            FirebaseStorage.getInstance().reference.child(path)
        val uploadTask = mStorage.putFile(uri)

        val acceptMap = mutableMapOf<String, Any>()
        acceptMap[getUserId()] = true

        //유저정보 올리는 로직
        val userMap = mutableMapOf<String, Any>()
        userMap["NickName"] = binding.userInfoProfileName.text.toString()
        userMap["ProfilePhoto"] = path

        FirebaseFirestore.getInstance().collection("Acceptable").document(getUserId())
            .set(acceptMap)

        FirebaseFirestore.getInstance().collection("UserInfo").document("Data")
            .collection(getUserId()).document("Current").set(userMap)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    //메인화면으로 넘어가기
                }
            }
    }

    private fun getUserId(): String {
        return getSharedPreferences("NarchiveUserSetting", MODE_PRIVATE).getString("UserID", "")
            .toString()
    }

    private fun getCurTime(): String {
        return SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Calendar.getInstance().time)
            .toString()
    }

    private fun checkUserNickname() {
        binding.userInfoProfileName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.changeSaveBtnColor(s!!.isEmpty())
                binding.userInfoBtnSetProfile.isClickable = s.isNotEmpty()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //갤러리 호출 실패했을 시
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            REQUEST_GALLERY -> {
                uri = data!!.data as Uri
                Glide.with(this).load(uri).into(binding.userInfoProfileImage)
            }
        }

    }

}