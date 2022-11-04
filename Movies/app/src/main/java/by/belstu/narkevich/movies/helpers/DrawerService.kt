package by.belstu.narkevich.movies.helpers

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import by.belstu.narkevich.movies.MainActivity
import by.belstu.narkevich.movies.R
import com.google.android.material.navigation.NavigationView

class DrawerService {
    companion object Static {
        fun createDrawer(activity: MainActivity, drawerLayout: DrawerLayout, toolbar: Toolbar, navigationView: NavigationView) {
            val toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
        }
    }
}