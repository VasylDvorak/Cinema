package com.example.cinema.view.favorite_movie_fragment

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.viewmodel.FavoriteMovieFragmentViewModel
import com.squareup.picasso.Picasso

class FavoriteMovieFragmentAdapter(
    private var onItemViewClickListener: OnItemViewClickListener?,
    private var removeMovie: removeMovieListener?,
    private var viewModel: FavoriteMovieFragmentViewModel
) :
    RecyclerView.Adapter<FavoriteMovieFragmentAdapter.MainViewHolder>() {

    private var aboutMovie: List<Docs> = listOf()

    interface removeMovieListener {
        fun removeMovieClick(like: Boolean, aboutMovieItem: Docs, position: Int)
    }

    interface OnItemViewClickListener {
        fun onItemClick(aboutMovie: Docs)
    }

    fun setAboutMovie(data: MutableList<Docs>) {
        aboutMovie = data
        notifyDataSetChanged()
    }

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
                                    resources.getText(com.example.cinema.R.string.rating)
                                            as String + " " + kp.toString()
                                findViewById<TextView>(R.id.details_budget_movie).text =
                                    resources.getText(com.example.cinema.R.string.budget)
                                            as String + " " +
                                            (russianFilmCritics * 10000) + " $"
                                findViewById<TextView>(R.id.details_revenue_movie).text =
                                    resources.getText(com.example.cinema.R.string.revenue)
                                            as String + " " + (await * 10000) + " $"
                            }
                        }

                        var strr = ""
                        aboutMovieItem.poster?.let {
                            strr = poster.url

                            if((strr != "") && (strr != null)){
                            Picasso.get().load(strr).transform(PicassoTransformation())
                                .into(findViewById<ImageView>(R.id.details_banner_movie))
                        }}

                        var delete: ImageView = findViewById(R.id.delete)

                        delete.apply {

                            setOnClickListener {

                                removeMovie?.removeMovieClick(
                                    false, aboutMovieItem,
                                    absoluteAdapterPosition
                                )

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
                R.layout.favorite_item,
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

    private fun showWatched(b: Boolean, watched: TextView) {
        if (b) {
            watched.visibility = View.VISIBLE
        } else {
            watched.visibility = View.GONE
        }
    }
}