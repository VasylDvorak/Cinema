package com.example.cinema.model


import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.database.CursorIndexOutOfBoundsException
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cinema.BuildConfig
import com.example.cinema.model.data_base.DBHelper
import com.example.cinema.model.gson_kinopoisk_API.Docs
import com.example.cinema.model.gson_kinopoisk_API.MovieDTO
import com.example.cinema.model.gson_kinopoisk_API.Poster
import com.example.cinema.model.gson_kinopoisk_API.Rating
import com.example.cinema.view.MainActivity
import com.example.cinema.view.details.*
import com.example.cinema.viewmodel.*
import org.json.JSONException
import org.json.JSONObject
import java.net.MalformedURLException

const val REQUEST_MOVIE = "REQUEST MOVIE"
const val url_trailer = "https://www.youtube.com/embed/DlM2CWNTQ84"

class DetailsService(name: String = "DetailService") : IntentService(name) {

    private lateinit var item_finish: MovieDTO
private var strr = ""
    private var list_trailer: MutableList<String> = mutableListOf()


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

        fact?.let {
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
        val url = find_request

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

        item_finish=dto
        return dto
    }

    private fun parseDocs(mainObject: JSONObject): MutableList<Docs> {

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
                strr=""
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
                        getString("name") ?: let { " " }, null,
                        getString("shortDescription"),
                        getString("type") ?: let { " " }, getInt("year"),
                        null, url_trailer = strr
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
        movieItem: MutableList<Docs>
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
       var str=url_trailer
            try {
                var videos = JSONObject(result).getJSONObject("videos")
                var trailer = videos.getJSONArray("trailers")
                var trailer0 = trailer[0] as JSONObject
                str = trailer0.getString("url")

            } catch (e: Exception) { }
        finally {
            list_trailer.add(str)
            for(i in 0 until list_trailer.size){
                try{
                    item_finish.docs[i].url_trailer=list_trailer[i]
                }
                catch(e: IndexOutOfBoundsException){
                    item_finish.docs[0].url_trailer=list_trailer[0]
                }

            }
if(item_finish.docs.size == list_trailer.size){
            onResponse(item_finish)}
        }

}}



