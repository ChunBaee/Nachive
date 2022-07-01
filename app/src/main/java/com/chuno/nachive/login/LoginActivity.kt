package com.chuno.nachive.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chuno.nachive.R
import com.chuno.nachive.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        checkToken()
        setClickListeners()
        observe()
        binding.lifecycleOwner = this

    }

    private fun observe() {
        viewModel.mAuthToken.observe(this) {
            if (it == "ERROR") {
                Log.d("KAKAO", "observe: 로그인 실패")
            } else {
                Log.d("KAKAO", "observe: 로그인 성공 $it")
            }
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

    private fun setClickListeners() {
        binding.btnLoginKakao.setOnClickListener(this)
        binding.btnLoginNaver.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }

    private fun loginKakao() {
        viewModel.getKakaoLoginData(this)
    }

    private fun loginNaver() {
        viewModel.getNaverLoginData(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_login_kakao -> {
                loginKakao()
            }

            R.id.btn_login_naver -> {
                loginNaver()
            }

            R.id.btn_logout -> {
                NaverIdLoginSDK.logout()

                UserApiClient.instance.unlink {
                    if (it != null) {
                        Log.e("KAKAO", "onCreate: 연결 끊기 실패 $it")
                    } else {
                        Log.i("KAKAO", "onCreate: 연걸 끊기 성공. 토큰 삭제")
                    }
                }
            }
        }
    }
}