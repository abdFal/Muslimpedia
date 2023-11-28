package com.example.muslimpedia.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muslimpedia.R
import com.example.muslimpedia.data.model.ArticlesItem
import com.example.muslimpedia.databinding.ItemRowNewsBinding
import com.example.muslimpedia.ui.detail.DetailNewsActivity
import com.example.muslimpedia.ui.detail.DetailNewsActivity.Companion.DATA_NEWS
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.MyViewHolder>(){

    private val listNews = ArrayList<ArticlesItem>()


    fun setData(list: List<ArticlesItem>?){
        listNews.clear()
        if (list != null){
            listNews.addAll(list)
        }
        notifyItemRangeChanged(0, listNews.size)
    }
    class MyViewHolder(val binding: ItemRowNewsBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(ItemRowNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listNews[position]
        val dateTimeString = data.publishedAt
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-hh'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = dateTimeFormat.parse(dateTimeString)
        val myDateFormat = SimpleDateFormat("EEE, dd MMMM | HH:mm", Locale.getDefault())

        val myDate = myDateFormat.format(date)
        holder.binding.apply {
            tvSource.text = data.source.name
            tvTitle.text = data.title
            tvDate.text = myDate

            Picasso.get()
                .load(data.urlToImage).fit().centerInside().placeholder(R.drawable.ic_logo)
                .into(imgNews)

        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.DATA_NEWS, data)
            intent.putExtra(DetailNewsActivity.DATE_NEWS, date)
            holder.itemView.context.startActivity(intent)
        }
    }
}