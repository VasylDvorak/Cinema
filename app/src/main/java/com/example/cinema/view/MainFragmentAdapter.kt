package com.example.cinema.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.model.AboutMovie

class MainFragmentAdapter(private var onItemViewClickListener: OnItemViewClickListener?) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var aboutMovie: List<AboutMovie> = listOf()
    private var upcoming: Boolean = false

    interface OnItemViewClickListener {
        fun onItemClick(aboutMovie: AboutMovie)
    }

    fun setAboutMovie(data: List<AboutMovie>, upcoming: Boolean) {
        aboutMovie = data
        notifyDataSetChanged()
        this.upcoming = upcoming

    }


    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(aboutMovie: AboutMovie) {
            itemView.findViewById<TextView>(R.id.now_playing_title_movie).text =
                aboutMovie.movie.movie_title
            itemView.findViewById<TextView>(R.id.now_playing_year_movie).text =
                aboutMovie.release_date
            itemView.findViewById<TextView>(R.id.now_playing_rating_movie).text = aboutMovie.rating
            itemView.findViewById<ImageView>(R.id.now_playing_banner)
                .setImageResource(aboutMovie.movie.picture)
            if (upcoming) {
                itemView.findViewById<TextView>(R.id.now_playing_rating_movie).visibility =
                    View.GONE
                itemView.findViewById<ImageView>(R.id.star).visibility = View.GONE
            }

            var heart: ImageView = itemView.findViewById(R.id.is_like_movie)
            if (!aboutMovie.isLike) {
                heart.setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
            } else {
                heart.setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
            }
            heart.setOnClickListener {
                if (aboutMovie.isLike) {
                    heart.setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                    aboutMovie.isLike = false
                } else {
                    heart.setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                    aboutMovie.isLike = true
                }
            }
            itemView.setOnClickListener {
                onItemViewClickListener?.onItemClick(aboutMovie)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.now_playing_item,
                parent, false
            ) as View
        )

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(aboutMovie[position])
    }

    override fun getItemCount(): Int {
        return aboutMovie.size
    }


}