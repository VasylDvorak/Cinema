package com.example.cinema.model.data_base.data_base_classes

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Logo_db(@PrimaryKey(autoGenerate = true) val id: Int? = null,

                   val _id: String = "",
                   val url: String = ""
) : Parcelable