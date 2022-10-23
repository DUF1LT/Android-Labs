package by.belstu.narkevich.movies

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import by.belstu.narkevich.movies.databinding.ActivityMovieBinding
import by.belstu.narkevich.movies.helpers.AppBarService
import by.belstu.narkevich.movies.helpers.ImageService
import by.belstu.narkevich.movies.helpers.IntentService

class MovieActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMovieBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        AppBarService.createAppBar(this, _binding.toolbarLayout.toolbar)

        val movie = IntentService.getMovieFromIntent(this)

        _binding.captionName.setText(movie.Name)
        _binding.captionCountry.setText(movie.Country)
        _binding.captionGenre.setText(movie.Genre)
        _binding.captionPegi.setText(if(movie.Pegi18) "Да" else "Нет")
        ImageService.loadImageFromStorage(movie.Image, _binding.movieImage)
    }
}