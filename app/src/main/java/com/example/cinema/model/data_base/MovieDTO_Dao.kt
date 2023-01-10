package com.example.cinema.model.data_base

import androidx.room.*
import com.example.cinema.model.data_base.data_base_classes.Docs_db
import com.example.cinema.model.data_base.data_base_classes.MovieDTO_db
import com.example.cinema.model.data_base.data_base_classes.Names_db
import java.util.*

@Dao
interface MovieDTO_Dao {

    @Query("SELECT * FROM MovieDTO_db")
    fun getMovieDTO_db_list(): MutableList<MovieDTO_db>

    @Query("SELECT * FROM Docs_db")
    fun getDocs_db_list(): MutableList<Docs_db>

    @Query("SELECT * FROM Names_db")
    fun getNames_db_list(): MutableList<Names_db>


    @Insert
    fun insertMovieDTO_db(movieDTO_db: MovieDTO_db)

    @Insert
    fun insertDocs_db(docs_db: Docs_db)

    @Insert
    fun insertNames_db(names_db: Names_db)


    @Update
    fun updateMovieDTO_db(movieDTO_db: MovieDTO_db)

    @Update
    fun updateDocs_db(docs_db: Docs_db)

    @Update
    fun updateNames_db(names_db: Names_db)


    @Delete
    fun deleteMovieDTO_db(movieDTO_db: MovieDTO_db)

    @Delete
    fun deleteDocs_db(docs_db: Docs_db)

    @Delete
    fun deleteNames_db(names_db: Names_db)


}