package com.ednagege.admitone.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.ednagege.admitone.R
import com.ednagege.admitone.home.dashboard.DashboardFragment
import com.ednagege.admitone.home.profile.ProfileFragment
import com.ednagege.admitone.home.ticket.TicketFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentHome = DashboardFragment()
        val fragmentTicket = TicketFragment()
        val fragmentProfile = ProfileFragment()

        setFragment(fragmentHome)

        iv_menu_home.setOnClickListener {
            setFragment(fragmentHome)
            changeIcon(iv_menu_home, R.drawable.ic_home_active)
            changeIcon(iv_menu_ticket, R.drawable.ic_ticket)
            changeIcon(iv_menu_profile, R.drawable.ic_profile)
        }

        iv_menu_ticket.setOnClickListener {
            setFragment(fragmentTicket)
            changeIcon(iv_menu_home, R.drawable.ic_home)
            changeIcon(iv_menu_ticket, R.drawable.ic_ticket_active)
            changeIcon(iv_menu_profile, R.drawable.ic_profile)
        }

        iv_menu_profile.setOnClickListener {
            setFragment(fragmentProfile)
            changeIcon(iv_menu_home, R.drawable.ic_home)
            changeIcon(iv_menu_ticket, R.drawable.ic_ticket)
            changeIcon(iv_menu_profile, R.drawable.ic_profile_active)
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layoutFrame, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView : ImageView, int : Int) {
        imageView.setImageResource(int)
    }
}