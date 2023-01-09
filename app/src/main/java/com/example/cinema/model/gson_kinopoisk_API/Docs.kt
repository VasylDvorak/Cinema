package com.example.cinema.model.gson_kinopoisk_API

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Docs(

    val externalId: ExternalId? = ExternalId(),
    val logo: Logo? = Logo(),
    val poster: Poster? = Poster(),
    val rating: Rating? = Rating(),
    val votes: Votes? = Votes(),
    val watchability: Watchability? = Watchability(),
    val id: Int = 0,
    val alternativeName: String? = "",
    val description: String = "",
    val enName: String? = "",
    val movieLength: Int = 0,
    val name: String = "",
    val names: MutableList<Names>? = mutableListOf(),
    val shortDescription: String? = "",
    val type: String = "",
    val year: Int = 0,
    val releaseYears: MutableList<String>? = mutableListOf(),
    var isLike: Boolean = false,
    var url_trailer: String = ""
) : Parcelable