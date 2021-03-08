package app.mohsendeadspace.com.test.Interface

import app.mohsendeadspace.com.test.Model.News
import app.mohsendeadspace.com.test.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

    @get:GET("v2/sources?apiKey=dbe4ece231984d10bc88987a74a30904")
    val sources:Call<WebSite>


    @GET
    fun getNewsFromSource(@Url url:String):Call<News>




}