package com.chuno.nachive.userinfo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chuno.nachive.R
import com.chuno.nachive.databinding.ActivityUserInfoBinding
import com.chuno.nachive.userinfo.util.UserInfoViewModel

class UserInfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserInfoBinding
    private val viewModel by viewModels<UserInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)

        binding.view = this

        observe()
        checkUserNickname()

        binding.userInfoBtnSetProfile.setOnClickListener {
            Toast.makeText(applicationContext, "눌림", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observe() {
        viewModel.isNickEmpty.observe(this) {
            binding.userInfoBtnSetProfile.setBackgroundResource(it)
        }
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

}