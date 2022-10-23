package by.belstu.narkevich.movies.helpers

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.*


class ImageService {
    companion object Static {
        fun loadImageFromStorage(path: String, imgView: ImageView?) {
            try {
                val f = File(path)
                val b = BitmapFactory.decodeStream(FileInputStream(f))
                imgView?.setImageBitmap(b)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        fun saveImageToInternalStorageAndGetImagePath(
            context: Context,
            bitmapImage: Bitmap,
            imageFileName: String
        ): String {
            val cw = ContextWrapper(context.applicationContext)
            val directory: File = cw.getDir("images", Context.MODE_PRIVATE)
            val mypath = File(directory, imageFileName)
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(mypath)
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    fos?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return mypath.toString()
        }
    }
}