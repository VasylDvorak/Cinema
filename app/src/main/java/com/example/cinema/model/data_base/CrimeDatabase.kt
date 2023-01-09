package com.example.cinema.model.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinema.model.data_base.data_base_classes.*

@Database(
    entities = [
        Docs_db::class, Docs_db_list::class, ExternalId_db::class,
        Logo_db::class, MovieDTO_db::class, Names_db::class,Names_db_list::class, Poster_db::class,
        Rating_db::class, ReleaseYears_db_list::class, Votes_db::class, Watchability_db::class
    ], version = 1
)
abstract class CrimeDatabase : RoomDatabase()