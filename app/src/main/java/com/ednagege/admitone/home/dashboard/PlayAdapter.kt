package com.ednagege.admitone.home.dashboard

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ednagege.admitone.R
import com.ednagege.admitone.home.model.Play

class PlayAdapter(private var data: List<Play>,
                  private val listener:(Play) -> Unit)
    : RecyclerView.Adapter<PlayAdapter.LeagueViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_play, parent, false)
        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvPlayerName: TextView = view.findViewById(R.id.tvPlayerName)
        private val imgPlayer: ImageView = view.findViewById(R.id.ivPlayer)

        fun bindItem(data: Play, listener: (Play) -> Unit, context: Context, position: Int) {
            tvPlayerName.text = data.nama
            Log.v("mermaid", data.nama)

            Glide.with(context)
                .load(data.url)
                .apply(RequestOptions.circleCropTransform())
                .into(imgPlayer)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

}
