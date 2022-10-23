package by.belstu.narkevich.movies.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import by.belstu.narkevich.movies.models.Movie
import by.belstu.narkevich.movies.MainActivity

class IntentService {
    companion object Static {
        fun moveToMainActivity(context: Context) {
            val mainActivity= Intent(context, MainActivity::class.java)
            context.startActivity(mainActivity)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getMovieFromIntent(activity: Activity): Movie {
            val movieExtra = activity.intent.getSerializableExtra("Movie")

            return if (movieExtra == null) Movie() else movieExtra as Movie
        }

        fun <T> moveToActivityWithMovie( context: Context, movie: Movie, activityClass: Class<T>) {
            val intent = Intent(context, activityClass)
            intent.putExtra("Movie", movie)

            context.startActivity(intent)
        }
    }
}