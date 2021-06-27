package com.ednagege.admitone.checkout

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ednagege.admitone.R
import com.ednagege.admitone.checkout.adapter.CheckoutAdapter
import com.ednagege.admitone.home.ticket.TicketActivity
import com.ednagege.admitone.checkout.model.Checkout
import com.ednagege.admitone.home.model.Film
import com.ednagege.admitone.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total : Double = 0.0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)

        //menyimpan data utk checkout (seat dan harga)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>
        val data = intent.getParcelableExtra<Film>("datas")

        for (a in dataList.indices) {
            total += dataList[a].price!!.toDouble()
        }

        dataList.add(Checkout("Total Payment", total.toString()))

        btnPayNow.setOnClickListener {
            val intent = Intent(this@CheckoutActivity, CheckoutPurchaseConfirmedActivity::class.java)
            startActivity(intent)
            showNotif(data)
        }

        btnCancel.setOnClickListener {
            finish()
        }

        btnBack.setOnClickListener {
            finish()
        }

        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = CheckoutAdapter(dataList) {
        }

        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

        if(preferences.getValues("balance")!!.toDouble() >= total) {
            tvTotalBalanceDesc.text = formatRupiah.format(preferences.getValues("balance")!!.toDouble())
            btnPayNow.visibility = View.VISIBLE
            tvInsufficientBal.visibility = View.INVISIBLE

        } else {
            if (preferences.getValues("balance") == "") {
                preferences.setValues("balance", "0")
            }

            tvTotalBalanceDesc.text = formatRupiah.format(preferences.getValues("balance")!!.toDouble())
            btnPayNow.visibility = View.INVISIBLE
            tvInsufficientBal.visibility = View.VISIBLE
            tvTotalBalanceDesc.setTextColor(resources.getColor(R.color.colorAccentPink))
        }
    }

    private fun showNotif(datas: Film) {
        val NOTIFICATION_CHANNEL_ID = "admitone_notif"
        val context = this.applicationContext
        var notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channelName = "AdmitOne Notification"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

        val mIntent = Intent(this, TicketActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("data", datas)
        mIntent.putExtras(bundle)

        val pendingIntent =
            PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.logo_admitone)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.mipmap.logo_admitone
                )
            )
            .setTicker("AdmitOne notification starting")
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setLights(Color.RED, 3000, 3000)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentTitle("Payment Successful!")
            .setContentText("You've purchased a ticket for "+datas.judul+". Enjoy the movie!")

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(115, builder.build())
    }
}