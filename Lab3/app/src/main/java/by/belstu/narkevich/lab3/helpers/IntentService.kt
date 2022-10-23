package by.belstu.narkevich.lab3.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import by.belstu.narkevich.lab3.ListActivity
import by.belstu.narkevich.lab3.MainActivity
import by.belstu.narkevich.lab3.models.Movie

class IntentService {
    companion object Static {
        fun moveToCreateActivity(context: Context) {
            val createActivity= Intent(context, MainActivity::class.java)
            context.startActivity(createActivity)
        }

        fun moveToListActivity(context: Context) {
            val listActivity= Intent(context, ListActivity::class.java)
            context.startActivity(listActivity)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getMovieFromIntent(activity: Activity): Movie {
            val movieExtra = activity.intent.getSerializableExtra("Movie")

            return if (movieExtra == null) Movie() else movieExtra as Movie
        }

        fun <T> moveToActivityWithMovie(movie: Movie, context: Context, activityClass: Class<T>) {
            val intent = Intent(context, activityClass)
            intent.putExtra("Movie", movie)

            context.startActivity(intent)
        }
    }
}