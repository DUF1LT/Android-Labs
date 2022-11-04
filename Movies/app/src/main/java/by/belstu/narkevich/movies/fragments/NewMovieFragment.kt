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
import by.belstu.narkevich.movies.databinding.FragmentNewMovieBinding
import by.belstu.narkevich.movies.helpers.*
import by.belstu.narkevich.movies.models.Movie
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.O)
class NewMovieFragment : Fragment() {
    private lateinit var _binding : FragmentNewMovieBinding

    private var selectedImageBitmap: Bitmap? = null;
    private var selectedImageUri: Uri? = null;
    private val SELECT_PICTURE = 200

    var onMovieCreated: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewMovieBinding.inflate(inflater, container, false)

        return _binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val compatActivity = activity as AppCompatActivity
        compatActivity.supportActionBar?.title = getString(R.string.newMovie)

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
            val newMovie = Movie()
            newMovie.Name = _binding.movieNameInput.text.toString()
            newMovie.Country = _binding.movieCountryInput.text.toString()
            newMovie.Genre = _binding.genresAutoCompleteTextView.text.toString()
            newMovie.Pegi18 = _binding.pegiCheckBox.isChecked

            if (selectedImageBitmap != null) {
                val imageName: String = FileService.getFileName(requireContext(), selectedImageUri)
                newMovie.Image = ImageService.saveImageToInternalStorageAndGetImagePath(requireContext(), selectedImageBitmap!!, imageName)
            }

            MovieDBHelper(context).addMovie(newMovie)

            Toast.makeText(requireContext().applicationContext, "Movie was created successfully", Toast.LENGTH_LONG)
                .show()

            onMovieCreated?.invoke()
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
}