package com.example.cinema.model.data_base.data_base_classes
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class Docs_db(@PrimaryKey val id: UUID = UUID.randomUUID(),

                   val externalIdDb: ExternalId_db? = null,
                   val logoDb: Logo_db? = null,
                   val posterDb: Poster_db? = null,
                   val ratingDb: Rating_db? = null,
                   val votesDb: Votes_db? = null,
                   val watchabilityDb: Watchability_db? = null,
                   val id_movie: Int = 0,
                   val alternativeName: String? = "",
                   val description: String = "",
                   val enName: String? = "",
                   val movieLength: Int = 0,
                   val name: String = "",
                   val names: Names_db_list? = Names_db_list(),
                   val shortDescription: String? = "",
                   val type: String = "",
                   val year: Int = 0,
                   val releaseYears: ReleaseYears_db_list? = ReleaseYears_db_list(),
                   var isLike: Boolean = false,
                   var url_trailer: String = ""
) : Parcelable