package com.example.cinema.model.data_base

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Handler

import com.example.cinema.model.gson_kinopoisk_API.Docs
import com.example.cinema.model.gson_kinopoisk_API.MovieDTO
import com.google.gson.Gson


class DataBase(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        // here we have defined variables for our database

        // below is variable for database name

        private val DATABASE_NAME = "GEEKS_FOR_GEEKS"

        // below is the variable for database version

        private val DATABASE_VERSION = 1

        // below is the variable for table name

        val TABLE_NAME = "movie_table"

        // below is the variable for id column

        val ID_COL = "id"

        // below is the variable for name column

        val MOVIEDTO_COl = "MOVIEDTO"

        // below is the variable for age column

        val MOVIEDTOFAVORITE_COL = "MOVIEDTOFAVORITE"

    }

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {

        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MOVIEDTO_COl + " TEXT," +
                MOVIEDTOFAVORITE_COL + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


    fun renewCurrentMovieDTO(movieDTO: MovieDTO) {
        updateFavoriteMovie(movieDTO, MOVIEDTO_COl, MOVIEDTOFAVORITE_COL)
    }


    fun readCurrentMovieDTO(): MovieDTO? {

        return readFromDB(MOVIEDTO_COl)
    }

    fun readFavoriteMovieMovieDTO(): MovieDTO? {

        return readFromDB(MOVIEDTOFAVORITE_COL)
    }


    fun readFromDB(str: String): MovieDTO? {

        var db = this.readableDatabase
        var cursor = db.query(
            TABLE_NAME, arrayOf(str),
            null, null, null, null, null
        )

        cursor!!.moveToFirst()

        var jsonString = cursor.getString(0)


        val gson = Gson()


        var movieDTO = gson.fromJson(jsonString, MovieDTO::class.java)
        cursor.close()
        db.close()
      //  movieDTO = readRawFromDB(str)
        return movieDTO

    }


    fun removeFavoriteMovie(docs: Docs) {

        var MovieDTOLike = readFromDB(MOVIEDTOFAVORITE_COL)

        MovieDTOLike?.let {
            MovieDTOLike

            for (i in 0 until it.docs.size) {
                if (it.docs[i].id == docs.id) {
                    it.docs.removeAt(i)
                    break
                }
            }



            updateFavoriteMovie(MovieDTOLike, MOVIEDTOFAVORITE_COL, MOVIEDTO_COl)

        }
    }


    fun addFavoriteMovie(docs: Docs) {

        var MovieDTOLike: MovieDTO? = readFromDB(MOVIEDTOFAVORITE_COL)

        var b = true
        if (MovieDTOLike != null) {
            for (docss in MovieDTOLike.docs) {
                if (docs.id == docss.id) {
                    b = false
                    break
                }
            }
        } else {
            MovieDTOLike = MovieDTO()
        }

        if (b) {
            MovieDTOLike.docs.add(docs)

            updateFavoriteMovie(MovieDTOLike, MOVIEDTOFAVORITE_COL, MOVIEDTO_COl)

        }
    }

    fun updateFavoriteMovie(movieDTO: MovieDTO?, changable: String, non_changeble: String) {

            var db = this.writableDatabase

            var cursor = db.query(
                TABLE_NAME, arrayOf(non_changeble),
                null, null, null, null, null
            )
            cursor!!.moveToFirst()
            val gson = Gson()
            val values = ContentValues()
            values.put(changable, gson.toJson(movieDTO))

            try {
                values.put(non_changeble, cursor.getString(0))
            } catch (e: CursorIndexOutOfBoundsException) {
                values.put(non_changeble, "")
            }


            // on below line we are calling a update method to update our database and passing our values.
            // and we are comparing it with name of our course which is stored in original name variable.

            if (cursor.count != 0) {
                db.update(TABLE_NAME, values, null, null)
            } else {
                db.insert(TABLE_NAME, null, values)
            }

            cursor.close()
            db.close()
      //  updateRawFavoriteMovie(movieDTO, changable)

    }

    // below method is to get
    // all data from our database

    fun getName(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)


    }

    fun openCreate(context: Context) {
        context.applicationContext.openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null)
    }

    fun like(docs: Docs): Boolean {

        var MovieDTOLike: MovieDTO? = readFavoriteMovieMovieDTO()

        var b = false
        if (MovieDTOLike != null) {
            for (docss in MovieDTOLike.docs) {
                if (docs.id == docss.id) {
                    b = true
                    break
                }
            }
        }

        return b
    }


    fun updateRawFavoriteMovie(movieDTO: MovieDTO?, changable: String) {
        var db = this.writableDatabase


        try {
            db.rawQuery(
                " UPDATE ${TABLE_NAME} SET ${changable} = '${Gson().toJson(movieDTO)}'",
                null
            )
        } catch (e: Exception) {
            db.rawQuery(
                " INSERT INTO ${TABLE_NAME} (${changable}) VALUES ('${Gson().toJson(movieDTO)}')",
                null
            )
        }
        db.close()
    }


    fun readRawFromDB(str: String): MovieDTO? {
        var db = this.readableDatabase

        var jsonString = db.rawQuery(" SELECT ${str} FROM ${TABLE_NAME} WHERE ${ID_COL} = 0", null)
            .getString(0)
        var movieDTO = Gson().fromJson(jsonString, MovieDTO::class.java)
        db.close()


        return movieDTO

    }


}