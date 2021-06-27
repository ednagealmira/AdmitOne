package com.ednagege.admitone.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ednagege.admitone.R
import com.ednagege.admitone.home.HomeActivity
import kotlinx.android.synthetic.main.activity_my_wallet_top_up_success.*

class MyWalletTopUpSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet_top_up_success)

        btnHome.setOnClickListener {
            finishAffinity()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}