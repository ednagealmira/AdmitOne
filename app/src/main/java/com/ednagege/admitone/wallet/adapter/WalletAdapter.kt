package com.ednagege.admitone.wallet.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ednagege.admitone.R
import com.ednagege.admitone.wallet.model.Wallet
import java.text.NumberFormat
import java.util.*

class WalletAdapter(private var data: List<Wallet>,
                    private val listener:(Wallet) -> Unit)
    : RecyclerView.Adapter<WalletAdapter.LeagueViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WalletAdapter.LeagueViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_transaction, parent, false)
        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: WalletAdapter.LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tvTransactionTitle)
        private val tvDate: TextView = view.findViewById(R.id.tvTransactionDate)
        private val tvValue: TextView = view.findViewById(R.id.tvTransactionValue)

        fun bindItem(data: Wallet, listener: (Wallet) -> Unit, context: Context) {
            tvTitle.text = data.title
            tvDate.text = data.date

            val localID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance()

            // kalau statusnya 0 maka transaksi nya (-)
            if (data.status.equals("0")) {
                tvValue.text = "- "+formatRupiah.format(data.money)
            } else {
                tvValue.text = "+ "+formatRupiah.format(data.money)
                tvValue.setTextColor(Color.parseColor("#49CDDE"))
            }


            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

}
