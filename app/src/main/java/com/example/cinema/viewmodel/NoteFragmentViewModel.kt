package com.example.cinema.viewmodel

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.app.App
import com.example.cinema.model.serch_name_movie_model.Docs
import com.example.cinema.model.room_data_base.LocalRepositoryImpl

class NoteFragmentViewModel(
    private val historyRepositoryImpl: LocalRepositoryImpl
    = LocalRepositoryImpl(App.getHistoryDao())
) : ViewModel() {
    private val selected: MutableLiveData<Docs> = MutableLiveData<Docs>()

    fun getDataNote(docs: Docs) {
        val handler = Handler()
    Thread {
   val selected_value = historyRepositoryImpl.getNote(docs)
    handler.post { selectedValue(selected_value)}
    }.start()
    }

    private fun selectedValue(selectedValue: Docs) {
        selected.value = selectedValue }

    fun getNote(): LiveData<Docs> {
        return selected
    }

    fun updateNote(docsData: Docs) {

        historyRepositoryImpl.updateNote(docsData)

    }
}