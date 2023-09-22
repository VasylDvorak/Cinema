package com.example.cinema.model.serch_name_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
const val url_trailer_default = "https://www.youtube.com/watch?v=VqE8TDJTq-M"
@Parcelize
data class Docs(

    var alternativeName: String="",
    var description: String="",
    var id: Int=0,
    var movieLength: Int=0,
    var name: String="",
    var poster: Poster= Poster(),
    var rating: Rating= Rating(),
    var shortDescription: String="",
    var type: String="",
    var year: Int=0,
    var current: Boolean = false,
    var watched: Boolean = false,
    var isLike: Boolean = false,
    var url_trailer: String = url_trailer_default,
    var note: String = "",
    var country: String = "",

) : Parcelable