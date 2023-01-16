package com.example.cinema.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cinema.R
import com.example.cinema.databinding.ActivityMainBinding
import com.example.cinema.view.favorite_movie.FavoriteMovieFragment
import com.example.cinema.view.main_movie.MainFragment
import com.example.cinema.view.ratings_movie.RatingsFragment


const val NAME_MSG: String = "MSG"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    companion object {
        var start_cinema = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNotificationChannel()


        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mainFragment = MainFragment()
        val favoriteMovieFragment = FavoriteMovieFragment()
        val ratingsFragment = RatingsFragment()
        setCurrentFragment(mainFragment)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(mainFragment)
                R.id.favorites -> setCurrentFragment(favoriteMovieFragment)
                R.id.ratings -> setCurrentFragment(ratingsFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            addToBackStack("")
            commit()
        }
    // инициализация канала нотификаций
    private fun initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                "2", resources.getString(R.string.name),
                importance
            )
            notificationManager.createNotificationChannel(channel)
        }
        if (intent.hasExtra(NAME_MSG)) {
            start_cinema = intent.getStringExtra(NAME_MSG)!!
        }
    }



}