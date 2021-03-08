package app.mohsendeadspace.com.test.Adapter.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import app.mohsendeadspace.com.test.Interface.ItemClickListener
import kotlinx.android.synthetic.main.news_layout.view.*

class ListNewsViewHolder(itemview:View):RecyclerView.ViewHolder(itemview),View.OnClickListener{

    private lateinit var itemClickListener: ItemClickListener

    var article_title=itemview.article_title
    var article_image=itemview.article_image

    init {
        itemview.setOnClickListener(this)
    }


    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener=itemClickListener
    }

    override fun onClick(p0: View) {
        itemClickListener.onClick(p0,adapterPosition)
    }

}