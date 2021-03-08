package app.mohsendeadspace.com.test.Common

import app.mohsendeadspace.com.test.Interface.NewsService
import app.mohsendeadspace.com.test.Remote.Retrofitclint

object Common{
    val BASE_URL="https://newsapi.org/"
    val API_KEY="dbe4ece231984d10bc88987a74a30904"

    val newsService:NewsService
        get() = Retrofitclint.getClint(BASE_URL).create(NewsService::class.java)

    fun getNewsApi(source:String):String{

        val apiUrl=StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apikey=")
            .append(API_KEY)
            .toString()
        return apiUrl
    }
}