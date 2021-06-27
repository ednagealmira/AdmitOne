package com.ednagege.admitone.home.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ednagege.admitone.MovieDetailActivity
import com.ednagege.admitone.R
import com.ednagege.admitone.home.model.Film
import com.ednagege.admitone.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tvName.text = preferences.getValues("name")

        //BALANCE
        if (preferences.getValues("balance").equals("")) {
            preferences.setValues("balance", "0")
        }

        currency(preferences.getValues("balance")!!.toDouble(), tvBalance)

        //IMAGES
        if (!preferences.getValues("url").equals("")) {
            Glide.with(this)
                .load(preferences.getValues("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(img_user_pic)
        } else {
            Glide.with(this)
                .load(R.drawable.user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(img_user_pic)
        }

        rvNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvComingSoon.layoutManager = LinearLayoutManager(context!!.applicationContext)
        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()

                for (getDataSnapshot in dataSnapshot.children) {
                    val film = getDataSnapshot.getValue(Film::class.java!!)
                    dataList.add(film!!)
                }

                if (dataList.isNotEmpty()) {
                    rvNowPlaying.adapter = NowPlayingAdapter(dataList) {
                        val intent = Intent(
                            context,
                            MovieDetailActivity::class.java
                        ).putExtra("data", it)
                        startActivity(intent)
                    }

                    rvComingSoon.adapter = ComingSoonAdapter(dataList) {
                        val intent = Intent(
                            context,
                            MovieDetailActivity::class.java
                        ).putExtra("data", it)
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun currency(currBalance: Double, textView: TextView) {
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textView.text = format.format(currBalance as Double)
    }

}