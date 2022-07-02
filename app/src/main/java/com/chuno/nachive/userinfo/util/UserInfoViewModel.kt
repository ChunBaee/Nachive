package com.chuno.nachive.userinfo.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chuno.nachive.R

class UserInfoViewModel : ViewModel() {

    val isNickEmpty = MutableLiveData<Int>(R.color.set_profile_save_before_finish)

    fun changeSaveBtnColor(isEmpty : Boolean) {
        if(isEmpty) {
            isNickEmpty.value = R.color.set_profile_save_before_finish
        } else {
            isNickEmpty.value = R.color.app_background_color
        }
    }
}