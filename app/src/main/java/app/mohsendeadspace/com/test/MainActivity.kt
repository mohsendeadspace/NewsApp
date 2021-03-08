package app.mohsendeadspace.com.test

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import app.mohsendeadspace.com.test.Adapter.ViewHolder.ListSourceAdapter
import app.mohsendeadspace.com.test.Common.Common
import app.mohsendeadspace.com.test.Interface.NewsService
import app.mohsendeadspace.com.test.Model.WebSite
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init cach DB
        Paper.init(this)
        //init service
        mService=Common.newsService

        //init view
        swipe_to_refrish.setOnRefreshListener{
            loadWebSiteSource(true)
        }
        recycler_view_source_news.setHasFixedSize(true)
        layoutManager= LinearLayoutManager(this)
        recycler_view_source_news.layoutManager=layoutManager
        dialog=SpotsDialog(this)
        loadWebSiteSource(false)
    }

    private fun loadWebSiteSource(isRefresh: Boolean) {


        if(!isRefresh){
            val cache=Paper.book().read<String>("cache")

            if(cache !=null && !cache.isBlank() && cache !="null"){

                //read cache
                val webSite=Gson().fromJson<WebSite>(cache,WebSite::class.java)
                adapter= ListSourceAdapter(baseContext,webSite)
                adapter.notifyDataSetChanged()
                recycler_view_source_news.adapter=adapter
            }
            else
            {
                //load web site and write cahce
                dialog.show()
                //fetch new data
                mService.sources.enqueue(object :retrofit2.Callback<WebSite>{


                    override fun onFailure(call: Call<WebSite>, t: Throwable) {
                        Toast.makeText(baseContext,"Failed",Toast.LENGTH_SHORT).show()
                    }

                    //ctrl+o
                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                        adapter= ListSourceAdapter(baseContext, response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter=adapter

                        //save to cache
                        Paper.book().write("cache",Gson().toJson(response!!.body()!!))

                        dialog.dismiss()
                    }
                })
            }

        }
        else
        {
            swipe_to_refrish.isRefreshing=true

            //fetch new data
            mService.sources.enqueue(object :retrofit2.Callback<WebSite>{
                override fun onFailure(call: Call<WebSite>, t: Throwable) {
                    Toast.makeText(baseContext,"Failed",Toast.LENGTH_SHORT).show()
                }

                //ctrl+o
                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                    adapter= ListSourceAdapter(baseContext, response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view_source_news.adapter=adapter

                    //save to cache
                    Paper.book().write("cache",Gson().toJson(response!!.body()!!))

                    swipe_to_refrish.isRefreshing=false
                }
            })
        }



    }
}
