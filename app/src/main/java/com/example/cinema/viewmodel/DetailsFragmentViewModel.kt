package com.example.cinema.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.model.data_base.DataBase
import com.example.cinema.model.gson_kinopoisk_API.Docs

class DetailsFragmentViewModel : ViewModel() {
    private val selected : MutableLiveData<Docs> = MutableLiveData<Docs>();

    fun select(docs : Docs) {
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