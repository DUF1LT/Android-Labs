package by.belstu.narkevich.movies.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import by.belstu.narkevich.movies.models.Movie


@RequiresApi(Build.VERSION_CODES.O)
class MovieDBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null,  DATABASE_VERSION) {
    companion object Static {
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "MovieDB.db"

        const val ID = "id"
        const val TABLE_NAME = "movie"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_COUNTRY = "country"
        const val COLUMN_NAME_GENRE = "genre"
        const val COLUMN_NAME_PEGI = "pegi"
        const val COLUMN_NAME_IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME  ($ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, $COLUMN_NAME_NAME TEXT, $COLUMN_NAME_COUNTRY TEXT," +
                "$COLUMN_NAME_GENRE TEXT, $COLUMN_NAME_PEGI INTEGER, $COLUMN_NAME_IMAGE INTEGER)");
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addMovie(movie: Movie): String {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_NAME_NAME, movie.Name)
        values.put(COLUMN_NAME_COUNTRY, movie.Country)
        values.put(COLUMN_NAME_GENRE, movie.Genre)
        values.put(COLUMN_NAME_PEGI, if(movie.Pegi18) 1 else 0)
        values.put(COLUMN_NAME_IMAGE, movie.Image)

        val result = db.insertOrThrow(TABLE_NAME, null, values)
        db.close()

        return result.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateMovie(movie: Movie) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(ID, movie.Id.toString())
        values.put(COLUMN_NAME_NAME, movie.Name)
        values.put(COLUMN_NAME_COUNTRY, movie.Country)
        values.put(COLUMN_NAME_GENRE, movie.Genre)
        values.put(COLUMN_NAME_PEGI, if(movie.Pegi18) 1 else 0)
        values.put(COLUMN_NAME_IMAGE, movie.Image)

        val selection = "$ID LIKE ?"
        val selectionArg = arrayOf(movie.Id.toString())

        db.update(TABLE_NAME, values, selection, selectionArg)
        db.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteMovie(movie: Movie) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$ID = ?", arrayOf(movie.Id.toString()))
    }

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMovieById(id: Long) : Movie {
        val db = this.writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID LIKE ?"

        val cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))
        cursor.moveToFirst()

        val id = cursor.getLong(cursor.getColumnIndex(ID))
        val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_NAME))
        val country = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COUNTRY))
        val genre = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_GENRE))
        val pegi = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_PEGI))
        val image = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_IMAGE))

        val movie = Movie()
        movie.Id = id
        movie.Name = name
        movie.Country = country
        movie.Genre = genre
        movie.Pegi18 = pegi == 1
        movie.Image = image

        return movie
    }

    fun getCursor(filter: String? = null): Cursor {
        val db = this.readableDatabase
        var selectQuery = "SELECT * FROM $TABLE_NAME "

        if(!filter.isNullOrEmpty() || !filter.isNullOrBlank()) {
            selectQuery += "WHERE $COLUMN_NAME_NAME LIKE '%$filter%'"
        }

        Log.i("query", selectQuery)

        return db.rawQuery(selectQuery, null)
    }
}