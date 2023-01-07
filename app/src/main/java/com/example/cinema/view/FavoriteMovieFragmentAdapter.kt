package com.example.cinema.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.cinema.R
import com.example.cinema.model.data_base.DBHelper
import com.example.cinema.model.gson_kinopoisk_API.Docs

import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class FavoriteMovieFragmentAdapter(private var onItemViewClickListener: OnItemViewClickListener?,
                                   private var removeMovie: removeMovieListener?) :
    RecyclerView.Adapter<FavoriteMovieFragmentAdapter.MainViewHolder>() {


    private var aboutMovie: List<Docs> = listOf()


    interface removeMovieListener{
        fun removeMovieClick(like: Boolean, aboutMovieItem: Docs, context: Context, position: Int)
    }

    interface OnItemViewClickListener {
        fun onItemClick(aboutMovie: Docs)
    }

    fun setAboutMovie(data: List<Docs>) {

        aboutMovie = data
        notifyDataSetChanged()

    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(aboutMovieItem: Docs) {

            itemView.apply {
                with(aboutMovieItem) {
                    aboutMovieItem.let {
                        findViewById<TextView>(R.id.details_title_movie).text =
                            aboutMovieItem.name
                        findViewById<TextView>(R.id.details_original_title_movie).text =
                            aboutMovieItem.alternativeName

                        findViewById<TextView>(R.id.details_genre_movie).text =
                            resources.getText(R.string.genere)
                                as String + " " + aboutMovieItem.type


                        findViewById<TextView>(R.id.details_duration_movie).text =
                            aboutMovieItem.movieLength.toString() + " " +
                                resources.getText(R.string.min)
                                        as String

                        findViewById<TextView>(R.id.details_year_movie).text = resources.getText(R.string.release_date)
                                as String + " " + aboutMovieItem.year.toString()


                        aboutMovieItem.rating?.let {
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
                            strr = poster!!.url

                            Glide.with( context ).load( strr )
                                .apply(bitmapTransform(
                                    RoundedCornersTransformation(120, 0,
                                        RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_RIGHT)
                                ))
                                .transition(GenericTransitionOptions.with(R.anim.zoom_in))
                                .into(findViewById(R.id.details_banner_movie))

                        }






                        var heart: ImageView = findViewById(R.id.delete)

                        heart.apply {

                            setOnClickListener {

                                removeMovie?.removeMovieClick(false, aboutMovieItem, context,
                                    absoluteAdapterPosition)

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

}