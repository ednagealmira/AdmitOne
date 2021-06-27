package com.ednagege.admitone.home.ticket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ednagege.admitone.R
import com.ednagege.admitone.checkout.model.Checkout

class TicketAdapter(private var data: List<Checkout>,
                    private val listener:(Checkout) -> Unit)
    : RecyclerView.Adapter<TicketAdapter.LeagueViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketAdapter.LeagueViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_ticket_seat, parent, false)
        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TicketAdapter.LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvSeat: TextView = view.findViewById(R.id.tvSeat)

        fun bindItem(data: Checkout, listener: (Checkout) -> Unit, context: Context) {

            tvSeat.setText("Seat "+data.seat)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

}
