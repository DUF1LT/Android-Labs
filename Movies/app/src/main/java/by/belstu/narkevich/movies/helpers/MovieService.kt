package by.belstu.narkevich.movies.helpers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import by.belstu.narkevich.movies.models.Movie
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class MovieService {
    companion object Static {
        var fileName = "movieList.json"

        fun loadMoviesFromFile(context: Context): ArrayList<Movie> {
            return FileService.readFromFile(context, fileName)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun removeMovie(context: Context, id: UUID) {
            val movies = FileService.readFromFile(context, fileName)
            val movie = movies.find { m ->  m?.Id == id }
            movies.remove(movie)

            val moviesJSON = Gson().toJson(movies)

            FileService.writeToFile(context, moviesJSON, fileName)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun addMovie(context: Context, movie: Movie) {
            val movies = FileService.readFromFile(context, fileName)
            movies.add(movie)

            val moviesJSON = Gson().toJson(movies)
            FileService.writeToFile(context, moviesJSON, fileName)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun editMovie(context: Context, movie: Movie) {
            val movies = FileService.readFromFile(context, fileName)
            val movieToReplace = movies.find { m -> m.Id == movie.Id}
            val movieIndex = movies.indexOf(movieToReplace)
            movies[movieIndex] = movie

            val moviesJSON = Gson().toJson(movies)
            FileService.writeToFile(context, moviesJSON, fileName)
        }
    }
}