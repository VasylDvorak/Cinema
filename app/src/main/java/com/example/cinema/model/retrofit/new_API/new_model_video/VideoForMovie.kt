package com.example.cinema.model.retrofit.new_API.new_model_video

data class VideoForMovie(
    val items: MutableList<Item>? = mutableListOf(),
    val total: Int? = 0
)