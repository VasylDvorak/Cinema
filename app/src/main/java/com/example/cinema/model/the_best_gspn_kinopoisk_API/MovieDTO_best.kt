package com.example.cinema.model.the_best_gspn_kinopoisk_API

data class MovieDTO_best(
    val docs: List<Doc>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)