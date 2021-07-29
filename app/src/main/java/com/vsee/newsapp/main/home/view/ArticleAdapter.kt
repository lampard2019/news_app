package com.vsee.newsapp.main.home.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vsee.newsapp.R
import com.vsee.newsapp.main.home.model.Article
import com.vsee.newsapp.utils.Utils
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleAdapter() : RecyclerView.Adapter<ArticleAdapter.DataViewHolder>() {

    var onClickListener: ((position: Int, article: Article) -> Unit)? = null
    private var data= arrayListOf<Article>()

    fun updateData(list: List<Article>){
        this.data.clear()
        this.data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener?.invoke(position, item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Article) {
            itemView.apply {
                Glide.with(item_image.context)
                    .load(data.urlToImage)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(item_image)
                item_title.text = data.title
                item_description.text = data.description
                item_timestamp.text = Utils.reformatDate(data.publishedAt)
            }
        }
    }

}