package by.belstu.narkevich.news.data

import by.belstu.narkevich.news.model.News
import by.belstu.narkevich.news.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitNewsService {
    @GET("news?category=technology")
    fun getNewsList(): Call<NewsResponse>
}