package by.belstu.narkevich.lab3

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import by.belstu.narkevich.lab3.databinding.ActivityMainBinding
import by.belstu.narkevich.lab3.helpers.IntentService
import by.belstu.narkevich.lab3.models.Movie

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        val movie = IntentService.getMovieFromIntent(this)

        _binding.movieNameInput.setText(movie.Name)
        _binding.movieCountryInput.setText(movie.Country)

        _binding.buttonToNext.setOnClickListener {
            movie.Name = _binding.movieNameInput.text.toString()
            movie.Country = _binding.movieCountryInput.text.toString()

            IntentService.moveToActivityWithMovie(movie, this, SecondActivity::class.java)
        };

        setContentView(_binding.root)
    }
}

