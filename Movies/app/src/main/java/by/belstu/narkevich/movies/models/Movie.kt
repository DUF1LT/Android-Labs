package by.belstu.narkevich.movies.models

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
class Movie : java.io.Serializable {
    var Id: Long = -1
    var Name: String = ""
    var Country: String = ""
    var Genre: String = ""
    var Pegi18: Boolean = false
    var Image: String = ""
}