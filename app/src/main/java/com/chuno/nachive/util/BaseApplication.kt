package com.chuno.nachive.util

import android.app.Application
import com.chuno.nachive.R
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, resources.getString(R.string.kakao_native_app_key))
        NaverIdLoginSDK.initialize(this, resources.getString(R.string.naver_client_id), resources.getString(R.string.naver_client_secret), resources.getString(R.string.app_name))
    }
}