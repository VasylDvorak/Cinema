package com.example.cinema.model

import android.os.Parcelable
import com.example.cinema.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AboutMovie(
    val movie: Movie = getDefaultMovie(),
    val release_date: String = "27.11.2018",
    val rating: String = "5.5",
    val genre: String = "action",
    val duration: String = "140 min",
    val budget: String = "139 000 000 $",
    val revenue: String = "403 706 375 $",
    val description: String = "Питер Паркер – обыкновенный школьник. " +
            "Однажды он отправился с классом на экскурсию, где его кусает странный паук-мутант. " +
            "Через время парень почувствовал в себе нечеловеческую силу и ловкость в движении, " +
            "а главное – умение лазать по стенам и метать стальную паутину. Свои способности " +
            "он направляет на защиту слабых. Так Питер становится настоящим супергероем по " +
            "имени Человек-паук, который помогает людям и борется с преступностью. Но там, " +
            "где есть супергерой, рано или поздно всегда объявляется и суперзлодей...",
    var isLike: Boolean = false


) : Parcelable

fun getDefaultMovie() = Movie("Человек паук", "Spider-Man", R.drawable.spiderman)

fun nowPlaying(): List<AboutMovie> {
    val description: String = "Питер Паркер – обыкновенный школьник. " +
            "Однажды он отправился с классом на экскурсию, где его кусает странный паук-мутант. " +
            "Через время парень почувствовал в себе нечеловеческую силу и ловкость в движении, " +
            "а главное – умение лазать по стенам и метать стальную паутину. Свои способности " +
            "он направляет на защиту слабых. Так Питер становится настоящим супергероем по " +
            "имени Человек-паук, который помогает людям и борется с преступностью. Но там, " +
            "где есть супергерой, рано или поздно всегда объявляется и суперзлодей..."
    return listOf(
        AboutMovie(
            Movie("Человек паук 2", "Spider-Man 2", R.drawable.spiderman),
            "27.11.2005",
            "4.1",
            " romantic comedy film, musical",
            "200 min",
            "22222222 $",
            "444444444 $",
            isLike = false,
            description = description
        ),
        AboutMovie(
            Movie("Человек паук 3", "Spider-Man 3", R.drawable.spiderman),
            "27.11.2005",
            "4.1",
            " romantic comedy film, musical",
            "200 min",
            "22222222 $",
            "444444444 $",
            isLike = false,
            description = description
        ),
        AboutMovie(
            Movie("Человек паук 4", "Spider-Man 4", R.drawable.spiderman),
            "27.11.2005",
            "4.1",
            " romantic comedy film, musical",
            "200 min",
            "22222222 $",
            "444444444 $",
            isLike = false,
            description = description
        ),
        AboutMovie(
            Movie("Человек паук 5", "Spider-Man 5", R.drawable.spiderman),
            "27.11.2005",
            "4.1",
            " romantic comedy film, musical",
            "200 min",
            "22222222 $",
            "444444444 $",
            isLike = false,
            description = description
        )
    )
}

fun upcoming(): List<AboutMovie> {
    var description: String = "Питер Паркер – обыкновенный школьник. " +
            "Однажды он отправился с классом на экскурсию, где его кусает странный паук-мутант. " +
            "Через время парень почувствовал в себе нечеловеческую силу и ловкость в движении, " +
            "а главное – умение лазать по стенам и метать стальную паутину. Свои способности " +
            "он направляет на защиту слабых. Так Питер становится настоящим супергероем по " +
            "имени Человек-паук, который помогает людям и борется с преступностью. Но там, " +
            "где есть супергерой, рано или поздно всегда объявляется и суперзлодей..."
    return listOf(
        AboutMovie(
            Movie("Человек паук 6", "Spider-Man 6", R.drawable.spiderman),
            "27.11.2005",
            "4.1",
            " romantic comedy film, musical",
            "200 min",
            "22222222 $",
            "444444444 $",
            isLike = false,
            description = description
        ),
        AboutMovie(
            Movie("Человек паук 7", "Spider-Man 7", R.drawable.spiderman),
            "27.11.2005",
            "4.1",
            " romantic comedy film, musical",
            "200 min",
            "22222222 $",
            "444444444 $",
            isLike = false,
            description = description
        ),
        AboutMovie(
            Movie("Человек паук 8", "Spider-Man 8", R.drawable.spiderman),
            "27.11.2005",
            "4.1",
            " romantic comedy film, musical",
            "200 min",
            "22222222 $",
            "444444444 $",
            isLike = false,
            description = description
        ),
        AboutMovie(
            Movie("Человек паук 9", "Spider-Man 9", R.drawable.spiderman),
            "27.11.2005",
            "4.1",
            " romantic comedy film, musical",
            "200 min",
            "22222222 $",
            "444444444 $",
            isLike = false,
            description = description
        ),
    )
}