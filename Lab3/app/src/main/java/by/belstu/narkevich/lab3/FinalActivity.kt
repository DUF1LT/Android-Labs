package by.belstu.narkevich.lab3

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import by.belstu.narkevich.lab3.databinding.ActivityFinalBinding
import by.belstu.narkevich.lab3.helpers.FileService
import by.belstu.narkevich.lab3.helpers.IntentService
import by.belstu.narkevich.lab3.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type

class FinalActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityFinalBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFinalBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val movie = IntentService.getMovieFromIntent(this)

        _binding.captionName.setText(movie.Name)
        _binding.captionCountry.setText(movie.Country)
        _binding.captionGenre.setText(movie.Genre)
        _binding.captionPegi.setText(if(movie.Pegi18) "Да" else "Нет")
        _binding.captionDuration.setText(movie.Duration.toString())
        _binding.captionYear.setText(movie.Year.toString())


        _binding.buttonToBack.setOnClickListener{
            IntentService.moveToActivityWithMovie(movie, this, ThirdActivity::class.java)
        }

        _binding.buttonToSave.setOnClickListener{
            val movieArrayList: java.util.ArrayList<Movie?> = FileService.readFromFile(
                applicationContext, FileService.fileName
            )

            movieArrayList.add(movie)

            val movieArrayListJSONString = Gson().toJson(movieArrayList)
            FileService.writeToFile(applicationContext, movieArrayListJSONString, FileService.fileName)

            Toast.makeText(applicationContext, "Save to JSON format success", Toast.LENGTH_LONG)
                .show()

            IntentService.moveToListActivity(this)
        }

        setContentView(_binding.root)
    }
}