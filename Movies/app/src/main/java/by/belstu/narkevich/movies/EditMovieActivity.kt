package by.belstu.narkevich.movies

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import by.belstu.narkevich.movies.databinding.ActivityEditMovieBinding
import by.belstu.narkevich.movies.helpers.*
import by.belstu.narkevich.movies.models.Movie
import java.io.IOException

class EditMovieActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityEditMovieBinding

    private var selectedImageBitmap: Bitmap? = null;
    private var selectedImageUri: Uri? = null;
    private val SELECT_PICTURE = 200

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditMovieBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val movie = IntentService.getMovieFromIntent(this)

        AppBarService.createAppBar(this, _binding.toolbarLayout.toolbar, "Edit movie - ${movie.Name}")

        _binding.movieNameInput.setText(movie.Name)
        _binding.movieCountryInput.setText(movie.Country)
        _binding.genresAutoCompleteTextView.setText(movie.Genre)
        _binding.pegiCheckBox.isChecked = movie.Pegi18
        ImageService.loadImageFromStorage(movie.Image, _binding.photo)

        val categoryAutoCompleteTextView = _binding.genresAutoCompleteTextView
        val categories = resources.getStringArray(R.array.genres)

        val categoriesArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, categories)
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
                val imageName: String = FileService.getFileName(this, selectedImageUri)
                movie.Image = ImageService.saveImageToInternalStorageAndGetImagePath(this, selectedImageBitmap!!, imageName)
            }

            MovieService.editMovie(this, movie)

            Toast.makeText(applicationContext, "Movie was edited successfully", Toast.LENGTH_LONG)
                .show()

            IntentService.moveToMainActivity(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data?.data
                try {
                    selectedImageBitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
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