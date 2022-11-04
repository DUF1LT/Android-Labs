package by.belstu.narkevich.movies.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import by.belstu.narkevich.movies.R
import by.belstu.narkevich.movies.database.MovieDBHelper
import by.belstu.narkevich.movies.helpers.ImageService
import by.belstu.narkevich.movies.models.Movie
import java.io.InvalidObjectException

@RequiresApi(Build.VERSION_CODES.O)
class MovieCursorAdapter internal constructor(context: Context?) : RecyclerView.Adapter<MovieCursorAdapter.ViewHolder>() {
    var filter: String? = null

    private val inflater: LayoutInflater
    private val dbHelper = MovieDBHelper(context)
    private var _cursor: Cursor? = null

    private var Cursor: Cursor
        get() = _cursor ?: throw InvalidObjectException("Cursor is null")
        set(value) {
            _cursor = value
        }

    var onItemClick: ((Movie) -> Unit)? = null

    init {
        setHasStableIds(true)
        Cursor = dbHelper.getCursor()
        inflater = LayoutInflater.from(context)
    }

    fun updateCursor() {
        Cursor.close()
        Cursor = dbHelper.getCursor(filter)

        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCursorAdapter.ViewHolder {
        val view: View = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MovieCursorAdapter.ViewHolder, position: Int) {
        val movie: Movie = getItem(position)
        ImageService.loadImageFromStorage(movie.Image, holder.imageView)
        holder.nameView.setText(movie.Name)
        holder.genreView.setText(movie.Genre)
        holder.countryView.setText(movie.Country)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    inner class ViewHolder internal constructor(view: View) :
        RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        val cardView: CardView
        val imageView: ImageView
        val nameView: TextView
        val genreView: TextView
        val countryView: TextView

        init {
            cardView = view.findViewById(R.id.card)
            imageView = view.findViewById(R.id.image)
            nameView = view.findViewById(R.id.name)
            genreView = view.findViewById(R.id.genre)
            countryView = view.findViewById(R.id.country)
            cardView.setOnCreateContextMenuListener(this);
            view.setOnClickListener {
                onItemClick?.invoke(getItem(adapterPosition))
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.setHeaderTitle(R.string.itemOptions)
            menu?.add(adapterPosition, R.id.item_details, 0, R.string.details)
            menu?.add(adapterPosition, R.id.item_edit, 1, R.string.edit)
            menu?.add(adapterPosition, R.id.item_delete, 2, R.string.delete)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun removeItem(position: Int) {
        val movie = getItem(position)
        dbHelper.deleteMovie(movie)
        updateCursor()
    }

    override fun getItemCount() = Cursor.count

    @SuppressLint("Range")
    override fun getItemId(position: Int): Long {
        Cursor.moveToPosition(position)
        return Cursor.getLong(Cursor.getColumnIndex(MovieDBHelper.ID))
    }

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getItem(position: Int) : Movie {
        Cursor.moveToPosition(position)
        val id = Cursor.getLong(Cursor.getColumnIndex(MovieDBHelper.ID))
        val name = Cursor.getString(Cursor.getColumnIndex(MovieDBHelper.COLUMN_NAME_NAME))
        val country = Cursor.getString(Cursor.getColumnIndex(MovieDBHelper.COLUMN_NAME_COUNTRY))
        val genre = Cursor.getString(Cursor.getColumnIndex(MovieDBHelper.COLUMN_NAME_GENRE))
        val pegi = Cursor.getInt(Cursor.getColumnIndex(MovieDBHelper.COLUMN_NAME_PEGI))
        val image = Cursor.getString(Cursor.getColumnIndex(MovieDBHelper.COLUMN_NAME_IMAGE))

        val movie = Movie()
        movie.Id = id
        movie.Name = name
        movie.Country = country
        movie.Genre = genre
        movie.Pegi18 = pegi == 1
        movie.Image = image

        return movie
    }
}