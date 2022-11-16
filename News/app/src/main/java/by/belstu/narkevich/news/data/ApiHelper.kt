package by.belstu.narkevich.news.data

object ApiHelper {
    private val BASE_URL = "https://inshorts.deta.dev/"

    val newsService: RetrofitNewsService
        get() = RetrofitBuilder.getClient(BASE_URL).create(RetrofitNewsService::class.java)
}