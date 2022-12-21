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
    var isLike: Boolean = false,
    var description: String = "Питер Паркер – обыкновенный школьник. " +
            "Однажды он отправился с классом на экскурсию, где его кусает странный паук-мутант. " +
            "Через время парень почувствовал в себе нечеловеческую силу и ловкость в движении, " +
            "а главное – умение лазать по стенам и метать стальную паутину. Свои способности " +
            "он направляет на защиту слабых. Так Питер становится настоящим супергероем по " +
            "имени Человек-паук, который помогает людям и борется с преступностью. Но там, " +
            "где есть супергерой, рано или поздно всегда объявляется и суперзлодей..."+
            "Питер Паркер – обыкновенный школьник. " +
            "Однажды он отправился с классом на экскурсию, где его кусает странный паук-мутант. " +
            "Через время парень почувствовал в себе нечеловеческую силу и ловкость в движении, " +
            "а главное – умение лазать по стенам и метать стальную паутину. Свои способности " +
            "он направляет на защиту слабых. Так Питер становится настоящим супергероем по " +
            "имени Человек-паук, который помогает людям и борется с преступностью. Но там, " +
            "где есть супергерой, рано или поздно всегда объявляется и суперзлодей..."

) : Parcelable
// "Новый Человек-паук: Высокое напряжение"
// "Человек-паук"

fun getDefaultMovie() = Movie("Новый Человек-паук: Высокое напряжение", "Spider-Man",
                                                        R.drawable.spiderman)

fun nowPlaying(): List<AboutMovie> = listOf(
    AboutMovie(
        getDefaultMovie(),
        "27.11.2005",
        "4.1",
        " romantic comedy film, musical",
        "200 min",
        "22222222 $",
        "444444444 $",
        false,
    ),

    AboutMovie(
        getDefaultMovie(),
        "27.11.2005",
        "4.1",
        " romantic comedy film, musical",
        "200 min",
        "22222222 $",
        "444444444 $",
        false,
    ),

    AboutMovie(
        getDefaultMovie(),
        "27.11.2005",
        "4.1",
        " romantic comedy film, musical",
        "200 min",
        "22222222 $",
        "444444444 $",
        false,
    ),

    AboutMovie(
        getDefaultMovie(),
        "27.11.2005",
        "4.1",
        " romantic comedy film, musical",
        "200 min",
        "22222222 $",
        "444444444 $",
        false,
    )
)


fun upcoming(): List<AboutMovie> = listOf(
    AboutMovie(
        getDefaultMovie(),
        "27.11.2005",
        "4.1",
        " romantic comedy film, musical",
        "200 min",
        "22222222 $",
        "444444444 $",
        false,
    ),

    AboutMovie(
        getDefaultMovie(),
        "27.11.2005",
        "4.1",
        " romantic comedy film, musical",
        "200 min",
        "22222222 $",
        "444444444 $",
        false,
    ),

    AboutMovie(
        getDefaultMovie(),
        "27.11.2005",
        "4.1",
        " romantic comedy film, musical",
        "200 min",
        "22222222 $",
        "444444444 $",
        false,
    ),

    AboutMovie(
        getDefaultMovie(),
        "27.11.2005",
        "4.1",
        " romantic comedy film, musical",
        "200 min",
        "22222222 $",
        "444444444 $",
        false,
    ),

)
