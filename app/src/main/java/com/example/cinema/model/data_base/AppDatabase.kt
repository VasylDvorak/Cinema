package com.example.cinema.model.data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cinema.model.data_base.data_base_classes.*

@Database(
    entities = [
        Docs_db::class, ExternalId_db::class,
        Logo_db::class, MovieDTO_db::class, Names_db::class, Poster_db::class,
        Rating_db::class, Votes_db::class, Watchability_db::class
    ], version = 1, exportSchema = false)
@TypeConverters(MovieTypeConverters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun movieDTO_Dao() : MovieDTO_Dao
    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "MovieDTO_db.db").allowMainThreadQueries()
                .build() } }
            return INSTANCE }

        fun destroyInstance() { INSTANCE = null }



    }

}