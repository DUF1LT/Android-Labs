package by.belstu.narkevich.lab3

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import by.belstu.narkevich.lab3.databinding.ActivitySecondBinding
import by.belstu.narkevich.lab3.helpers.FileService
import by.belstu.narkevich.lab3.helpers.ImageService
import by.belstu.narkevich.lab3.helpers.IntentService
import java.io.IOException


class SecondActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySecondBinding
    private var selectedImageBitmap: Bitmap? = null;
    private var selectedImageUri: Uri? = null;
    private val SELECT_PICTURE = 200

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val movie = IntentService.getMovieFromIntent(this)

        _binding.captionName.setText(movie.Name)
        _binding.captionCountry.setText(movie.Country)
        _binding.movieGenreInput.setText(movie.Genre)
        _binding.pegiCheckBox.isChecked = movie.Pegi18
        ImageService.loadImageFromStorage(movie.Image, _binding.photo)

        _binding.photo?.setOnClickListener {
            val builder = VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE)
        }

        _binding.buttonToBack.setOnClickListener {
            movie.Genre = _binding.movieGenreInput.text.toString()
            movie.Pegi18 = _binding.pegiCheckBox.isChecked

            if (selectedImageBitmap != null) {
                val imageName: String = FileService.getFileName(this, selectedImageUri)
                movie.Image = ImageService.saveImageToInternalStorageAndGetImagePath(this, selectedImageBitmap!!, imageName)
            }

            IntentService.moveToActivityWithMovie(movie, this, MainActivity::class.java)
        }

        _binding.buttonToNext.setOnClickListener {
            movie.Genre = _binding.movieGenreInput.text.toString()
            movie.Pegi18 = _binding.pegiCheckBox.isChecked

            if (selectedImageBitmap != null) {
                val imageName: String = FileService.getFileName(this, selectedImageUri)
                movie.Image = ImageService.saveImageToInternalStorageAndGetImagePath(this, selectedImageBitmap!!, imageName)
            }

            IntentService.moveToActivityWithMovie(movie, this, ThirdActivity::class.java)
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
                    _binding.photo?.setImageBitmap(selectedImageBitmap)
                }
            }
        }
    }
}