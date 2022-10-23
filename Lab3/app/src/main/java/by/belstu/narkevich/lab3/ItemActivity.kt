package by.belstu.narkevich.lab3

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import by.belstu.narkevich.lab3.databinding.ActivityItemBinding
import by.belstu.narkevich.lab3.helpers.ImageService
import by.belstu.narkevich.lab3.helpers.IntentService
import by.belstu.narkevich.lab3.models.Movie

class ItemActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityItemBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val movie = IntentService.getMovieFromIntent(this)

        _binding.captionName.setText(movie.Name)
        _binding.captionCountry.setText(movie.Country)
        _binding.captionGenre.setText(movie.Genre)
        _binding.captionPegi.setText(if(movie.Pegi18) "Да" else "Нет")
        _binding.captionDuration.setText(movie.Duration.toString())
        _binding.captionYear.setText(movie.Year.toString())
        ImageService.loadImageFromStorage(movie.Image, _binding.movieImage)

        _binding.toListButton.setOnClickListener {
            IntentService.moveToListActivity(this)
        }

        _binding.email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Информация по фильму")
            intent.putExtra(Intent.EXTRA_TEXT, Movie.movieInfoToText(movie))
            if (intent.resolveActivity(getPackageManager()) == null) {
                Toast.makeText(this, "unresolved package", Toast.LENGTH_SHORT).show()
            }

            startActivity(intent);
        }

        _binding.youtube.setOnClickListener {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${movie.YoutubeTrailerCode}"))
            val webIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=${movie.YoutubeTrailerCode}"))
            try {
                startActivity(appIntent)
            } catch (ex: ActivityNotFoundException) {
                startActivity(webIntent)
            }
        }
    }
}