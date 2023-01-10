package com.example.cinema.model.data_base

import androidx.room.TypeConverter
import com.example.cinema.model.data_base.data_base_classes.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class MovieTypeConverters {
    @TypeConverter
    fun toMovieDTO_db_list(value: String): MutableList<MovieDTO_db> {
        val listType = object : TypeToken<MutableList<MovieDTO_db>>() {
        }.type
        return Gson().fromJson(value, listType)

    }
    @TypeConverter
    fun fromMovieDTO_db_list(list: MutableList<MovieDTO_db>): String {
        return Gson().toJson(list)
    }


    @TypeConverter
    fun toDocs_db_list(value: String): MutableList<Docs_db> {
        val listType = object : TypeToken<MutableList<Docs_db>>() {
            }.type
        return Gson().fromJson(value, listType)

    }
    @TypeConverter
    fun fromDocs_db_list(list: MutableList<Docs_db>): String {
        return Gson().toJson(list)
    }


    @TypeConverter
    fun fromExternalId_db(externalIdDb: ExternalId_db): String {
        return Gson().toJson(externalIdDb)
    }
    @TypeConverter
    fun toExternalId_db(value: String): ExternalId_db {
        return  Gson().fromJson(value, ExternalId_db::class.java)
    }

    @TypeConverter
    fun fromLogo_db(logoDb: Logo_db): String {
        return Gson().toJson(logoDb)
    }
    @TypeConverter
    fun toLogo_db(value: String): Logo_db {
        return Gson().fromJson(value, Logo_db::class.java)
    }

    @TypeConverter
    fun fromPoster_db(posterDb: Poster_db): String {
        return Gson().toJson(posterDb)
    }
    @TypeConverter
    fun toPoster_db(value: String): Poster_db {
        return Gson().fromJson(value, Poster_db::class.java)
    }


    @TypeConverter
    fun fromRating_db(ratingDb: Rating_db): String {
        return Gson().toJson(ratingDb)
    }
    @TypeConverter
    fun toRating_db(value: String): Rating_db {
        return Gson().fromJson(value, Rating_db::class.java)
    }

    @TypeConverter
    fun fromVotes_db(votesDb: Votes_db): String {
        return Gson().toJson(votesDb)
    }
    @TypeConverter
    fun toVotes_db(value: String): Votes_db {
        return Gson().fromJson(value, Votes_db::class.java)
    }

    @TypeConverter
    fun fromWatchability_db(watchabilityDb: Watchability_db): String {
        return Gson().toJson(watchabilityDb)
    }
    @TypeConverter
    fun toWatchability_db(value: String): Watchability_db {
        return Gson().fromJson(value, Watchability_db::class.java)
    }

    @TypeConverter
    fun fromNames_db(names: Names_db): String {
        return Gson().toJson(names)
    }
    @TypeConverter
    fun toNames_db(value: String): Names_db {
        return Gson().fromJson(value, Names_db::class.java)
    }


    @TypeConverter
    fun fromNames_db_list(list: MutableList<Names_db>): String {
        return Gson().toJson(list)
    }



    @TypeConverter
    fun toNames_db_list(value: String): MutableList<Names_db> {
        val listType = object : TypeToken<MutableList<Names_db>>() {
        }.type
        return Gson().fromJson(value, listType)

    }




    @TypeConverter
    fun fromReleaseYears_db_list(list: MutableList<String>): String {
        return Gson().toJson(list)
    }



    @TypeConverter
    fun toReleaseYears_db_list(value: String): MutableList<String> {
        val listType = object : TypeToken<MutableList<String>>() {
        }.type
        return Gson().fromJson(value, listType)

    }


    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}