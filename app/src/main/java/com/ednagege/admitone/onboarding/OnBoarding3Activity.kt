package com.ednagege.admitone.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ednagege.admitone.R
import com.ednagege.admitone.sign.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_on_boarding3.*

class OnBoarding3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding3)

        btnStartAdventure.setOnClickListener {
            finishAffinity()
            var intent = Intent(this@OnBoarding3Activity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}