package com.example.cinema.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.model.data_base.DataBase
import com.example.cinema.model.model_stuio.Docs
import com.example.cinema.model.retrofit_repository.DetailsRepository
import com.example.cinema.model.retrofit_repository.DetailsRepositoryImpl
import com.example.cinema.model.retrofit_repository.RemoteDataSource

class DetailsFragmentViewModel(    private val detailsRepositoryImpl: DetailsRepository =
                                       DetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {
    private val selected : MutableLiveData<Docs> = MutableLiveData<Docs>();

    fun select(docs: Docs, context: Context) {
        docs.url_trailer = detailsRepositoryImpl.getPlayMovieDetails(docs.id.toString(), context)
        selected.setValue(docs)
    }

    fun  getSelected() : LiveData<Docs> {
        return selected;
    }

    fun addNowPlaying(docsData: Docs, context: Context?) {
        var dbHelper = DataBase(context!!, null)
        dbHelper.addNowPlayingMovieDTO(docsData)
        dbHelper.close()

    }
}