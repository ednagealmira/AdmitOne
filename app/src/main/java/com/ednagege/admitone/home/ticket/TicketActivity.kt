package com.ednagege.admitone.home.ticket

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ednagege.admitone.R
import com.ednagege.admitone.checkout.model.Checkout
import com.ednagege.admitone.home.model.Film
import kotlinx.android.synthetic.main.activity_ticket.*

class TicketActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        var data = intent.getParcelableExtra<Film>("data")

        tvMovieTitle.text = data.judul
        tvMovieGenre.text = data.genre
        tvRating.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(iv_poster)

        rvItems.layoutManager = LinearLayoutManager(this)
        dataList.add(Checkout("B1", ""))
        dataList.add(Checkout("B2", ""))

        rvItems.adapter = TicketAdapter(dataList) {}

        btnBack.setOnClickListener {
            finish()
        }

        iv_base_qr.setOnClickListener {
            showDialog("Please scan this QR Code at the nearest ticket counter.")
        }

    }

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_qr_scan)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val tvDesc = dialog.findViewById(R.id.tv_scan_qr) as TextView
        tvDesc.text = title

        val btnClose = dialog.findViewById(R.id.btn_close) as Button
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
}