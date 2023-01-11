package com.example.cinema.model.data_base

import android.content.Context
import com.example.cinema.model.data_base.data_base_classes.MovieDTO_db
import com.example.cinema.model.gson_kinopoisk_API.Docs
import com.example.cinema.model.gson_kinopoisk_API.MovieDTO
import com.google.gson.Gson

class DataBaseRoom(context: Context) {
    private val repo: MovieDTO_repository by lazy { MovieDTO_repository(context) }


    fun renewCurrentMovieDTO(movieDTO: MovieDTO) {
        val gson = Gson()
        val out_from_Db = gson.toJson(movieDTO)
        repo.updateMovieDTO_db(gson.fromJson(out_from_Db, MovieDTO_db::class.java))
    }


    fun readCurrentMovieDTO(): MovieDTO? {
        val gson = Gson()
        val out_from_Db = gson.toJson(repo.getMovieDTO_db_list()[0])
        return gson.fromJson(out_from_Db, MovieDTO::class.java)
    }

    fun readFavoriteMovieMovieDTO(): MovieDTO? {
        val gson = Gson()
        val out_from_Db = gson.toJson(repo.getMovieDTO_db_list()[1])
        return gson.fromJson(out_from_Db, MovieDTO::class.java)
    }


    fun removeFavoriteMovie(docs: Docs) {

        var MovieDTOLike = readFavoriteMovieMovieDTO()

        MovieDTOLike?.let {
            MovieDTOLike

            for (i in 0 until it.docs.size) {
                if (it.docs[i].id == docs.id) {
                    it.docs.removeAt(i)
                    break
                }
            }


            val gson = Gson()
            val out_from_Db = gson.toJson(MovieDTOLike)
            repo.updateMovieDTO_db(gson.fromJson(out_from_Db, MovieDTO_db::class.java))

        }
    }


    fun addFavoriteMovie(docs: Docs) {

        var MovieDTOLike: MovieDTO? = readFavoriteMovieMovieDTO()

        var b = true
        if (MovieDTOLike != null) {
            for (docss in MovieDTOLike.docs) {
                if (docs.id == docss.id) {
                    b = false
                    break
                }
            }
        } else {
            MovieDTOLike = MovieDTO(docs = mutableListOf(docs))
        }

        if (b) {
            MovieDTOLike.docs.add(docs)
            val gson = Gson()
            val out_from_Db = gson.toJson(MovieDTOLike)


            repo.updateMovieDTO_db(gson.fromJson(out_from_Db, MovieDTO_db::class.java))

        }
    }


    fun like(docs: Docs): Boolean {
        var b = false
        try {


            readFavoriteMovieMovieDTO()

            var MovieDTOLike: MovieDTO? = readFavoriteMovieMovieDTO()


            if (MovieDTOLike != null) {
                for (docss in MovieDTOLike.docs) {
                    if (docs.id == docss.id) {
                        b = true
                        break
                    }
                }
            }
        } catch (e: IndexOutOfBoundsException) {
        }
        return b
    }


}