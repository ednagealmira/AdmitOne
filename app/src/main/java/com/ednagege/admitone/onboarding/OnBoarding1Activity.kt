package com.ednagege.admitone.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ednagege.admitone.R
import com.ednagege.admitone.sign.signin.SignInActivity
import com.ednagege.admitone.utils.Preferences
import kotlinx.android.synthetic.main.activity_on_boarding1.*

class OnBoarding1Activity : AppCompatActivity() {
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding1)

        preferences = Preferences(this)
        if(preferences.getValues("status").equals("1")) {
            finishAffinity() //menghapus semua page yg kita tampilkan sebelumnya
            var intent = Intent(this@OnBoarding1Activity, SignInActivity::class.java)
            startActivity(intent)
        }

        btnSkip.setOnClickListener {
            preferences.setValues("onboarding", "1")
            finishAffinity()
            var intent = Intent(this@OnBoarding1Activity, SignInActivity::class.java)
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            var intent = Intent(this@OnBoarding1Activity, OnBoarding2Activity::class.java)
            startActivity(intent)
        }

    }
}