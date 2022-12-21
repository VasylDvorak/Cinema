package com.example.cinema.view.details

import android.content.Context
import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cinema.R
import com.example.cinema.model.gson_decoder.Docs
import com.example.cinema.model.gson_decoder.MovieDTO
import com.example.cinema.model.gson_decoder.Poster
import com.example.cinema.model.gson_decoder.Rating
import com.example.cinema.view.Extensions
import com.example.cinema.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONObject
import java.net.MalformedURLException
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
class MovieLoader(
    private val listener: MovieLoaderListener,
    private val url: String,
    private val context: Context?,
    private val vms: ViewModelStoreOwner,
    private val mainView: ConstraintLayout
) {

    private val model: MainViewModel by lazy {
        ViewModelProvider(vms).get(MainViewModel::class.java)

    }

    fun loadMovie() {
        try {
            val handler = Handler()
            Thread(Runnable {
                try {
                    requestMovieData(url)
                    handler.post { listener.onLoaded() }
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
        fun onLoaded()
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
                    Extensions.showSnackbar(
                        mainView,
                        context!!.resources.getString(R.string.error),
                        context.resources.getString(R.string.OK),
                        { context.resources.getString(R.string.OK) }
                    )
                }
            }

        )
        queue.add(request)

    }

    private fun parseMovieData(result: String): MovieDTO {

        val mainObject = JSONObject(result)
        val list = parseDocs(mainObject)
        return parseMovieData(mainObject, list)

    }

    private fun parseDocs(mainObject: JSONObject): List<Docs> {

        val list = ArrayList<Docs>()
        val docsArray = mainObject.getJSONArray("docs")
        for (i in 0 until docsArray.length()) {
            try {

                val docs = docsArray[i] as JSONObject
                val posterObject = parsePoster(docs.getJSONObject("poster"))
                val ratingObject = ratingPoster(docs.getJSONObject("rating"))
                val item_docs = Docs(
                    null, null,
                    posterObject, ratingObject,
                    null, null,
                    docs.getInt("id"), docs.getString("alternativeName"),
                    docs.getString("description"),
                    null, docs.getInt("movieLength"),
                    docs.getString("name"), null, null,
                    docs.getString("type"), docs.getInt("year"),
                    null
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
        Log.d("MyLog_poster", "_id: ${poster._id}")
        Log.d("MyLog_poster", "url: ${poster.url}")
        Log.d("MyLog_poster", "previewUrl: ${poster.previewUrl}")
        return poster
    }


    private fun parseMovieData(
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
        model.liveDataCurrent.value = item
        listener.onLoaded()
        Log.d("item", "item: ${item}")
        Log.d("MyLog", "docs: ${item.docs}")
        Log.d("MyLog", "total: ${item.total}")
        Log.d("MyLog", "limit: ${item.limit}")
        Log.d("MyLog", "page: ${item.page}")
        Log.d("MyLog", "pages: ${item.pages}")

        return item
    }

}
