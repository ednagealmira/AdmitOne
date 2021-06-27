package com.ednagege.admitone.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ednagege.admitone.R
import com.ednagege.admitone.sign.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_on_boarding2.*

class OnBoarding2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding2)

        btnSkip.setOnClickListener {
            finishAffinity()
            var intent = Intent(this@OnBoarding2Activity, SignInActivity::class.java)
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            var intent = Intent(this@OnBoarding2Activity, OnBoarding3Activity::class.java)
            startActivity(intent)
        }

    }
}