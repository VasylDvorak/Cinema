package com.example.cinema.model.room_data_base

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long=0,
    var alternativeName: String="",
    var description: String="",
    var id_server: Int=0,
    var movieLength: Int=0,
    var name: String="",
    var rating_kp: Double=0.0,
    var rating_russianFilmCritics: Int =0,
    var poster_url: String="",
    var shortDescription: String="",
    var type: String="",
    var year: Int=0,
    var current_request: Int = 0,
    var watched: Int = 0,
    var isLike: Int = 0,
    var url_trailer: String = "",
    var note: String = ""

)


