package by.belstu.narkevich.movies.helpers

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AppBarService {
    companion object Static {
        fun createAppBar(appCompatActivity: AppCompatActivity, toolbar: Toolbar, title: String? = null) {
            appCompatActivity.setSupportActionBar(toolbar)
            if(title != null)
            {
                appCompatActivity.supportActionBar?.title = title
                appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }
}