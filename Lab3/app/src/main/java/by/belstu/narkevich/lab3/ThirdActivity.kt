package by.belstu.narkevich.lab3

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import by.belstu.narkevich.lab3.databinding.ActivityThirdBinding
import by.belstu.narkevich.lab3.helpers.IntentService
import by.belstu.narkevich.lab3.models.Movie
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

class ThirdActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityThirdBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityThirdBinding.inflate(layoutInflater)

        _binding.yearPicker.minValue = 1990
        _binding.yearPicker.maxValue = 2022
        _binding.yearPicker.wrapSelectorWheel = true

        val movie = IntentService.getMovieFromIntent(this)

        _binding.captionName.setText(movie.Name)
        _binding.captionCountry.setText(movie.Country)
        _binding.captionGenre.setText(movie.Genre)
        _binding.captionPegi.setText(if(movie.Pegi18) "Да" else "Нет")
        _binding.captionPegi.setText(movie.YoutubeTrailerCode)

        _binding.yearPicker.value = movie.Year
        _binding.durationInput.setText(movie.Duration.toString())

        _binding.buttonToBack.setOnClickListener{
            movie.Year = _binding.yearPicker.value
            movie.Duration = _binding.durationInput.text.toString().toInt()
            movie.YoutubeTrailerCode = _binding.youtubeCode!!.text.toString()

            IntentService.moveToActivityWithMovie(movie, this, SecondActivity::class.java)
        }

        _binding.buttonToNext.setOnClickListener {
            movie.Year = _binding.yearPicker.value
            movie.Duration = _binding.durationInput.text.toString().toInt()
            movie.YoutubeTrailerCode = _binding.youtubeCode!!.text.toString()

            IntentService.moveToActivityWithMovie(movie, this, FinalActivity::class.java)
        };

        setContentView(_binding.root)
    }
}