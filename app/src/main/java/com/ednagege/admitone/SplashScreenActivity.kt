package com.ednagege.admitone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ednagege.admitone.onboarding.OnBoarding1Activity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        var handler = Handler()

        handler.postDelayed({
            var intent = Intent(this@SplashScreenActivity, OnBoarding1Activity::class.java)
            startActivity(intent)
            finish()

        },  800)
    }
}