package app.mohsendeadspace.com.test.Remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofitclint {
    private var retrofit:Retrofit?=null

    fun getClint(baseUrl:String?):Retrofit{

        if(retrofit==null){

            retrofit=Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

}