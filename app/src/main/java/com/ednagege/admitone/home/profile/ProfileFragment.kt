package com.ednagege.admitone.home.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ednagege.admitone.EditProfileActivity
import com.ednagege.admitone.wallet.MyWalletActivity
import com.ednagege.admitone.R
import com.ednagege.admitone.utils.Preferences
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    lateinit var preferences : Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(context!!.applicationContext)

        tv_user_name.text = preferences.getValues("name")
        tv_user_email.text = preferences.getValues("email")

        if (!preferences.getValues("url").equals("")) {
            Glide.with(this)
                .load(preferences.getValues("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(iv_user_pic)
        } else {
            Glide.with(this)
                .load(R.drawable.user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_user_pic)
        }

        tvMyWallet.setOnClickListener {
            startActivity(Intent(activity, MyWalletActivity::class.java))
        }

        tvEditProfile.setOnClickListener {
            startActivity(Intent(activity, EditProfileActivity::class.java))
        }
    }

}