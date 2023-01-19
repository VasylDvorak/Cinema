package com.example.cinema.model.retrofit

import com.example.cinema.model.serch_name_movie_model.MovieDTO
import retrofit2.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    DetailsRepository {
    override fun getMovieDetailsFromServer(request_movie: String?, callback: Callback<MovieDTO>) {
        remoteDataSource.getMovieDetails(request_movie, callback)
    }

    override fun getBestMovieDetailsFromServer(
        find_type: String, callback: Callback<com.example.cinema.model.best_movie_model.MovieDTOBest>
    ) {
        remoteDataSource.getBestMovieDetails(find_type, callback) }

    override fun getPlayMovieDetails(idd: String): String {
        return remoteDataSource.getPlayMovie(idd) }
}
