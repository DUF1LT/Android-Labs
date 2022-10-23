package by.belstu.narkevich.movies

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import by.belstu.narkevich.movies.databinding.ActivityNewMovieBinding
import by.belstu.narkevich.movies.helpers.*
import by.belstu.narkevich.movies.models.Movie
import java.io.IOException


class NewMovieActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityNewMovieBinding

    private var selectedImageBitmap: Bitmap? = null;
    private var selectedImageUri: Uri? = null;
    private val SELECT_PICTURE = 200

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewMovieBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        AppBarService.createAppBar(this, _binding.toolbarLayout.toolbar, "New movie")

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
            val newMovie: Movie = Movie()
            newMovie.Name = _binding.movieNameInput.text.toString()
            newMovie.Country = _binding.movieCountryInput.text.toString()
            newMovie.Genre = _binding.genresAutoCompleteTextView.text.toString()
            newMovie.Pegi18 = _binding.pegiCheckBox.isChecked

            if (selectedImageBitmap != null) {
                val imageName: String = FileService.getFileName(this, selectedImageUri)
                newMovie.Image = ImageService.saveImageToInternalStorageAndGetImagePath(this, selectedImageBitmap!!, imageName)
            }

            MovieService.addMovie(this, newMovie)

            Toast.makeText(applicationContext, "Movie was created successfully", Toast.LENGTH_LONG)
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