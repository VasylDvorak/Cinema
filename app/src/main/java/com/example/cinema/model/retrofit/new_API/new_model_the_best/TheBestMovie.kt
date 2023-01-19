package com.example.cinema.model.retrofit.new_API.new_model_the_best

data class TheBestMovie(
    val items: MutableList<Item>? = mutableListOf(),
    val total: Int? = 0,
    val totalPages: Int? =0
)