package com.example.cinema.model.data_base.data_base_classes

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Docs_db(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,

    val externalIdDb: ExternalId_db = ExternalId_db(),
    val logoDb: Logo_db = Logo_db(),
    val posterDb: Poster_db = Poster_db(),
    val ratingDb: Rating_db = Rating_db(),
    val votesDb: Votes_db = Votes_db(),
    val watchabilityDb: Watchability_db? = Watchability_db(),
    val id_movie: Int = 0,
    val alternativeName: String = "",
    val description: String = "",
    val enName: String = "",
    val movieLength: Int = 0,
    val name: String = "",
    val names: MutableList<Names_db> = mutableListOf(),
    val shortDescription: String = "",
    val type: String = "",
    val year: Int = 0,
    val releaseYears: MutableList<String> = mutableListOf(),
    var isLike: Boolean = false,
    var url_trailer: String = ""
) : Parcelable