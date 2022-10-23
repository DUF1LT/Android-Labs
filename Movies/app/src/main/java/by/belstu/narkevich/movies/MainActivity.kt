package by.belstu.narkevich.movies

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.belstu.narkevich.movies.adapters.MovieAdapter
import by.belstu.narkevich.movies.databinding.ActivityMainBinding
import by.belstu.narkevich.movies.helpers.AppBarService
import by.belstu.narkevich.movies.helpers.IntentService
import by.belstu.narkevich.movies.helpers.MovieService

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private lateinit var list: RecyclerView
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        AppBarService.createAppBar(this, _binding.toolbarLayout.toolbar)

        val movies = MovieService.loadMoviesFromFile(this)

        adapter = MovieAdapter(this, movies)
        adapter.onItemClick = {
            IntentService.moveToActivityWithMovie(this, it, MovieActivity::class.java)
        }

        list = _binding.list
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter

        registerForContextMenu(list);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_item -> {
                IntentService.moveToAddNewActivity(this)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_delete -> {
                    AlertDialog.Builder(this)
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Delete") { dialog, id -> adapter.removeItem(this, item.groupId) }
                        .setNegativeButton("Cancel") { dialog, id -> }
                        .create()
                        .show()

                return true
            }
            R.id.item_details -> {
                adapter.detailsItem(this, item.groupId)

                return true
            }
            R.id.item_edit -> {
                adapter.editItem(this, item.groupId)

                return true
            }
            else ->  super.onContextItemSelected(item)
        }
    }
}