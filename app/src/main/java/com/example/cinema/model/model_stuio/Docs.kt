package com.example.cinema.model.model_stuio

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Docs(

    var alternativeName: String="",
    var description: String="",
    var enName: String="",
    var externalId: ExternalId= ExternalId(),
    var id: Int=0,
    var logo: Logo=Logo(),
    var movieLength: Int=0,
    var name: String="",
    var names: List<Name> = listOf(),
    var poster: Poster= Poster(),
    var rating: Rating= Rating(),
    var releaseYears: MutableList<ReleaseYear> = mutableListOf(),
    var shortDescription: String="",
    var type: String="",
    var votes: Votes = Votes(),
    var watchability: Watchability = Watchability(),
    var year: Int=0,
    var isLike: Boolean = false,
    var url_trailer: String = "",
    var note: String = ""
) : Parcelable {

}