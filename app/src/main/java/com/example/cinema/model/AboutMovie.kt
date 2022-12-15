package com.example.cinema.model

import com.example.cinema.R

data class AboutMovie(
    val movie: Movie = getDefaultMovie(),
    val release_date: String = "27.11.2018",
    val rating: String = "5.5"
)

fun getDefaultMovie() = Movie("Человек паук", R.drawable.spiderman)