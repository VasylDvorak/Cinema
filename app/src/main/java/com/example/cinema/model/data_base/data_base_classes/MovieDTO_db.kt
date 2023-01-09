package com.example.cinema.model.data_base.data_base_classes

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class MovieDTO_db(@PrimaryKey val id: UUID = UUID.randomUUID(),

                       val docs: Docs_db_list? =Docs_db_list(),
                       val total: Int = 1,
                       val limit: Int = 1,
                       val page: Int = 1,
                       val pages: Int = 1,

                       ) : Parcelable


fun nowPlaying(): MovieDTO_db = MovieDTO_db()

fun upcoming(): MovieDTO_db = MovieDTO_db()
