package com.chuno.nachive.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

class LoginRepository {

    fun kakaoLogin(context : Context, mAuthToken : MutableLiveData<String>) {
        UserApiClient.instance.loginWithKakaoTalk(context = context, requestCode = 1000) { token, error ->
            if(error != null) {
                Log.e("KAKAO", "loginKakao: 로그인 실패, $error")
                mAuthToken.value = "ERROR"
            } else if(token != null) {
                Log.i("KAKAO", "loginKakao: 로그인 성공, ${token.accessToken}")
                mAuthToken.value = token.accessToken
            }
        }
    }
    fun naverLogin(context : Context, mAuthToken: MutableLiveData<String>) {
        val naverLoginCallback = object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d("NAVER", "onFailure: ${NaverIdLoginSDK.getLastErrorCode()}")
            }

            override fun onSuccess() {
                mAuthToken.value = NaverIdLoginSDK.getAccessToken()
            }
        }

        NaverIdLoginSDK.authenticate(callback = naverLoginCallback, context = context)
    }
}