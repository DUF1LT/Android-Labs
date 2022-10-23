package by.belstu.narkevich.lab3

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.updateLayoutParams
import by.belstu.narkevich.lab3.databinding.ActivityListBinding
import by.belstu.narkevich.lab3.helpers.FileService
import by.belstu.narkevich.lab3.helpers.IntentService
import by.belstu.narkevich.lab3.models.Movie

class ListActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityListBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListBinding.inflate(layoutInflater);
        setContentView(_binding.root)

        val table = _binding.moviesTableLayout

        _binding.createButton.setOnClickListener{
            IntentService.moveToCreateActivity(this)
        }

        val movieArrayList: java.util.ArrayList<Movie?> = FileService.readFromFile(
            applicationContext, FileService.fileName
        )

        table.removeAllViews()
        for (movie in movieArrayList) {
            val row = TableRow(this)

            val text = TextView(this)
            text.text = movie!!.Name
            text.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Medium)

            val infoButton = Button(this)
            infoButton.text = "Информация"
            infoButton.setOnClickListener {
                val itemIntent = Intent(this, ItemActivity::class.java)
                itemIntent.putExtra("Movie", movie)
                startActivity(itemIntent)
            }

            row.addView(text)
            row.addView(infoButton)

            table.addView(row)
        }

        _binding.phone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel:" + (it as TextView).text);
            if (intent.resolveActivity(getPackageManager()) == null) {
                Toast.makeText(this, "unresolved package", Toast.LENGTH_SHORT).show()
            }
            startActivity(intent);
        }
    }

}