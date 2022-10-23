package by.belstu.narkevich.lab3.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class Movie : java.io.Serializable {
    var Name: String = ""
    var Year: Int = 0
    var Country: String = ""
    var Genre: String = ""
    var Pegi18: Boolean = false
    var Duration: Int = 0
    var YoutubeTrailerCode: String = ""
    var Image: String = ""

    companion object Static {
        fun movieInfoToText(movie: Movie): String {
            val name = "Имя: " + movie.Name
            val year = "Год: " + movie.Year
            val country = "Страна: " + movie.Country
            val genre = "Жанр: " + movie.Genre
            val pegi18 = "Рейтинг 18+: " + if(movie.Pegi18) "Да" else "Нет"
            val duration = "Продолжительность: " + movie.Duration

            val info = name + "\n" +
                    year + "\n" +
                    country + "\n" +
                    genre + "\n" +
                    pegi18 + "\n" +
                    duration + "\n"

            return info
        }
    }
}