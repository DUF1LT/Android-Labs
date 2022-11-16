package by.belstu.narkevich.news.fragments

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.belstu.narkevich.news.R
import by.belstu.narkevich.news.adapters.ListAdapter
import by.belstu.narkevich.news.data.ApiHelper
import by.belstu.narkevich.news.data.RetrofitNewsService
import by.belstu.narkevich.news.databinding.FragmentListBinding
import by.belstu.narkevich.news.model.News
import by.belstu.narkevich.news.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment : Fragment() {
    private lateinit var _binding: FragmentListBinding
    lateinit var service: RetrofitNewsService
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ListAdapter
    lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        service = ApiHelper.newsService
        recycler = _binding.list
        recycler.layoutManager = LinearLayoutManager(requireContext())

        getNewsList()
    }

    private fun getNewsList() {
        service.getNewsList().enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                val responseData = response.body() as NewsResponse

                adapter = ListAdapter(requireContext(), responseData.data!!)
                adapter.notifyDataSetChanged()

                recycler.adapter = adapter
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.readMore -> {
                val news = adapter.getItem(item.groupId)
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("${news.readMoreUrl}"))
                startActivity(browserIntent)

                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}