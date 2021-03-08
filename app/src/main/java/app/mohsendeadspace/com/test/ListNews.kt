package app.mohsendeadspace.com.test

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import app.mohsendeadspace.com.test.Adapter.ViewHolder.ListNewsAdapter
import app.mohsendeadspace.com.test.Common.Common
import app.mohsendeadspace.com.test.Interface.NewsService
import app.mohsendeadspace.com.test.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {



    var source=""
    var webHotUrl:String?=""
    lateinit var dialog:android.app.AlertDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        //init view
        mService=Common.newsService
        dialog=SpotsDialog(this)
        swipe_to_refrish.setOnRefreshListener { (loadNews(source,true)) }

        diagonallayout.setOnClickListener{

            val detail= Intent(baseContext,NewsDetail::class.java)
            detail.putExtra("webURL",webHotUrl)
            startActivity(detail)

        }

        list_news.setHasFixedSize(true)
        list_news.layoutManager=LinearLayoutManager(this)


        if (intent != null){
            source=intent.getStringExtra("source")
            if(!source.isEmpty())
                loadNews(source,false)


        }
    }

    private fun loadNews(source: String?, isRefreshed: Boolean) {
        if (isRefreshed){
            dialog.show()
            mService.getNewsFromSource(Common.getNewsApi(source!!))
                .enqueue(object :Callback<News>{
                    override fun onFailure(call: Call<News>, t: Throwable) {

                    }
                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        dialog.dismiss()

                        //get first article to not news
                        Picasso.with(baseContext)
                            .load(response!!.body()!!.articles!![0].urlToImage)
                            .into(tap_image)
                        top_title.text=response!!.body()!!.articles!![0].title
                        top_author.text=response!!.body()!!.articles!![0].author

                        webHotUrl=response!!.body()!!.articles!![0].url
                        //load all reamin article
                        var removeFirstItem=response!!.body()!!.articles
                        //because we get first item to not new,so we need removed it.
                        removeFirstItem!!.removeAt(0)
                        adapter= ListNewsAdapter(removeFirstItem,baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter=adapter
                    }

                })
        }

        else
        {
            swipe_to_refrish.isRefreshing=true
            mService.getNewsFromSource(Common.getNewsApi(source!!))
                .enqueue(object :Callback<News>{
                    override fun onFailure(call: Call<News>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        swipe_to_refrish.isRefreshing=false

                        //get first article to not news
                        Picasso.with(baseContext)
                            .load(response!!.body()!!.articles!![0].urlToImage)
                            .into(tap_image)

                        top_title.text=response!!.body()!!.articles!![0].title
                        top_author.text=response!!.body()!!.articles!![0].author

                        webHotUrl=response!!.body()!!.articles!![0].url


                        //load all reamin article
                        var removeFirstItem=response!!.body()!!.articles
                        //because we get first item to not new,so we need removed it.
                        removeFirstItem!!.removeAt(0)
                        adapter= ListNewsAdapter(removeFirstItem,baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter=adapter
                    }

                })

        }

    }
}
