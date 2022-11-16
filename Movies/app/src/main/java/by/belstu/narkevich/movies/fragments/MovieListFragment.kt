package by.belstu.narkevich.movies.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import by.belstu.narkevich.movies.R
import by.belstu.narkevich.movies.adapters.MovieAdapter
import by.belstu.narkevich.movies.adapters.MovieCursorAdapter
import by.belstu.narkevich.movies.databinding.FragmentMovieListBinding
import by.belstu.narkevich.movies.helpers.ConcurrentHelpers
import by.belstu.narkevich.movies.helpers.MovieService
import by.belstu.narkevich.movies.models.Movie

@RequiresApi(Build.VERSION_CODES.O)
class MovieListFragment(layoutManagerType: LayoutManagerType? = null) : Fragment() {
    private lateinit var _binding: FragmentMovieListBinding
    private lateinit var list: RecyclerView
    private lateinit var adapter: MovieCursorAdapter
    private var layoutManagerType: LayoutManagerType

    var onMovieClick: ((Movie) -> Unit)? = null
    var onMovieDetails: ((Movie) -> Unit)? = null
    var onMovieEdit: ((Movie) -> Unit)? = null

    companion object {
        fun createGridLayout(context: Context): LayoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        fun createLinearLayout(context: Context) : LayoutManager = LinearLayoutManager(context)
    }

    init {
        this.layoutManagerType = layoutManagerType ?: LayoutManagerType.Linear
    }

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

        adapter = MovieCursorAdapter(context)
        adapter.onItemClick = {
            onMovieClick?.invoke(it)
        }

        list = _binding.list

        when(layoutManagerType) {
            LayoutManagerType.Linear -> {
                list.layoutManager = createLinearLayout(activity as Context)
            }
            LayoutManagerType.Grid -> {
                list.layoutManager = createGridLayout(activity as Context)
            }
        }

        list.adapter = adapter

        val onFilterChange = ConcurrentHelpers.debounce<String>(300L, viewLifecycleOwner.lifecycleScope) {
            adapter.filter = it
            adapter.updateCursor()
        }

        _binding.filter.doOnTextChanged { text, start, before, count ->
            onFilterChange(text.toString())
        }

        registerForContextMenu(list)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_delete -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Warning")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Delete") { dialog, id -> adapter.removeItem(item.groupId) }
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
