package com.chuno.nachive.login.util

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

class LoginRepository {
    fun kakaoLogin(context : Context, mAuthToken : MutableLiveData<String>, mAuthType : MutableLiveData<Int>) {
        UserApiClient.instance.loginWithKakaoTalk(context = context, requestCode = 1000) { token, error ->
            if(error != null) {
                Log.e("KAKAO", "loginKakao: 로그인 실패, $error")
                mAuthToken.value = "ERROR"
            } else if(token != null) {
                Log.i("KAKAO", "loginKakao: 로그인 성공, ${token.accessToken}")
                mAuthType.value = 0
                mAuthToken.value = token.accessToken
            }
        }
    }
    fun getKakaoIdKey(mAuthId : MutableLiveData<String>) {
        UserApiClient.instance.me { user, error ->
            Log.d("KAKAO", "kakaoLogin: ${user?.id}")
            mAuthId.value = "Kakao_${user?.id}"
        }
    }

    fun naverLogin(context : Context, mAuthToken: MutableLiveData<String>, mAuthType : MutableLiveData<Int>) {
        val naverLoginCallback = object : OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                Log.d("NAVER", "onFailure: ${NaverIdLoginSDK.getLastErrorCode()}")
                mAuthToken.value = "ERROR"
            }
            override fun onSuccess() {
                mAuthType.value = 1
                mAuthToken.value = NaverIdLoginSDK.getAccessToken()
            }
        }
        NaverIdLoginSDK.authenticate(callback = naverLoginCallback, context = context)
    }
    fun getNaverIdKey(mAuthId : MutableLiveData<String>) {
        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
            override fun onError(errorCode: Int, message: String) {
            }

            override fun onFailure(httpStatus: Int, message: String) {
            }
            override fun onSuccess(result: NidProfileResponse) {
                Log.d("KAKAO", "naverLogin: ${result.profile?.id}")
                mAuthId.value = "Naver_${result.profile?.id}"
            }

        })
    }

    fun googleLogin(token : String, mAuthToken: MutableLiveData<String>, mAuthType : MutableLiveData<Int>) {
        val auth = Firebase.auth
        val credential = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    mAuthToken.value = auth.currentUser?.uid
                    mAuthType.value = 2
                } else {
                    mAuthToken.value = "ERROR"
                }
            }
    }


}