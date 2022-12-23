package com.example.cinema.model.gson_kinopoisk_API

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Docs(

    val externalId: ExternalId? = null,
    val logo: Logo? = null,
    val poster: Poster? = null,
    val rating: Rating? = null,
    val votes: Votes? = null,
    val watchability: Watchability? = null,
    val id: Int = 0,
    val alternativeName: String? = "",
    val description: String = "",
    val enName: String? = "",
    val movieLength: Int = 0,
    val name: String = "",
    val names: List<Names>? = null,
    val shortDescription: String? = "",
    val type: String = "",
    val year: Int = 0,
    val releaseYears: List<String>? = null,
    var isLike: Boolean = false,
    var url_trailer: String = ""
) : Parcelable