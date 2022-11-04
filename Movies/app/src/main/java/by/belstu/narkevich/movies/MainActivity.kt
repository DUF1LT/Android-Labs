package by.belstu.narkevich.movies

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.belstu.narkevich.movies.databinding.ActivityMainBinding
import by.belstu.narkevich.movies.fragments.*
import by.belstu.narkevich.movies.helpers.AppBarService
import by.belstu.narkevich.movies.helpers.FragmentManagerService
import com.google.android.material.navigation.NavigationView

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var _binding: ActivityMainBinding

    private lateinit var drawer: DrawerLayout

    private lateinit var _movieListFragment : MovieListFragment
    private lateinit var _movieDetailsFragment : MovieDetailsFragment
    private lateinit var _newMovieFragment : NewMovieFragment
    private lateinit var _editMovieFragment : EditMovieFragment

    private var _isListLinearLayoutManager: Boolean = true;

    var infoLayoutId: Int = R.id.frameLayout;

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        AppBarService.createAppBar(this, _binding.toolbarLayout.toolbar)

        drawer = _binding.drawerLayout
        val navigationView = _binding.navView
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawer, _binding.toolbarLayout.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        if(_binding.secondFrameLayout != null) {
            infoLayoutId = R.id.secondFrameLayout
        }

        registerMovieListFragment(LayoutManagerType.Linear)

        supportActionBar?.title = getString(R.string.app_name)

    }

    override fun onBackPressed() {
        supportActionBar?.title = getString(R.string.app_name)

        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }

        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
            return
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
        }

        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()

        supportActionBar?.title = getString(R.string.app_name)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val item = menu?.findItem(R.id.swap_layout)
        if(_isListLinearLayoutManager) {
            item?.icon = getDrawable(R.drawable.ic_baseline_grid_on_24)
        } else {
            item?.icon = getDrawable(R.drawable.ic_baseline_list_24)
        }

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
            R.id.swap_layout -> {
                if(_isListLinearLayoutManager) {
                    registerMovieListFragment(LayoutManagerType.Grid)
                } else {
                    registerMovieListFragment(LayoutManagerType.Linear)
                }

                _isListLinearLayoutManager = !_isListLinearLayoutManager

                return true
            }
            android.R.id.home -> {
                FragmentManagerService.openMovieFragment(supportFragmentManager, infoLayoutId, _movieListFragment, true)

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun registerMovieListFragment(layoutManagerType: LayoutManagerType) {
        _movieListFragment = MovieListFragment(layoutManagerType)

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

        FragmentManagerService.openMovieFragment(supportFragmentManager, R.id.frameLayout, _movieListFragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.drawer_add_new -> {
                _newMovieFragment = NewMovieFragment()
                _newMovieFragment.onMovieCreated = {
                    FragmentManagerService.openMovieFragmentWithRemove(supportFragmentManager, R.id.frameLayout, _movieListFragment, true)
                }

                FragmentManagerService.openMovieFragment(supportFragmentManager, infoLayoutId, _newMovieFragment, true)
            }
            R.id.drawer_swap_layout -> {
                if(_isListLinearLayoutManager) {
                    registerMovieListFragment(LayoutManagerType.Grid)
                } else {
                    registerMovieListFragment(LayoutManagerType.Linear)
                }

                _isListLinearLayoutManager = !_isListLinearLayoutManager
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}