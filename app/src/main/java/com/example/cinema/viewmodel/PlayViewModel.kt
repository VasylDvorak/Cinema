package com.example.cinema.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.support.annotation.RequiresApi
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cinema.BuildConfig
import com.example.cinema.model.url_trailer
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val url_trailer = "https://www.youtube.com/embed/DlM2CWNTQ84"

class PlayViewModel(
    private val liveDataToObserve: MutableLiveData<String> = MutableLiveData()
) : ViewModel() {
    private lateinit var context_VM: Context
    private lateinit var str: String
    fun getPlayData(): MutableLiveData<String> {
        return liveDataToObserve
    }

    fun fromDetailFragment(urlStr: String) {

        try {

            val uri = URL(urlStr)
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






     fun trailerMovie(id: Int, context: Context) {
        context_VM = context
        var idd = id.toString()
        var trailerUrl = "https://api.kinopoisk.dev/movie?field=id&search=${idd}&token" +
                "=${BuildConfig.KINOPOISK_API_KEY}"
        requestTrailerMovieData(trailerUrl)
    }

    private fun requestTrailerMovieData(trailerUrl: String) {
        val queue = Volley.newRequestQueue(context_VM)
        val request = StringRequest(
            Request.Method.GET,
            trailerUrl,
            { result ->
                run {
                    parseTrailerMovieData(result)
                }
            },
            { error ->
                run {

                }
            }

        )
        queue.add(request)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun parseTrailerMovieData(result: String?) {

        try {
            var videos = JSONObject(result).getJSONObject("videos")
            var trailer = videos.getJSONArray("trailers")
            var trailer0 = trailer[0] as JSONObject
            str = trailer0.getString("url")
        } catch (e: Exception) {
            str =url_trailer
        }
        result_for_web_view(str)
    }

}