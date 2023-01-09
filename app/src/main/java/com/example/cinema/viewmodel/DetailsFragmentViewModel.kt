package com.example.cinema.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.model.gson_kinopoisk_API.Docs

class DetailsFragmentViewModel : ViewModel() {
    private val selected : MutableLiveData<Docs> = MutableLiveData<Docs>();

    fun select(docs : Docs) {
        selected.setValue(docs)
    }

    fun  getSelected() : LiveData<Docs> {
        return selected;
    }
}