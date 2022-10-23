package by.belstu.narkevich.movies.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class Movie : java.io.Serializable {
    var Id: UUID = UUID.randomUUID()
    var Name: String = ""
    var Country: String = ""
    var Genre: String = ""
    var Pegi18: Boolean = false
    var YoutubeTrailerCode: String = ""
    var Image: String = ""

    companion object Static {
        fun movieInfoToText(movie: Movie): String {
            val name = "Имя: " + movie.Name
            val country = "Страна: " + movie.Country
            val genre = "Жанр: " + movie.Genre
            val pegi18 = "Рейтинг 18+: " + if(movie.Pegi18) "Да" else "Нет"

            val info = name + "\n" +
                    country + "\n" +
                    genre + "\n" +
                    pegi18 + "\n"

            return info
        }
    }
}