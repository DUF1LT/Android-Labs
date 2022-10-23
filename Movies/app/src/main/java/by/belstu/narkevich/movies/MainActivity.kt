package by.belstu.narkevich.movies

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import by.belstu.narkevich.movies.databinding.ActivityMainBinding
import by.belstu.narkevich.movies.fragments.EditMovieFragment
import by.belstu.narkevich.movies.fragments.MovieDetailsFragment
import by.belstu.narkevich.movies.fragments.MovieListFragment
import by.belstu.narkevich.movies.fragments.NewMovieFragment
import by.belstu.narkevich.movies.helpers.AppBarService
import by.belstu.narkevich.movies.helpers.FragmentManagerService

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    private lateinit var _movieListFragment : MovieListFragment
    private lateinit var _movieDetailsFragment : MovieDetailsFragment
    private lateinit var _newMovieFragment : NewMovieFragment
    private lateinit var _editMovieFragment : EditMovieFragment

    var infoLayoutId: Int = R.id.frameLayout;

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        AppBarService.createAppBar(this, _binding.toolbarLayout.toolbar)

        if(_binding.secondFrameLayout != null) {
            infoLayoutId = R.id.secondFrameLayout
        }

        _movieListFragment = MovieListFragment()
        FragmentManagerService.openMovieFragment(supportFragmentManager, R.id.frameLayout, _movieListFragment)

        supportActionBar?.title = getString(R.string.app_name)

        _movieListFragment.onMovieClick = {
            _movieDetailsFragment = MovieDetailsFragment(it.Id)
            FragmentManagerService.openMovieFragment(supportFragmentManager, infoLayoutId, _movieDetailsFragment, true)
        }

        _movieListFragment.onMovieDetails = {
            _movieDetailsFragment = MovieDetailsFragment(it.Id)
            FragmentManagerService.openMovieFragment(supportFragmentManager, infoLayoutId, _movieDetailsFragment, true)
        }

        _movieListFragment.onMovieEdit = {
            _editMovieFragment = EditMovieFragment(it.Id)
            _editMovieFragment.onMovieEdited = {
                FragmentManagerService.openMovieFragmentWithRemove(supportFragmentManager, R.id.frameLayout, _movieListFragment, true)
            }
            FragmentManagerService.openMovieFragment(supportFragmentManager, infoLayoutId, _editMovieFragment, true)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onResume() {
        super.onResume()

        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_item -> {
                _newMovieFragment = NewMovieFragment()
                _newMovieFragment.onMovieCreated = {
                    FragmentManagerService.openMovieFragmentWithRemove(supportFragmentManager, R.id.frameLayout, _movieListFragment, true)
                }

                FragmentManagerService.openMovieFragment(supportFragmentManager, infoLayoutId, _newMovieFragment, true)
                return true
            }
            android.R.id.home -> {
                FragmentManagerService.openMovieFragment(supportFragmentManager, infoLayoutId, _movieListFragment, true)

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}