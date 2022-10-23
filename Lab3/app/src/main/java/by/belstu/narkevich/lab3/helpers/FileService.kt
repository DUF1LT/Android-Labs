package by.belstu.narkevich.lab3.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import by.belstu.narkevich.lab3.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type

class FileService {
    companion object Static {
        var fileName = "movieList.json"

        fun writeToFile(context: Context, data: String, fileName: String) {
            val dir = File(context.filesDir, "lab3")
            if (!dir.exists()) {
                dir.mkdir()
            }
            try {
                val file = File(dir, fileName)
                val writer = FileWriter(file)
                writer.append(data)
                writer.flush()
                writer.close()
            } catch (e: Exception) {
                Log.e("Exception", "File write failed: $e")
            }
        }

        fun readFromFile(context: Context, fileName: String): ArrayList<Movie?> {
            var movieArrayList: ArrayList<Movie?> = ArrayList()
            val dir = File(context.filesDir, "lab3")
            if (dir.exists()) {
                try {
                    Log.i("Path", dir.absolutePath + "/" + fileName)
                    FileReader(dir.absolutePath + "/" + fileName).use {
                        val strings = it.readLines().reduce { acc, string -> acc + string}
                        val gson = Gson()
                        val listMovieType: Type = object : TypeToken<ArrayList<Movie?>?>() {}.getType()
                        movieArrayList = gson.fromJson(strings, listMovieType)
                    }
                } catch (e: IOException) {
                    Log.e("Exception", "File read failed: $e")
                }
            } else {
                dir.createNewFile()
            }

            return movieArrayList
        }

        @SuppressLint("Range")
        fun getFileName(context: Context, uri: Uri?): String {
            var result: String? = null
            if (uri?.scheme == "content") {
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                } finally {
                    cursor?.close()
                }
            }
            if (result == null) {
                result = uri?.path
                val cut = result!!.lastIndexOf('/')
                if (cut != -1) {
                    result = result.substring(cut + 1)
                }
            }
            return result
        }
    }
}