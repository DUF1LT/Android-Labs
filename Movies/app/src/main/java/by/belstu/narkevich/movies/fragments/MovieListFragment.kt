package by.belstu.narkevich.movies.fragments

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.belstu.narkevich.movies.R
import by.belstu.narkevich.movies.adapters.MovieAdapter
import by.belstu.narkevich.movies.databinding.FragmentMovieListBinding
import by.belstu.narkevich.movies.helpers.MovieService
import by.belstu.narkevich.movies.models.Movie

class MovieListFragment : Fragment() {
    private lateinit var _binding: FragmentMovieListBinding
    private lateinit var list: RecyclerView
    private lateinit var adapter: MovieAdapter

    var onMovieClick: ((Movie) -> Unit)? = null
    var onMovieDetails: ((Movie) -> Unit)? = null
    var onMovieEdit: ((Movie) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)

        return _binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movies = MovieService.loadMoviesFromFile(requireContext())
        
        adapter = MovieAdapter(activity, movies)
        adapter.onItemClick = {
            onMovieClick?.invoke(it)
        }

        list = _binding.list
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = adapter

        registerForContextMenu(list)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_delete -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Warning")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Delete") { dialog, id -> adapter.removeItem(requireContext(), item.groupId) }
                    .setNegativeButton("Cancel") { dialog, id -> }
                    .create()
                    .show()

                return true
            }
            R.id.item_details -> {
                val movie = adapter.getItem(item.groupId)
                onMovieDetails?.invoke(movie)

                return true
            }
            R.id.item_edit -> {
                val movie = adapter.getItem(item.groupId)
                onMovieEdit?.invoke(movie)

                return true
            }
            else ->  super.onContextItemSelected(item)
        }
    }
}