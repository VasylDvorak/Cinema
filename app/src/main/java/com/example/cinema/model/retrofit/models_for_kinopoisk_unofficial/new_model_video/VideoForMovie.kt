package com.example.cinema.model.retrofit.models_for_kinopoisk_unofficial.new_model_video

data class VideoForMovie(
    val items: MutableList<Item> = mutableListOf(),
    val total: Int = 0
)