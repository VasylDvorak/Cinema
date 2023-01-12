package com.example.cinema.model.halk

data class Rating(
    val _id: String,
    val await: Double,
    val filmCritics: Double,
    val imdb: Double,
    val kp: Double,
    val russianFilmCritics: Int
)