package com.example.cinema.viewmodel

import android.os.Build
import android.os.Handler
import android.support.annotation.RequiresApi
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema.model.gson_kinopoisk_API.Docs
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class PlayViewModel(
    private val liveDataToObserve: MutableLiveData<String> = MutableLiveData(),
) : ViewModel() {

    fun getPlayData(): MutableLiveData<String> {
        return liveDataToObserve
    }

    fun fromDetailFragment(docs: Docs) {

        try {

            val uri = URL(docs.url_trailer)
            val handler = Handler() //Запоминаем основной поток
            Thread {

                var urlConnection: HttpsURLConnection? = null
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.apply{
                        requestMethod = "GET" //установка метода получения данных — GET
                        readTimeout = 10000 //установка таймаута — 10 000 миллисекунд
                    }
                    val reader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream)) //читаем данные в поток
                    val result = getLines(reader) // Возвращаемся к основному потоку

                    handler.post {
                        result_for_web_view(result)
                    }
                } catch (e: Exception) {

                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    urlConnection?.disconnect()

                }
            }.start()


        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
        }

    }

    fun result_for_web_view(result: String) {
        liveDataToObserve.postValue(result)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

}