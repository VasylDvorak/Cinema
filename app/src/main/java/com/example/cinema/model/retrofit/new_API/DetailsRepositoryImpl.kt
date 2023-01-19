package com.example.cinema.model.retrofit.new_API

import com.example.cinema.model.retrofit.new_API.new_model_movie_information.MovieInformation
import com.example.cinema.model.retrofit.new_API.new_model_the_best.TheBestMovie
import com.example.cinema.model.serch_name_movie_model.MovieDTO
import com.example.cinema.model.utils.convertBestToMovieDTO
import com.example.cinema.model.utils.converterMovieInformationToMovieDTO
import retrofit2.Callback


class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    DetailsRepository {
    override fun getMovieDetailsFromServer(request_movie: String?, callback: Callback<MovieInformation>) {
        remoteDataSource.getMovieDetails(request_movie, callback)
    }

    override fun getBestMovieDetailsFromServer(
        genres: String, yearTo : Int, callback: Callback<TheBestMovie>
    ) {
        remoteDataSource.getBestMovieDetails(genres.toInt(), yearTo, callback) }

    override fun getPlayMovieDetails(idd: String): String {
        return remoteDataSource.getPlayMovie(idd) }

    override fun fromMovieInformationToMovieDTO (input : MovieInformation) : MovieDTO {
        return converterMovieInformationToMovieDTO(input)
    }

    override fun fromTheBestMovieToMovieDTO (input : TheBestMovie) : MovieDTO {
        return convertBestToMovieDTO(input)
    }

}
