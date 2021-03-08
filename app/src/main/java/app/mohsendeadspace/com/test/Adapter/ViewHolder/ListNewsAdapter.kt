package app.mohsendeadspace.com.test.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.mohsendeadspace.com.test.Interface.ItemClickListener
import app.mohsendeadspace.com.test.Model.Article
import app.mohsendeadspace.com.test.NewsDetail
import app.mohsendeadspace.com.test.R
import com.squareup.picasso.Picasso

class ListNewsAdapter(val articleList:MutableList<Article>,private val context: Context):RecyclerView.Adapter<ListNewsViewHolder>(){


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListNewsViewHolder {
        val inflater=LayoutInflater.from(p0!!.context)
        val itemView=inflater.inflate(R.layout.news_layout,p0,false)
        return ListNewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(p0: ListNewsViewHolder, p1: Int) {
        Picasso.with(context)
            .load(articleList[p1]!!.urlToImage)
            .into(p0!!.article_image)

        if(articleList[p1].title!!.length > 65){

            p0.article_title.text=articleList[p1].title!!.substring(0,65)+"..."

        }
        else{
            p0.article_title.text=articleList[p1].title!!

        }
        /*if(articleList[p1].publishedAt !=null){
            var date:Date?=null
            try {
                date=ISO8601Parser.parse(articleList[p1].publishedAt!!)
            }catch (ex:ParseException){
                ex.printStackTrace()
            }
          //  p0.article_time.setReferenceTime(date!!.time)
        }*/

        // set event click
        p0.setItemClickListener(object :ItemClickListener{
            override fun onClick(view: View, position: Int) {

                val detail=Intent(context,NewsDetail::class.java)
                detail.putExtra("webURL",articleList[position].url)
                context.startActivity(detail)

            }

        })

    }

}