package com.example.cinema.model.serch_name_movie_model

import android.os.Parcelable
import com.example.cinema.model.retrofit.new_API.new_model_movie_information.Genre
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Docs(

    var alternativeName: String?="",
    var description: String?="",
    var enName: String?="",
    var externalId: ExternalId?= ExternalId(),
    var id: Int?=0,
    var logo: Logo?=Logo(),
    var movieLength: Int?=0,
    var name: String?="",
    var names: List<Name>? = listOf(),
    var poster: Poster?= Poster(),
    var rating: Rating?= Rating(),
    var releaseYears: MutableList<ReleaseYear>? = mutableListOf(),
    var shortDescription: String?="",
    var type: String?="",
    var votes: Votes? = Votes(),
    var watchability: Watchability? = Watchability(),
    var year: Int?=0,
    var current: Boolean? = false,
    var watched: Boolean? = false,
    var isLike: Boolean? = false,
    var url_trailer: String? = "",
    var note: String? = "",
    var genre: String?=""

) : Parcelable