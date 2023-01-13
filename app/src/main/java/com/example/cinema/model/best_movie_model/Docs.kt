package com.example.cinema.model.best_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Docs(
    val alternativeName: String="",
    val description: String="",
    val enName: String="",
    val externalId: ExternalId = ExternalId(),
    val id: Int=0,
    val logo: Logo= Logo(),
    val movieLength: Int=0,
    val name: String="",
    val names: MutableList<Name> =  mutableListOf(),
    val poster: Poster = Poster(),
    val rating: Rating = Rating(),
    val releaseYears: MutableList<String> = mutableListOf(),
    val shortDescription: String = "",
    val type: String = "",
    val votes: Votes = Votes(),
    val watchability: Watchability = Watchability(),
    val year: Int=0
) : Parcelable