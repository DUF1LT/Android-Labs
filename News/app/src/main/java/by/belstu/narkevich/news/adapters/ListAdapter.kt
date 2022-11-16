package by.belstu.narkevich.news.adapters

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import by.belstu.narkevich.news.R
import by.belstu.narkevich.news.model.News
import com.squareup.picasso.Picasso

class ListAdapter(private val context: Context, private val newsList: MutableList<News>):
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        val cardView: CardView = itemView.findViewById(R.id.card)
        val image: ImageView = itemView.findViewById(R.id.image)
        val author: TextView = itemView.findViewById(R.id.author)
        val title: TextView = itemView.findViewById(R.id.title)
        val date: TextView = itemView.findViewById(R.id.date)

        fun bind(listItem: News) {
            cardView.setOnCreateContextMenuListener(this);
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.setHeaderTitle(R.string.news_options)
            menu?.add(adapterPosition, R.id.readMore, 0, R.string.readMore)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    fun getItem(position: Int) = newsList[position]

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listItem = newsList[position]
        holder.bind(listItem)

        Picasso.get().load(newsList[position].imageUrl).fit().into(holder.image)
        holder.author.text = newsList[position].author
        holder.title.text = newsList[position].title
        holder.date.text = newsList[position].date
    }
}