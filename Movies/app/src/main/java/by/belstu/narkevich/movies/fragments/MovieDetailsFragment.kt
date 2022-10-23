package by.belstu.narkevich.movies.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.belstu.narkevich.movies.R
import by.belstu.narkevich.movies.databinding.FragmentMovieDetailsBinding
import by.belstu.narkevich.movies.helpers.ImageService
import by.belstu.narkevich.movies.helpers.MovieService
import java.util.*

class MovieDetailsFragment(movieId: UUID? = null) : Fragment() {
    private var movieId: UUID?
    private var movieIdKey: String = "MovieIdKey"

    private lateinit var _binding : FragmentMovieDetailsBinding

    init {
        this.movieId = movieId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState != null) {
            movieId = UUID.fromString(savedInstanceState.getString(movieIdKey))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return _binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = MovieService.getMovie(requireContext(), movieId!!)

        val compatActivity = activity as AppCompatActivity
        compatActivity.supportActionBar?.title = getString(R.string.movie) + " - " + movie.Name

        _binding.captionName.setText(movie.Name)
        _binding.captionCountry.setText(movie.Country)
        _binding.captionGenre.setText(movie.Genre)
        _binding.captionPegi.setText(if(movie.Pegi18) "Да" else "Нет")
        ImageService.loadImageFromStorage(movie.Image, _binding.movieImage)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(movieIdKey, movieId.toString())
    }
}