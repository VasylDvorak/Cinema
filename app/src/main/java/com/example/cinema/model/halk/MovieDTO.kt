package com.example.cinema.model.halk

data class MovieDTO(
    val docs: List<Doc>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)