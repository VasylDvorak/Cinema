package com.example.cinema.model.utils

import com.example.cinema.model.gson_kinopoisk_API.Docs
import com.example.cinema.model.gson_kinopoisk_API.MovieDTO

fun convertDtoToModel(movieDTO: MovieDTO): List<Docs> {
   // val fact: FactDTO = weatherDTO.fact!!
    return movieDTO.docs
}
