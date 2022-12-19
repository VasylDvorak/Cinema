package com.example.cinema.view.details

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.cinema.model.MovieDTO
import com.example.cinema.BuildConfig
import com.google.gson.Gson

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

@RequiresApi(Build.VERSION_CODES.N)
class MovieLoader(private val listener: MovieLoaderListener, private val movie_title: String) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovie() {
        try {
           // BuildConfig.KINOPOISK_API_KEY
/*
            val uri =
                URL("https://api.kinopoisk.dev/movie?field" +
                        "=name&search=${movie_title}&isStrict=false&" +
                        "token=${BuildConfig.KINOPOISK_API_KEY}")

            val uri =
                URL("https://api.kinopoisk.dev/movie?field" +
                        "=id&search=63398e2ec22d011bb5e3bd2c&" +
                        "token=${BuildConfig.KINOPOISK_API_KEY}")

   */



            val uri =
                URL("https://api.kinopoisk.dev/movie?field" +
                        "=name&search=...А человек играет на трубе&isStrict=false&" +
                        "token=${BuildConfig.KINOPOISK_API_KEY}")


            val handler = Handler()
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"

                        /*
                    urlConnection.addRequestProperty(
                        "X-Yandex-API-Key",
                        BuildConfig.KINOPOISK_API_KEY
                    )
*/


                    urlConnection.readTimeout = 10000

                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                   println(getLines(bufferedReader))
                    // преобразование ответа от сервера (JSON) в модель данных (MovieDTO)


                    val movieDTO: MovieDTO =
                        Gson().fromJson(getLines(bufferedReader), MovieDTO::class.java)

                    handler.post { listener.onLoaded(movieDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    interface MovieLoaderListener {
        fun onLoaded(movieDTO: MovieDTO)
        fun onFailed(throwable: Throwable)
    }
}
