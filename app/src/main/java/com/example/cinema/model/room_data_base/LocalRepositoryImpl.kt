package com.example.cinema.model.room_data_base

import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.model.serch_name_movie_model.MovieDTO
import com.example.cinema.model.utils.convertHistoryEntityToMovie
import com.example.cinema.model.utils.convertMovieToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) :
    LocalRepository {

    override fun saveEntity(docs: Docs, current: Boolean, watched: Boolean, isLike: Boolean) {
        localDataSource.insert(convertMovieToEntity(docs, current, watched, isLike))
    }

    override fun readFavoriteMovieMovieDTO(): MovieDTO {
        return MovieDTO(
            convertHistoryEntityToMovie(localDataSource.getDataIsLiked()),
            0, 0, 0, 0
        )
    }

    override fun addFavoriteMovie(docs: Docs) {
        Thread {
            var favoriteDocs = localDataSource.getDataIsLiked()
            var include = false
            if ((favoriteDocs.size != 0) && (favoriteDocs.size != null)) {
                favoriteDocs.map {
                    if (it.id_server == docs.id) {
                        include = true
                    }
                }
            }

            if (!include) {
                saveEntity(docs, false, false, true)
            }
        }.start()
    }

    override fun removeFavoriteMovie(docs: Docs) {
        Thread {
            var favoriteDocs = localDataSource.getDataIsLiked()
            favoriteDocs.map {
                if (it.id_server == docs.id) {
                    localDataSource.delete(it)
                }
            }
        }.start()
    }

    override fun like(docs: Docs): Boolean {
        var favoriteDocs = localDataSource.getDataIsLiked()
        var b = false
        favoriteDocs.map {
            if (docs.id == it.id_server) {
                b = true
            }
        }
        return b
    }

    override fun readNowPlayingMovieMovieDTO(): MovieDTO {
        return MovieDTO(
            convertHistoryEntityToMovie(localDataSource.getDataWatched()),
            0, 0, 0, 0
        )
    }

    fun addNowPlayingMovieDTO(docs: Docs) {

        Thread {
            var nowPlayngDocsOld = localDataSource.getDataWatched()
            var include = false
            if ((nowPlayngDocsOld.size != 0) && (nowPlayngDocsOld.size != null)) {
                nowPlayngDocsOld.map {
                    if (it.id_server == docs.id) {
                        include = true
                    }
                }
            }

            if (!include) {
                nowPlayngDocsOld.map { localDataSource.delete(it) }
                if (nowPlayngDocsOld.size > 9) {
                    nowPlayngDocsOld.removeFirst()
                }
                var new_watching_historyEntity = mutableListOf(
                    convertMovieToEntity(
                        docs, false, true, false
                    )
                ).plus(nowPlayngDocsOld).toMutableList()

                new_watching_historyEntity.map { localDataSource.insert(it) }
            }
        }.start()
    }


    override fun watched(docs: Docs): Boolean {
        var watchedDocs = localDataSource.getDataWatched()
        var b = false
        watchedDocs.map {
            if (docs.id == it.id_server) {
                b = true
            }
        }
        return b
    }


    override fun readCurrentMovieDTO(): MovieDTO {
        return MovieDTO(
            convertHistoryEntityToMovie(localDataSource.getDataCurrentRequest()),
            0, 0, 0, 0
        )
    }

    override fun renewCurrentMovieDTO(movieDTO: MovieDTO) {
        Thread {
            if ((movieDTO.docs.size != 0) && (movieDTO.docs.size != null)) {
                var oldHistoryEntity = localDataSource.getDataCurrentRequest()
                if ((oldHistoryEntity.size != 0) && (oldHistoryEntity.size != null)) {
                    oldHistoryEntity.map { localDataSource.delete(it) }
                }
                for (new in movieDTO.docs) {
                    saveEntity(new, true, false, false)
                }
            }
        }.start()
    }


    override fun updateNote(docs: Docs) {
        Thread {
            var updatedHistoryEntity = localDataSource.getHistoryEntity(docs.id)
            updatedHistoryEntity.note = docs.note
            localDataSource.update(updatedHistoryEntity)
        }.start()
    }

    override fun getNote(docs: Docs): Docs {
        var getHistoryEntity = localDataSource.getHistoryEntity(docs.id)
        return convertHistoryEntityToMovie(mutableListOf(getHistoryEntity))[0]
    }
}
