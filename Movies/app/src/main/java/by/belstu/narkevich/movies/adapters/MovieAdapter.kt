package by.belstu.narkevich.movies.adapters

import android.content.Context;
import android.os.Build
import android.view.ContextMenu
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView

import androidx.recyclerview.widget.RecyclerView;
import by.belstu.narkevich.movies.helpers.ImageService
import by.belstu.narkevich.movies.models.Movie
import by.belstu.narkevich.movies.R
import by.belstu.narkevich.movies.helpers.MovieService


class MovieAdapter internal constructor(context: Context?, movies: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private val movies: ArrayList<Movie>

    var onItemClick: ((Movie) -> Unit)? = null

    init {
        this.movies = movies
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie: Movie = movies[position]
        ImageService.loadImageFromStorage(movie.Image, holder.imageView)
        holder.nameView.setText(movie.Name)
        holder.genreView.setText(movie.Genre)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder internal constructor(view: View) :
        RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        val cardView: CardView
        val imageView: ImageView
        val nameView: TextView
        val genreView: TextView

        init {
            cardView = view.findViewById(R.id.card)
            imageView = view.findViewById(R.id.image)
            nameView = view.findViewById(R.id.name)
            genreView = view.findViewById(R.id.genre)
            cardView.setOnCreateContextMenuListener(this);
            view.setOnClickListener {
                onItemClick?.invoke(movies[adapterPosition])
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
    fun getItem(position: Int) = movies[position]

    @RequiresApi(Build.VERSION_CODES.O)
    fun removeItem(context: Context, position: Int) {
        val movie = movies[position]
        movies.removeAt(position)

        MovieService.removeMovie(context, movie.Id)
        notifyDataSetChanged()
    }
}