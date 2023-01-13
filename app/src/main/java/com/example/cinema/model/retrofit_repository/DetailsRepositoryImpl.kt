package com.example.cinema.model.retrofit_repository


import android.content.Context
import com.example.cinema.model.model_stuio.MovieDTO
import retrofit2.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    DetailsRepository {
    override fun getMovieDetailsFromServer(request_movie: String?, callback: Callback<MovieDTO>) {
        remoteDataSource.getMovieDetails(request_movie, callback)

    }

    override fun getBestMovieDetailsFromServer(
        find_type: String, callback: Callback<com.example.
        cinema.model.best_movie_model.MovieDTO>
    ) {
        remoteDataSource.getBestMovieDetails(find_type, callback)
    }

    override fun getPlayMovieDetails(idd: String, context: Context): String {
        return remoteDataSource.getPlayMovie(idd, context)
    }
}
