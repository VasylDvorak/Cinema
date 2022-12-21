package com.example.cinema.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.model.gson_decoder.MovieDTO
import com.squareup.picasso.Picasso

class MainFragmentAdapter(private var onItemViewClickListener: OnItemViewClickListener?) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var aboutMovie: List<MovieDTO> = listOf()
    private var upcoming: Boolean = false

    interface OnItemViewClickListener {
        fun onItemClick(aboutMovie: MovieDTO)
    }

    fun setAboutMovie(data: List<MovieDTO>, upcoming: Boolean) {
        aboutMovie = data
        notifyDataSetChanged()
        this.upcoming = upcoming
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(aboutMovieItem: MovieDTO) {
            itemView.apply {
                if (aboutMovieItem.docs.size != 0) {
                findViewById<TextView>(R.id.now_playing_title_movie).text =
                    aboutMovieItem.docs[0].name
                findViewById<TextView>(R.id.now_playing_year_movie).text =
                    aboutMovieItem.docs[0].year.toString()
                findViewById<TextView>(R.id.now_playing_rating_movie).text =
                    aboutMovieItem.docs[0].year.toString()

                var strr: String = aboutMovieItem.docs[0].poster.url
                Picasso.get().load(strr).into(findViewById<ImageView>(R.id.now_playing_banner))

                if (upcoming) {
                    findViewById<TextView>(R.id.now_playing_rating_movie).visibility =
                        View.GONE
                    findViewById<ImageView>(R.id.star).visibility = View.GONE
                }

                var heart: ImageView = findViewById(R.id.is_like_movie)

                heart.apply {
                    if (!aboutMovieItem.docs[0].isLike) {
                        setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                    } else {
                        setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                    }

                    setOnClickListener {

                        if (aboutMovieItem.docs[0].isLike) {
                            setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                            aboutMovieItem.docs[0].isLike = false
                        } else {
                            setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                            aboutMovieItem.docs[0].isLike = true
                        }

                    }

                }
                setOnClickListener {
                    onItemViewClickListener?.onItemClick(aboutMovieItem)
                }

            }}
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