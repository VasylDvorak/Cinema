package com.example.cinema.model.data_base

import androidx.room.TypeConverter
import com.example.cinema.model.data_base.data_base_classes.*
import com.google.gson.Gson
import java.util.*

class CrimeTypeConverters {
    @TypeConverter
    fun fromDocs_db_list(docs: Docs_db_list?): String? {
        return Gson().toJson(docs)
    }
    @TypeConverter
    fun toDocs_db_list(millisSinceEpoch: String?): Docs_db_list? {
        return millisSinceEpoch?.let {
            Gson().fromJson(it, Docs_db_list::class.java)
        }
    }

    @TypeConverter
    fun fromDocs_db(docs: Docs_db?): String? {
        return Gson().toJson(docs)
    }
    @TypeConverter
    fun toDocs_db(millisSinceEpoch: String?): Docs_db? {
        return millisSinceEpoch?.let {
            Gson().fromJson(it, Docs_db::class.java)
        }
    }

    @TypeConverter
    fun fromExternalId_db(externalIdDb: ExternalId_db?): String? {
        return Gson().toJson(externalIdDb)
    }
    @TypeConverter
    fun toExternalId_db(millisSinceEpoch: String?): ExternalId_db? {
        return millisSinceEpoch?.let {
            Gson().fromJson(it, ExternalId_db::class.java)
        }
    }

    @TypeConverter
    fun fromLogo_db(logoDb: Logo_db?): String? {
        return Gson().toJson(logoDb)
    }
    @TypeConverter
    fun toLogo_db(millisSinceEpoch: String?): Logo_db? {
        return millisSinceEpoch?.let {
            Gson().fromJson(it, Logo_db::class.java)
        }
    }

    @TypeConverter
    fun fromPoster_db(posterDb: Poster_db?): String? {
        return Gson().toJson(posterDb)
    }
    @TypeConverter
    fun toPoster_db(millisSinceEpoch: String?): Poster_db? {
        return millisSinceEpoch?.let {
            Gson().fromJson(it, Poster_db::class.java)
        }
    }


    @TypeConverter
    fun fromRating_db(ratingDb: Poster_db?): String? {
        return Gson().toJson(ratingDb)
    }
    @TypeConverter
    fun toRating_db(millisSinceEpoch: String?): Rating_db? {
        return millisSinceEpoch?.let {
            Gson().fromJson(it, Rating_db::class.java)
        }
    }

    @TypeConverter
    fun fromVotes_db(votesDb: Poster_db?): String? {
        return Gson().toJson(votesDb)
    }
    @TypeConverter
    fun toVotes_db(millisSinceEpoch: String?): Votes_db? {
        return millisSinceEpoch?.let {
            Gson().fromJson(it, Votes_db::class.java)
        }
    }

    @TypeConverter
    fun fromWatchability_db(watchabilityDb: Watchability_db?): String? {
        return Gson().toJson(watchabilityDb)
    }
    @TypeConverter
    fun toWatchability_db(millisSinceEpoch: String?): Watchability_db? {
        return millisSinceEpoch?.let {
            Gson().fromJson(it, Watchability_db::class.java)
        }
    }

    @TypeConverter
    fun fromNames_db(names: Names_db?): String? {
        return Gson().toJson(names)
    }
    @TypeConverter
    fun toNames_db(millisSinceEpoch: String?): Names_db? {
        return millisSinceEpoch?.let {
            Gson().fromJson(it, Names_db::class.java)
        }
    }


    @TypeConverter
    fun fromNames_db_list(names_db_list: Names_db_list?): String? {
        return Gson().toJson(names_db_list)
    }
    @TypeConverter
    fun toNames_db_list(millisSinceEpoch: String?): Names_db_list? {
        return millisSinceEpoch?.let {
            Gson().fromJson(it, Names_db_list::class.java)
        }
    }


    @TypeConverter
    fun fromReleaseYears_db_list(releaseYears: Names_db_list?): String? {
        return Gson().toJson(releaseYears)
    }
    @TypeConverter
    fun toReleaseYears_db_list(millisSinceEpoch: String?): ReleaseYears_db_list? {
        return millisSinceEpoch?.let {
            Gson().fromJson(it, ReleaseYears_db_list::class.java)
        }
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