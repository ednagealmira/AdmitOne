package com.ednagege.admitone.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ednagege.admitone.R
import com.ednagege.admitone.home.HomeActivity
import kotlinx.android.synthetic.main.activity_checkout_purchase_confirmed.*

class CheckoutPurchaseConfirmedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_purchase_confirmed)

        btnHome.setOnClickListener {
            finishAffinity()
            var intent = Intent(this@CheckoutPurchaseConfirmedActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}