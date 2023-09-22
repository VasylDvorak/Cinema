package com.example.cinema.view.best_movie_fragment

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinema.R
import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.viewmodel.TheBestMovieViewModel
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class TheBestMovieFragmentAdapter(
    private var onItemViewClickListener: OnItemViewClickListener?,
    private var likeClickListener: LikeClickListener?,
    private var viewModel: TheBestMovieViewModel
) :
    RecyclerView.Adapter<TheBestMovieFragmentAdapter.MainViewHolder>() {

    private var aboutMovie: List<Docs> = listOf()

    interface LikeClickListener {
        fun onLikeClick(like: Boolean, aboutMovieItem: Docs) }

    interface OnItemViewClickListener {
        fun onItemClick(aboutMovie: Docs) }

    fun setAboutMovie(data: List<Docs>) {
        aboutMovie = data
        notifyDataSetChanged() }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(aboutMovieItem: Docs) {
            itemView.apply {
                with(aboutMovieItem) {
                    aboutMovieItem.let {

                        val watched: TextView = findViewById(R.id.watched)
                        val handler = Handler()
                        Thread{
                            val b = viewModel.getWatched(aboutMovieItem)
                            handler.post { showWatched(b, watched)}
                        }.start()

                        findViewById<TextView>(R.id.details_title_movie).text =
                            aboutMovieItem.name
                        findViewById<TextView>(R.id.details_original_title_movie).text =
                            aboutMovieItem.alternativeName

                        findViewById<TextView>(R.id.details_genre_movie).text =
                            resources.getText(R.string.type)
                                    as String + " " + aboutMovieItem.type
                        findViewById<TextView>(R.id.details_duration_movie).text =
                            aboutMovieItem.movieLength.toString() + " " +
                                    resources.getText(R.string.min)
                                            as String

                        findViewById<TextView>(R.id.details_year_movie).text =
                            resources.getText(R.string.release_date)
                                    as String + " " + aboutMovieItem.year.toString()

                        aboutMovieItem.rating.let {
                            with(aboutMovieItem.rating) {
                                findViewById<TextView>(R.id.details_rating_movie).text =
                                    resources.getText(R.string.rating)
                                            as String + " " + kp.toString()
                                findViewById<TextView>(R.id.details_budget_movie).text =
                                    resources.getText(R.string.budget)
                                            as String + " " +
                                            (russianFilmCritics * 10000) + " $"
                                findViewById<TextView>(R.id.details_revenue_movie).text =
                                    resources.getText(R.string.revenue)
                                            as String + " " + (await * 10000) + " $"
                            }
                        }


                        var strr = ""
                        aboutMovieItem.poster?.let {
                            strr = poster.url

                            Glide.with(context).load(strr)
                                .apply(
                                    RequestOptions.bitmapTransform(
                                        RoundedCornersTransformation(
                                            120, 0,
                                            RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_RIGHT
                                        )
                                    )
                                )
                                .transition(GenericTransitionOptions.with(R.anim.zoom_in))
                                .into(findViewById(R.id.details_banner_movie))
                        }

                        var heart: ImageView = findViewById(R.id.heart_best)

                        heart.apply {
                            val handler = Handler()
                            Thread{
                                val b = viewModel.getLike(aboutMovieItem)
                                handler.post { showLike(b, heart)}
                            }.start()

                            setOnClickListener {

                                if (isLike) {
                                    setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                                    isLike = false
                                    likeClickListener?.onLikeClick(isLike, aboutMovieItem)

                                } else {
                                    setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
                                    isLike = true
                                    likeClickListener?.onLikeClick(isLike, aboutMovieItem)

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
                R.layout.the_best_item,
                parent, false
            ) as View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(aboutMovie[position]) }

    override fun getItemCount(): Int {
        return aboutMovie.size }

    private fun showLike(b: Boolean, heart: ImageView) {
        if (!b) {
            heart.setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
        } else {
            heart.setImageResource(R.drawable.ic_baseline_favorite_24_yellow)
        }
    }

    private fun showWatched(b: Boolean, watched: TextView) {
        if (b) {
            watched.visibility = View.VISIBLE
        } else {
            watched.visibility = View.GONE
        }
    }
}