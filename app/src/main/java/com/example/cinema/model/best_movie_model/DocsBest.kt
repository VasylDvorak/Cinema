package com.example.cinema.model.best_movie_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DocsBest(

    val alternativeName: String?="",
    val description: String?="",
    val enName: String?="",
    val id: Int?=0,
    val movieLength: Int?=0,
    val name: String?="",
    val poster: PosterBest? = PosterBest(),
    val rating: RatingBest? = RatingBest(),
    val shortDescription: String? = "",
    val type: String? = "",
    val year: Int?=0

) : Parcelable