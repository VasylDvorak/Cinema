package com.example.cinema.model

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cinema.BuildConfig
import com.example.cinema.model.gson_decoder.Docs
import com.example.cinema.model.gson_decoder.MovieDTO
import com.example.cinema.model.gson_decoder.Poster
import com.example.cinema.model.gson_decoder.Rating
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONObject
import java.net.MalformedURLException
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
class MovieLoader(
    private val listener: MovieLoaderListener,
    private var find_request: String?,
    private val context: Context?
) {
    private var url_trailer = "https://www.youtube.com/embed/DlM2CWNTQ84"
    private val list_trailers = ArrayList<String>()
    private var is_like_list: ArrayList<Boolean> = ArrayList()
    private lateinit var item_finish: MovieDTO


    fun loadMovie() {
                val url = "https://api.kinopoisk.dev/movie?field" +
                "=name&search=${find_request}&isStrict=false&" +
                "token=${BuildConfig.KINOPOISK_API_KEY}"
        println(find_request)
        try {
            val handler = Handler()
            Thread(Runnable {
                try {
                    requestMovieData(url.toString())
                    handler.post {  }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }
    }


    interface MovieLoaderListener {
        fun onLoaded(movieDTO: MovieDTO)
        fun onFailed(throwable: Throwable)
    }


    private fun requestMovieData(url: String) {

        val queue = Volley.newRequestQueue(context)
          val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                run {

                    parseMovieData(result)
                }
            },

            { error ->
                run {

                }
            }

        )

        queue.add(request)

    }


    private fun parseMovieData(result: String): MovieDTO {
        val mainObject = JSONObject(result)
        val list = parseDocs(mainObject)

        var dto = parseMovieDataHead(mainObject, list)

        return dto

    }

    private fun parseDocs(mainObject: JSONObject): List<Docs> {

        val list = ArrayList<Docs>()
        val docsArray = mainObject.getJSONArray("docs")
        for (i in 0 until docsArray.length()) {
            try {

                val docs = docsArray[i] as JSONObject
                val posterObject = parsePoster(docs.getJSONObject("poster"))
                val ratingObject = ratingPoster(docs.getJSONObject("rating"))

                trailerMovie(docs.getInt("id"))

                val item_docs = Docs(
                    null, null,
                    posterObject, ratingObject,
                    null, null,
                    docs.getInt("id"), docs.getString("alternativeName"),
                    docs.getString("description"),
                    null, docs.getInt("movieLength"),
                    docs.getString("name"), null, null,
                    docs.getString("type"), docs.getInt("year"),
                    null, url_trailer = ""
                )

                list.add(item_docs)
            } catch (e: Exception) {
                Log.e("", "Fail URI", e)
                e.printStackTrace()
                listener.onFailed(e)
            }

        }
        return list
    }

    private fun ratingPoster(ratingObject: JSONObject): Rating {
        return Rating(
            ratingObject.getString("_id"),
            ratingObject.getInt("kp"),
            ratingObject.getInt("imdb"),
            ratingObject.getInt("filmCritics"),
            ratingObject.getInt("russianFilmCritics"),
            ratingObject.getInt("await")
        )

    }

    private fun parsePoster(posterObject: JSONObject): Poster {
        var poster = Poster(
            posterObject.getString("_id"),
            posterObject.getString("url"),
            posterObject.getString("previewUrl")
        )

        return poster
    }


    private fun parseMovieDataHead(
        mainObject: JSONObject,
        movieItem: List<Docs>
    ): MovieDTO {
        val item = MovieDTO(
            movieItem,
            mainObject.getInt("total"),
            mainObject.getInt("limit"),
            mainObject.getInt("page"),
            mainObject.getInt("pages")

        )
        item_finish = item
        return item
    }

    private fun trailerMovie(id: Int) {

        var idd = id.toString()
        var trailerUrl = "https://api.kinopoisk.dev/movie?field=id&search=${idd}&token" +
                "=${BuildConfig.KINOPOISK_API_KEY}"
        requestTrailerMovieData(trailerUrl)
    }

    private fun requestTrailerMovieData(trailerUrl: String) {

        val queue = Volley.newRequestQueue(context)
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
            var str = trailer0.getString("url")
            is_like_list.add(true)
            list_trailers.add(str)
        } catch (e: Exception) {
            list_trailers.add(url_trailer)
            is_like_list.add(false)
        }

        for (i in 0 until list_trailers.size) {
            try {
                item_finish.docs[i].isLike = is_like_list[i]
                item_finish.docs[i].url_trailer = list_trailers[i]
                println(item_finish.docs[i].url_trailer)
            } catch (e: IndexOutOfBoundsException) {
            }
            listener.onLoaded(item_finish)
        }

    }
}
