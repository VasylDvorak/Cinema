package com.example.cinema.model.data_base.data_base_classes

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class ExternalId_db(@PrimaryKey(autoGenerate = true) val id: Int? = null,

                         val kpHD: String = "",
                         val imdb: String = "",
                         val _id: String = ""
) : Parcelable