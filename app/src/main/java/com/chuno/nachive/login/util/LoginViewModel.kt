package com.chuno.nachive.login.util

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chuno.nachive.login.util.LoginRepository

class LoginViewModel : ViewModel() {
    private val repo = LoginRepository()

    val mAuthType = MutableLiveData<Int>() //0:카카오 1:네이버 2:구글 3:로그아웃
    val mAuthToken = MutableLiveData<String>()
    val mAuthId = MutableLiveData<String>()

    fun getKakaoLoginData(context : Context) {
        Log.d("KAKAO", "getKakaoLoginData: 로그인 메서드로 들어옴")
        repo.kakaoLogin(context, mAuthToken, mAuthType)
    }
    fun getKakaoUserId() {
        repo.getKakaoIdKey(mAuthId)
    }

    fun getNaverLoginData(context : Context) {
        Log.d("NAVER", "getNaverLoginData: 로그인 메서드로 들어옴")
        repo.naverLogin(context, mAuthToken, mAuthType)
    }
    fun getNaverUserId() {
        repo.getNaverIdKey(mAuthId)
    }

    fun getGoogleLoginData(token : String) {
        repo.googleLogin(token, mAuthToken, mAuthType)
    }
    fun getGoogleUserId() {
        mAuthId.value = "Google_${mAuthToken.value}"
    }
}