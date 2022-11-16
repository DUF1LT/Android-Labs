package by.belstu.narkevich.news.model

data class NewsResponse(
    var category: String? = null,
    var data: MutableList<News>? = null,
    var success: Boolean? = null,
)