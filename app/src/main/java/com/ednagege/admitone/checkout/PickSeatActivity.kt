package com.ednagege.admitone.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ednagege.admitone.MovieDetailActivity
import com.ednagege.admitone.R
import com.ednagege.admitone.checkout.model.Checkout
import com.ednagege.admitone.home.HomeActivity
import com.ednagege.admitone.home.model.Film
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_pick_seat.*
import kotlinx.android.synthetic.main.activity_pick_seat.btnBack
import kotlinx.android.synthetic.main.activity_pick_seat.tvMovieTitle

class PickSeatActivity : AppCompatActivity() {

    var statusA1 : Boolean = false
    var statusA2 : Boolean = false
    var statusA3 : Boolean = false
    var statusA4 : Boolean = false
    var statusB1 : Boolean = false
    var statusB2 : Boolean = false
    var statusB3 : Boolean = false
    var statusC3 : Boolean = false
    var statusC4 : Boolean = false
    var total : Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_seat)

        val data = intent.getParcelableExtra<Film>("data")
        tvMovieTitle.text = data.judul

        btnBack.setOnClickListener {
            finish()
        }

        seatA1.setOnClickListener{
            if (statusA1) {
                seatA1.setImageResource(R.drawable.ic_seat_empty)
                statusA1 = false
                total -= 1
                buyTicket(total)
                // delete data
                dataList.remove(Checkout("A1", "50000"))
            } else {
                seatA1.setImageResource(R.drawable.ic_seat_selected)
                statusA1 = true
                total += 1
                buyTicket(total)

                val data = Checkout("A1", "50000")
                dataList.add(data)
            }
        }

        seatA2.setOnClickListener{
            if (statusA2) {
                seatA2.setImageResource(R.drawable.ic_seat_empty)
                statusA2 = false
                total -= 1
                buyTicket(total)
                // delete data
                dataList.remove(Checkout("A2", "50000"))
            } else {
                seatA2.setImageResource(R.drawable.ic_seat_selected)
                statusA2 = true
                total += 1
                buyTicket(total)

                val data = Checkout("A2", "50000")
                dataList.add(data)
            }
        }

        seatA3.setOnClickListener{
            if (statusA3) {
                seatA3.setImageResource(R.drawable.ic_seat_empty)
                statusA3 = false
                total -= 1
                buyTicket(total)
                // delete data
                dataList.remove(Checkout("A1", "50000"))
            } else {
                seatA3.setImageResource(R.drawable.ic_seat_selected)
                statusA3 = true
                total += 1
                buyTicket(total)

                val data = Checkout("A3", "50000")
                dataList.add(data)
            }
        }

        seatA4.setOnClickListener{
            if (statusA4) {
                seatA4.setImageResource(R.drawable.ic_seat_empty)
                statusA4 = false
                total -= 1
                buyTicket(total)
                // delete data
                dataList.remove(Checkout("A4", "50000"))
            } else {
                seatA4.setImageResource(R.drawable.ic_seat_selected)
                statusA4 = true
                total += 1
                buyTicket(total)

                val data = Checkout("A4", "50000")
                dataList.add(data)
            }
        }

        seatB1.setOnClickListener{
            if (statusB1) {
                seatB1.setImageResource(R.drawable.ic_seat_empty)
                statusB1 = false
                total -= 1
                buyTicket(total)
                // delete data
                dataList.remove(Checkout("B1", "50000"))
            } else {
                seatB1.setImageResource(R.drawable.ic_seat_selected)
                statusB1 = true
                total += 1
                buyTicket(total)

                val data = Checkout("B1", "50000")
                dataList.add(data)
            }
        }

        seatB2.setOnClickListener{
            if (statusB2) {
                seatB2.setImageResource(R.drawable.ic_seat_empty)
                statusB2 = false
                total -= 1
                buyTicket(total)
                // delete data
                dataList.remove(Checkout("B2", "50000"))
            } else {
                seatB2.setImageResource(R.drawable.ic_seat_selected)
                statusB2 = true
                total += 1
                buyTicket(total)

                val data = Checkout("B2", "50000")
                dataList.add(data)
            }
        }

        seatB3.setOnClickListener{
            if (statusB3) {
                seatB3.setImageResource(R.drawable.ic_seat_empty)
                statusB3 = false
                total -= 1
                buyTicket(total)
                // delete data
                dataList.remove(Checkout("B3", "50000"))
            } else {
                seatB3.setImageResource(R.drawable.ic_seat_selected)
                statusB3 = true
                total += 1
                buyTicket(total)

                val data = Checkout("B3", "50000")
                dataList.add(data)
            }
        }

        seatC3.setOnClickListener{
            if (statusC3) {
                seatC3.setImageResource(R.drawable.ic_seat_empty)
                statusC3 = false
                total -= 1
                buyTicket(total)
                // delete data
                dataList.remove(Checkout("C3", "50000"))
            } else {
                seatC3.setImageResource(R.drawable.ic_seat_selected)
                statusC3 = true
                total += 1
                buyTicket(total)

                val data = Checkout("C3", "50000")
                dataList.add(data)
            }
        }

        seatC4.setOnClickListener{
            if (statusC4) {
                seatC4.setImageResource(R.drawable.ic_seat_empty)
                statusC4 = false
                total -= 1
                buyTicket(total)
            } else {
                seatC4.setImageResource(R.drawable.ic_seat_selected)
                statusC4 = true
                total += 1
                buyTicket(total)

                val data = Checkout("C4", "50000")
                dataList.add(data)
            }
        }

        btnBuyTicket.setOnClickListener {
            var intent = Intent(this, CheckoutActivity::class.java).putExtra("data", dataList)
            startActivity(intent)
        }

    }

    private fun buyTicket(total: Int) {
        if (total == 0) {
            btnBuyTicket.setText("Buy Ticket")
            btnBuyTicket.visibility = View.INVISIBLE
        } else {
            btnBuyTicket.setText("Buy Ticket ("+total+")")
            btnBuyTicket.visibility = View.VISIBLE
        }
    }
}