package by.belstu.narkevich.movies.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.belstu.narkevich.movies.R
import by.belstu.narkevich.movies.database.MovieDBHelper
import by.belstu.narkevich.movies.databinding.FragmentEditMovieBinding
import by.belstu.narkevich.movies.helpers.*
import java.io.IOException
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class EditMovieFragment(movieId: Long = 0) : Fragment() {
    private var movieId: Long
    private var movieIdKey: String = "MovieIdKey"

    private lateinit var _binding : FragmentEditMovieBinding

    private var selectedImageBitmap: Bitmap? = null;
    private var selectedImageUri: Uri? = null;
    private val SELECT_PICTURE = 200

    var onMovieEdited: (() -> Unit)? = null

    init {
        this.movieId = movieId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState != null) {
            movieId = savedInstanceState.getLong(movieIdKey)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditMovieBinding.inflate(inflater, container, false)

        return _binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = MovieDBHelper(context).getMovieById(movieId!!)

        val compatActivity = activity as AppCompatActivity
        compatActivity.supportActionBar?.title = getString(R.string.editMovie) + " - " + movie.Name

        _binding.movieNameInput.setText(movie.Name)
        _binding.movieCountryInput.setText(movie.Country)
        _binding.genresAutoCompleteTextView.setText(movie.Genre)
        _binding.pegiCheckBox.isChecked = movie.Pegi18
        ImageService.loadImageFromStorage(movie.Image, _binding.photo)

        val categoryAutoCompleteTextView = _binding.genresAutoCompleteTextView
        val categories = resources.getStringArray(R.array.genres)

        val categoriesArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, categories)
        categoryAutoCompleteTextView.setAdapter(categoriesArrayAdapter)

        _binding.photo.setOnClickListener {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
        }

        _binding.buttonToSave.setOnClickListener {
            movie.Name = _binding.movieNameInput.text.toString()
            movie.Country = _binding.movieCountryInput.text.toString()
            movie.Genre = _binding.genresAutoCompleteTextView.text.toString()
            movie.Pegi18 = _binding.pegiCheckBox.isChecked

            if (selectedImageBitmap != null) {
                val imageName: String = FileService.getFileName(requireContext(), selectedImageUri)
                movie.Image = ImageService.saveImageToInternalStorageAndGetImagePath(requireContext(), selectedImageBitmap!!, imageName)
            }

            MovieDBHelper(context).updateMovie(movie)

            Toast.makeText(requireContext().applicationContext, "Movie was edited successfully", Toast.LENGTH_LONG)
                .show()

            onMovieEdited?.invoke()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data?.data
                try {
                    selectedImageBitmap =
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageUri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (null != selectedImageBitmap) {
                    _binding.photo.setImageBitmap(selectedImageBitmap)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(movieIdKey, movieId)
    }
}