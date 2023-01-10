package com.example.cinema.model.data_base


import android.content.Context
import android.os.AsyncTask
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cinema.model.data_base.data_base_classes.Docs_db
import com.example.cinema.model.data_base.data_base_classes.MovieDTO_db
import com.example.cinema.model.data_base.data_base_classes.Names_db

class MovieDTO_repository (context: Context) {

    var db: MovieDTO_Dao = AppDatabase.getInstance(context)?.movieDTO_Dao()!!



    fun getMovieDTO_db_list(): MutableList<MovieDTO_db> = db.getMovieDTO_db_list()
    fun getDocs_db_list(): MutableList<Docs_db> = db.getDocs_db_list()
    fun getNames_db_list(): MutableList<Names_db> = db.getNames_db_list()


    fun insertMovieDTO_db(movieDTO_db: MovieDTO_db) { insertAsyncTaskMovieDTO_db(db).execute(movieDTO_db) }
    fun insertDocs_db(docs_db: Docs_db) { insertAsyncTaskDocs_db(db).execute(docs_db) }
    fun insertNames_db(names_db: Names_db) { insertAsyncTaskNames_db(db).execute(names_db) }







    fun updateMovieDTO_db(movieDTO_db: MovieDTO_db){db.updateMovieDTO_db(movieDTO_db)}
    fun updateDocs_db(docs_db: Docs_db){db.updateDocs_db(docs_db)}
    fun updateNames_db(names_db: Names_db){db.updateNames_db(names_db)}



    fun deleteMovieDTO_db(movieDTO_db: MovieDTO_db){db.deleteMovieDTO_db(movieDTO_db)}
    fun deleteDocs_db(docs_db: Docs_db){db.deleteDocs_db(docs_db)}
    fun deleteNames_db(names_db: Names_db){db.deleteNames_db(names_db)}






    private class insertAsyncTaskMovieDTO_db internal constructor(private val movieDTO_Dao: MovieDTO_Dao) :
        AsyncTask<MovieDTO_db, Void, Void>() {

        override fun doInBackground(vararg params: MovieDTO_db): Void? {
            movieDTO_Dao.insertMovieDTO_db(params[0])
            return null
            }
        }

    private class insertAsyncTaskDocs_db internal constructor(private val movieDTO_Dao: MovieDTO_Dao) :
        AsyncTask<Docs_db, Void, Void>() {

        override fun doInBackground(vararg params: Docs_db): Void? {
            movieDTO_Dao.insertDocs_db(params[0])
            return null
        }
    }

    private class insertAsyncTaskNames_db internal constructor(private val movieDTO_Dao: MovieDTO_Dao) :
        AsyncTask<Names_db, Void, Void>() {

        override fun doInBackground(vararg params: Names_db): Void? {
            movieDTO_Dao.insertNames_db(params[0])
            return null
        }
    }




}