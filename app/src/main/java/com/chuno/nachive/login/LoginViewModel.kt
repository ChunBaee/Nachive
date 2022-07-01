package com.chuno.nachive.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val repo = LoginRepository()

    val mAuthToken = MutableLiveData<String>()

    fun getKakaoLoginData(context : Context) {
        repo.kakaoLogin(context, mAuthToken)
    }

    fun getNaverLoginData(context : Context) {
        repo.naverLogin(context, mAuthToken)
    }

}