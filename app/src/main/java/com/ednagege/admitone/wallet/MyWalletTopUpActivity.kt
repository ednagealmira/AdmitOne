package com.ednagege.admitone.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import com.ednagege.admitone.R
import com.ednagege.admitone.utils.Preferences
import kotlinx.android.synthetic.main.activity_my_wallet_top_up.*
import java.lang.NumberFormatException
import java.text.NumberFormat
import java.util.*

class MyWalletTopUpActivity : AppCompatActivity() {

    private lateinit var preferences: Preferences

    private var status10k: Boolean = false
    private var status25k: Boolean = false
    private var status50k: Boolean = false
    private var status100k: Boolean = false
    private var status250k: Boolean = false
    private var status500k: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet_top_up)

        preferences = Preferences(this)
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

        if (preferences.getValues("balance").equals("")) {
            preferences.setValues("balance", "0")
        }

        tvBalance.text = formatRupiah.format(preferences.getValues("balance")!!.toDouble())

        initListener()

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun initListener() {
        btnTopUp.setOnClickListener {
            val intent = Intent(this, MyWalletTopUpSuccessActivity::class.java)
            startActivity(intent)
        }

        tvTopUp10.setOnClickListener {
            if (status10k) {
                deselectAmount(tvTopUp10)
            } else {
                selectAmount(tvTopUp10,"10000")
            }
        }

        tvTopUp25.setOnClickListener {
            if (status25k) {
                deselectAmount(tvTopUp25)
            } else {
                selectAmount(tvTopUp25, "25000")
            }
        }

        tvTopUp50.setOnClickListener {
            if (status50k) {
                deselectAmount(tvTopUp50)
            } else {
                selectAmount(tvTopUp50, "50000")
            }
        }

        tvTopUp100.setOnClickListener {
            if (status100k) {
                deselectAmount(tvTopUp100)
            } else {
                selectAmount(tvTopUp100, "100000")
            }
        }

        tvTopUp250.setOnClickListener {
            if (status250k) {
                deselectAmount(tvTopUp250)
            } else {
                selectAmount(tvTopUp250, "250000")
            }
        }

        tvTopUp500.setOnClickListener {
            if (status500k) {
                deselectAmount(tvTopUp500)
            } else {
                selectAmount(tvTopUp500, "500000")
            }
        }

        etTopUpAmount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                try {
                    if (s.toString().toInt() >= 10000) {
                        btnTopUp.visibility = View.VISIBLE
                    } else {
                        tvTopUp10.setTextColor(resources.getColor(R.color.colorPrimaryDark))
                        tvTopUp10.setBackgroundResource(R.drawable.shape_rectangle_outline_dark)
                        status10k = false
                        btnTopUp.visibility = View.INVISIBLE
                    }
                } catch (e: NumberFormatException) {
                    tvTopUp10.setTextColor(resources.getColor(R.color.colorPrimaryDark))
                    tvTopUp10.setBackgroundResource(R.drawable.shape_rectangle_outline_dark)
                    status10k = false
                    btnTopUp.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun selectAmount(textView: TextView, amount: String) {
        textView.setTextColor(resources.getColor(R.color.colorAccentPink))
        textView.setBackgroundResource(R.drawable.shape_rectangle_outline_pink)
        when (textView) {
            tvTopUp10 -> status10k = true
            tvTopUp25 -> status25k = true
            tvTopUp50 -> status50k = true
            tvTopUp100 -> status100k = true
            tvTopUp250 -> status250k = true
            tvTopUp500 -> status500k = true
        }

        btnTopUp.visibility = View.VISIBLE
        etTopUpAmount.setText(amount)
    }

    private fun deselectAmount(textView: TextView) {
        textView.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        textView.setBackgroundResource(R.drawable.shape_rectangle_outline_dark)
        when (textView) {
            tvTopUp10 -> status10k = false
            tvTopUp25 -> status25k = false
            tvTopUp50 -> status50k = false
            tvTopUp100 -> status100k = false
            tvTopUp250 -> status250k = false
            tvTopUp500 -> status500k = false
        }
        btnTopUp.visibility = View.INVISIBLE
        etTopUpAmount.setText("")
    }
}