package com.example.cinema.model.repository


import com.example.cinema.model.model_stuio.MovieDTO

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    DetailsRepository {
    override fun getMovieDetailsFromServer(
        request_movie: String,
        callback: retrofit2.Callback<MovieDTO>
    ) {
        remoteDataSource.getMovieDetails(request_movie, callback)

    }
}
