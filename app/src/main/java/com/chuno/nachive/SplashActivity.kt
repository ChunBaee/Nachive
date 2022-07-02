package com.chuno.nachive

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chuno.nachive.databinding.ActivitySplashBinding
import com.chuno.nachive.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@SplashActivity, R.layout.activity_splash)

        Thread {
            binding.splashLogo.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.anim_alpha))
        }.start()

        Thread {
            binding.splashBigCloud.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.anim_from_down_to_up))
            Thread.sleep(1500)
            binding.splashSmallCloud.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.anim_from_down_to_up))
            Thread.sleep(1500)
            startActivity(Intent(this, LoginActivity::class.java))
        }.start()
    }
}