package com.chuno.nachive

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.chuno.nachive.databinding.ActivitySplashBinding
import com.chuno.nachive.login.LoginActivity
import com.chuno.nachive.userinfo.UserInfoActivity
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

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
            Thread.sleep(500)
            checkUser()
        }.start()
    }

    private fun checkUser() {
        FirebaseFirestore.getInstance().collection("Acceptable").whereEqualTo(
            getSharedPreferences("NarchiveUserSetting", MODE_PRIVATE).getString("UserID", "")
                .toString(), true).get()
            .addOnSuccessListener {
                for(i in it) {
                    Log.d("KAKAO", "checkUser: ${i.data.values}")
                    if(i.data.values.contains(true)) {
                        startActivity(Intent(this, UserInfoActivity::class.java))
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }
            }
    }
}