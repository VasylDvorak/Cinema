package com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_movie_information

data class Film(
    val countries: MutableList<Country> = mutableListOf(),
    val description: String ="",
    val filmId: Int =0,
    val filmLength: String="",
    val genres: MutableList<Genre> = mutableListOf(),
    val nameEn: String ="",
    val nameRu: String= "",
    val posterUrl: String = "",
    val posterUrlPreview: String ="",
    val rating: String = "",
    val ratingVoteCount: Int =0,
    val type: String = "",
    val year: String = ""
)