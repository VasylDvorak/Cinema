package com.example.cinema.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.cinema.R

import com.example.cinema.model.gson_kinopoisk_API.Docs


import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class MainFragmentAdapter(private var onItemViewClickListener: OnItemViewClickListener?) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var aboutMovie: List<Docs> = listOf()
    private var upcoming: Boolean = false

    interface OnItemViewClickListener {
        fun onItemClick(aboutMovie: Docs)
    }

    fun setAboutMovie(data: List<Docs>, upcoming: Boolean) {
        aboutMovie = data
        notifyDataSetChanged()
        this.upcoming = upcoming
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(aboutMovieItem: Docs) {
            itemView.apply {
                with(aboutMovieItem) {
                    aboutMovieItem.let {
                        findViewById<TextView>(R.id.now_playing_title_movie).text =
                            aboutMovieItem.name
                        findViewById<TextView>(R.id.now_playing_year_movie).text =
                            aboutMovieItem.year.toString()
                        aboutMovieItem.rating?.let {
                            findViewById<TextView>(R.id.now_playing_rating_movie).text =
                                rating?.kp.toString()
                        }

                        var strr = ""
                        aboutMovieItem.poster?.let {
                            strr = poster!!.url

                            Glide.with( context ).load( strr )
                                .apply(bitmapTransform(
                                    RoundedCornersTransformation(120, 0,
                                    RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_RIGHT)
                                ))
                                .transition(GenericTransitionOptions.with(R.anim.zoom_in))
                                .into(findViewById(R.id.now_playing_banner))

                        }


                        if (upcoming) {
                            findViewById<TextView>(R.id.now_playing_rating_movie).visibility =
                                View.GONE
                            findViewById<ImageView>(R.id.star).visibility = View.GONE
                        }

                        var heart: ImageView = findViewById(R.id.is_like_movie)

                        heart.apply {
                            if (!isLike) {
                                setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                            } else {
                                setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                            }

                            setOnClickListener {

                                if (isLike) {
                                    setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                                    isLike = false
                                } else {
                                    setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                                    isLike = true
                                }

                            }

                        }
                        setOnClickListener {
                            onItemViewClickListener?.onItemClick(aboutMovieItem)
                        }

                    }
                }
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