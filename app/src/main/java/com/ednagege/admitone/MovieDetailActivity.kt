package com.ednagege.admitone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ednagege.admitone.checkout.PickSeatActivity
import com.ednagege.admitone.home.HomeActivity
import com.ednagege.admitone.home.dashboard.PlayAdapter
import com.ednagege.admitone.home.model.Film
import com.ednagege.admitone.home.model.Play
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<Play>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // ambil data yang dikirimkan dari dashboardfragment
        val data = intent.getParcelableExtra<Film>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
            .child(data.judul.toString())
            .child("play")

        tvMovieTitle.text = data.judul
        tvMovieGenre.text = data.genre
        tvStoryboardDesc.text = data.desc
        tvRating.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(iv_poster)

        rvWhosPlayed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()

        btnPickSeats.setOnClickListener {
            var intent = Intent(this@MovieDetailActivity, PickSeatActivity::class.java).putExtra("data", data)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MovieDetailActivity, ""+p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                dataList.clear()

                for (getdataSnapshot in p0.children) {
                    var Film = getdataSnapshot.getValue(Play::class.java)
                    dataList.add(Film!!)
                }

                rvWhosPlayed.adapter = PlayAdapter(dataList) {}
            }
        })
    }
}