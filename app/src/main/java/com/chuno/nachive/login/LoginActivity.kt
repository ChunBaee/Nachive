package com.chuno.nachive.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chuno.nachive.R
import com.chuno.nachive.databinding.ActivityLoginBinding
import com.chuno.nachive.login.util.LoginViewModel
import com.chuno.nachive.userinfo.UserInfoActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    private val pref by lazy {
        getSharedPreferences("NarchiveUserSetting", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        //XML에서 선언해둔 view, viewModel과 실질적인 연결
        binding.view = this
        binding.viewModel = viewModel

        checkToken()
        observe()
        binding.lifecycleOwner = this
    }

    private fun observe() {
        viewModel.mAuthToken.observe(this) {
            if (it == "ERROR") {
                Log.d("KAKAO", "observe: 로그인 실패")
            } else {
                Log.d("KAKAO", "observe: 로그인 성공 $it, ${viewModel.mAuthType.value}")
            }
        }
        //로그인 타입 판별 (3을 넣은 이유는 로그아웃시 Preference에서 삭제하기 위함)
        viewModel.mAuthType.observe(this) {
            when(it) {
                0 -> {
                    pref.edit().putString("LoginType", "KAKAO").apply()
                    viewModel.getKakaoUserId()
                }
                1 -> {
                    pref.edit().putString("LoginType", "NAVER").apply()
                    viewModel.getNaverUserId()
                }
                2 -> {
                    pref.edit().putString("LoginType", "GOOGLE").apply()
                    viewModel.getGoogleUserId()
                }
                3 -> pref.edit().putString("LoginType", null).apply()
            }
        }
        //UserId Preference에 저장 ex)Kakao_12345 후 유저정보 설정하는 화면으로 이동
        viewModel.mAuthId.observe(this) {
            pref.edit().putString("UserID", it).commit()
            startActivity(Intent(applicationContext, UserInfoActivity::class.java))
        }
    }

    private fun checkToken() {
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
                        Log.d("KAKAO", "checkToken: 로그인이 필요합니다")
                    } else {
                        Log.d("KAKAO", "checkToken: 에러입니다. $error")
                    }
                } else {
                    Log.d("KAKAO", "checkToken: 성공입니다. ${tokenInfo?.id} ${tokenInfo?.expiresIn}")
                }
            }
        }
    }

    fun googleLogin() {
        startActivityForResult(
            GoogleSignIn.getClient(
                this,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("380819217604-ppvs4umg9u2mr1fe2rtsjpnfja6h30kd.apps.googleusercontent.com")
                    .requestEmail().build()
            ).signInIntent, 1000
        )

    }

    fun logout() {
        Firebase.auth.signOut()
        NaverIdLoginSDK.logout()
        UserApiClient.instance.unlink {
            if (it != null) {
                Log.e("KAKAO", "onCreate: 연결 끊기 실패 $it")
            } else {
                Log.i("KAKAO", "onCreate: 연걸 끊기 성공. 토큰 삭제")
            }
        }
        viewModel.mAuthType.value = 3
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            1000 -> {
                val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    viewModel.getGoogleLoginData(account.idToken!!)
                } catch (e : ApiException) {
                }
            }
        }
    }
}