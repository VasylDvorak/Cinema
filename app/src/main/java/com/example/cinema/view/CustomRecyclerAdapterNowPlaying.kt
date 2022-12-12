package com.example.cinema.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R

class CustomRecyclerAdapterNowPlaying(
    private val title: List<String>,
    private val year: List<String>,
    private val rating: List<String>,
    private val picture: List<Int>,
    private val listener: OnItemClickListenerNow
) : RecyclerView
.Adapter<CustomRecyclerAdapterNowPlaying.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val now_playing_title_movie_text: TextView =
            itemView.findViewById(R.id.now_playing_title_movie)
        val now_playing_year_movie_text: TextView =
            itemView.findViewById(R.id.now_playing_year_movie)
        val now_playing_rating_movi_text: TextView =
            itemView.findViewById(R.id.now_playing_rating_movie)
        val now_playing_banner_picture: ImageView = itemView.findViewById(R.id.now_playing_banner)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClickNowPlaying(position)
            }
        }

    }

    interface OnItemClickListenerNow {
        fun onItemClickNowPlaying(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.now_playing_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.now_playing_title_movie_text.text = title[position]
        holder.now_playing_year_movie_text.text = year[position]
        holder.now_playing_rating_movi_text.text = rating[position]
        holder.now_playing_banner_picture.setImageResource(picture[position])
    }

    override fun getItemCount() = title.size
}


