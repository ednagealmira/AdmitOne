package com.ednagege.admitone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ednagege.admitone.home.profile.ProfileFragment
import com.ednagege.admitone.utils.Preferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.tvEditProfile

class EditProfileActivity : AppCompatActivity() {

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        preferences = Preferences(this)

        if (!preferences.getValues("url").equals("")) {
            Glide.with(this)
                .load(preferences.getValues("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        } else {
            Glide.with(this)
                .load(R.drawable.user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnSaveChanges.setOnClickListener {
            finish()
        }
    }
}