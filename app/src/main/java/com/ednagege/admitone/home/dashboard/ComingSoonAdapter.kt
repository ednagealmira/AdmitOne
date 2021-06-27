package com.ednagege.admitone.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ednagege.admitone.R
import com.ednagege.admitone.home.model.Film

class ComingSoonAdapter(private var data: List<Film>,
                        private val listener:(Film) -> Unit)
    : RecyclerView.Adapter<ComingSoonAdapter.LeagueViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_coming_soon, parent, false)
        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tvMovieTitle)
        private val tvGenre: TextView = view.findViewById(R.id.tvMovieGenre)
        private val tvRating: TextView = view.findViewById(R.id.tvRating)
        private val imgPoster: ImageView = view.findViewById(R.id.iv_poster)

        fun bindItem(data: Film, listener: (Film) -> Unit, context: Context, position: Int) {
            tvTitle.text = data.judul
            tvGenre.text = data.genre
            tvRating.text = data.rating

            Glide.with(context)
                .load(data.poster)
                .into(imgPoster)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

}
