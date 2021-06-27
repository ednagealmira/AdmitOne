package com.ednagege.admitone.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ednagege.admitone.R
import com.ednagege.admitone.utils.Preferences
import com.ednagege.admitone.wallet.adapter.WalletAdapter
import com.ednagege.admitone.wallet.model.Wallet
import kotlinx.android.synthetic.main.activity_my_wallet.*
import kotlinx.android.synthetic.main.activity_my_wallet.btnTopUp
import kotlinx.android.synthetic.main.activity_my_wallet.tvBalance
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MyWalletActivity : AppCompatActivity() {

    private lateinit var preferences: Preferences

    private var dataList = ArrayList<Wallet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

        preferences = Preferences(this)
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

        if (preferences.getValues("balance").equals("")) {
            preferences.setValues("balance", "0")
        }

        tvBalance.text = formatRupiah.format(preferences.getValues("balance")!!.toDouble())

        dataList.add(
            Wallet(
                "X-Men: Dark Phoenix",
                "Saturday, 18 January 2020",
                100000.0,
                "0"
            )
        )
        dataList.add(
            Wallet(
                "Top Up",
                "Monday, 13 January 2020",
                250000.0,
                "1"
            )
        )
        dataList.add(
            Wallet(
                "Wreck-It Ralph 2",
                "Tuesday, 03 December 2019",
                180000.0,
                "0"
            )
        )
        dataList.add(
            Wallet(
                "Top Up",
                "Tuesday, 03 December 2019",
                200000.0,
                "1"
            )
        )

        rvTransaction.layoutManager = LinearLayoutManager(this)
        rvTransaction.adapter = WalletAdapter(dataList) {
        }

        btnTopUp.setOnClickListener {
            startActivity(Intent(this, MyWalletTopUpActivity::class.java))
        }

        btnBack.setOnClickListener {
            finish()
        }

    }
}