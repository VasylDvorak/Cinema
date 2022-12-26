package com.example.cinema.model


import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cinema.BuildConfig
import com.example.cinema.model.gson_kinopoisk_API.Docs
import com.example.cinema.model.gson_kinopoisk_API.MovieDTO
import com.example.cinema.model.gson_kinopoisk_API.Poster
import com.example.cinema.model.gson_kinopoisk_API.Rating
import com.example.cinema.view.details.*
import com.example.cinema.viewmodel.*
import org.json.JSONException
import org.json.JSONObject
import java.net.MalformedURLException

const val REQUEST_MOVIE = "REQUEST MOVIE"
const val url_trailer = "https://www.youtube.com/embed/DlM2CWNTQ84"

class DetailsService(name: String = "DetailService") : IntentService(name) {

    private val list_trailers = ArrayList<String>()
    private var is_like_list: ArrayList<Boolean> = ArrayList()
    private lateinit var item_finish: MovieDTO


    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)
    private var context_from_VM: Context? = this


    override fun onHandleIntent(intent: Intent?) {

        Log.d("TAG", "onHandleIntent")
        intent?.let {

            val find_request = intent.getStringExtra(REQUEST_MOVIE)

            if (find_request == null) {
                onEmptyData()
            } else {
                loadMovie(find_request.toString())
            }
        } ?: run {
            onEmptyIntent()
        }
    }


    private fun onResponse(movieDTO: MovieDTO) {
        val fact = movieDTO

        fact.let {
            onSuccessfulResponse(movieDTO)
        }
    }

    private fun onSuccessfulResponse(movieDTO: MovieDTO) {

        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, DETAILS_RESPONSE_SUCCESS_EXTRA)

        broadcastIntent.apply {

            putExtra(DETAILS_CONDITION_EXTRA, movieDTO)

        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }

    fun loadMovie(find_request: String?) {
        val url = "https://api.kinopoisk.dev/movie?field" +
                "=name&search=${find_request}&isStrict=false&" +
                "token=${BuildConfig.KINOPOISK_API_KEY}"
        try {
            try {
                requestMovieData(url.toString())

            } catch (e: Exception) {
                Log.e("", "Fail connection", e)
                e.printStackTrace()

            }

        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            onMalformedURL()
        }


    }


    private fun requestMovieData(url: String) {
        val context = context_from_VM
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


            val docs = docsArray[i] as JSONObject


            var posterObject: Poster?
            try {
                posterObject = parsePoster(docs.getJSONObject("poster"))
            } catch (e: JSONException) {
                posterObject = null
            }


            var movieLength = 0
            try {
                movieLength = docs.getInt("movieLength")
            } catch (e: JSONException) {
            }


            try {
                val ratingObject = ratingPoster(docs.getJSONObject("rating"))

                trailerMovie(docs.getInt("id"))

                val item_docs = with(docs) {
                    Docs(
                        null, null,
                        posterObject, ratingObject,
                        null, null,
                        getInt("id"),
                        getString("alternativeName") ?: let { " " },
                        getString("description") ?: let { " " },
                        null, movieLength,
                        getString("name") ?: let { " " }, null, null,
                        getString("type") ?: let { " " }, getInt("year"),
                        null, url_trailer = ""
                    )
                }

                list.add(item_docs)
            } catch (e: Exception) {
                Log.e("", "Fail URI", e)
                e.printStackTrace()
                // onErrorRequest(e.message ?: "Empty error")
            }

        }
        return list
    }

    private fun ratingPoster(ratingObject: JSONObject): Rating {
        return with(ratingObject) {
            Rating(
                getString("_id") ?: let { " " },
                getInt("kp"),
                getInt("imdb"),
                getInt("filmCritics"),
                getInt("russianFilmCritics"),
                getInt("await")
            )
        }

    }

    private fun parsePoster(posterObject: JSONObject): Poster {
        var poster = with(posterObject) {
            Poster(
                getString("_id") ?: let { " " },
                getString("url") ?: let { " " },
                getString("previewUrl") ?: let { " " }
            )
        }
        return poster
    }


    private fun parseMovieDataHead(
        mainObject: JSONObject,
        movieItem: List<Docs>
    ): MovieDTO {
        val item = with(mainObject) {
            MovieDTO(
                movieItem,
                getInt("total"),
                getInt("limit"),
                getInt("page"),
                getInt("pages")

            )
        }
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
        val context = context_from_VM
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

            } catch (e: IndexOutOfBoundsException) {
            }

            onResponse(item_finish)

        }

    }
}



