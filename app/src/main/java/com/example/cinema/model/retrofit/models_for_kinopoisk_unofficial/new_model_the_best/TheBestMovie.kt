package com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_the_best

data class TheBestMovie(
    val items: MutableList<Item> = mutableListOf(),
    val total: Int = 0,
    val totalPages: Int =0
)