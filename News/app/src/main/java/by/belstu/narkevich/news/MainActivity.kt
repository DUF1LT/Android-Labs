package by.belstu.narkevich.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.belstu.narkevich.news.databinding.ActivityMainBinding
import by.belstu.narkevich.news.fragments.ListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setSupportActionBar(_binding.toolbarLayout.toolbar)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, ListFragment())
            commit()
        }
    }
}