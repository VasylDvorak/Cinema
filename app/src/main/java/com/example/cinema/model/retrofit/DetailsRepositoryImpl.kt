package com.example.cinema.model.retrofit

import com.example.cinema.model.best_movie_model.MovieDTOBest
import com.example.cinema.model.serch_name_movie_model.MovieDTO
import com.example.cinema.model.utils.convertBestToMovieDTO
import retrofit2.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    DetailsRepository {
    override fun getMovieDetailsFromServer(request_movie: String?, callback: Callback<MovieDTO>) {
        remoteDataSource.getMovieDetails(request_movie, callback)
    }

    override fun getBestMovieDetailsFromServer(
        find_type: Int, callback: Callback<MovieDTOBest>
    ) {
        remoteDataSource.getBestMovieDetails(find_type, callback) }

    override fun getPlayMovieDetails(idd: Int): String {
        return remoteDataSource.getPlayMovie(idd.toString()) }

    override fun converter (input : MovieDTOBest) : MovieDTO{
    return convertBestToMovieDTO(input)
    }
}
