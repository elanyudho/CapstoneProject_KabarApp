package com.dicoding.kabar.ui.news

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.kabar.R
import com.dicoding.kabar.data.source.remote.response.NewsResponse
import com.dicoding.kabar.databinding.ItemMovieTvshowListBinding
import com.dicoding.kabar.databinding.NewsListBinding
import com.dicoding.kabar.ui.webview.webviewnews.WebViewNewsActivity

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val mData = ArrayList<NewsResponse>()

    fun setData(items: ArrayList<NewsResponse>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    class NewsViewHolder(private val binding: NewsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsResponse) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(news.image)
                    .apply(
                        RequestOptions().placeholder(R.drawable.ic_baseline_loading)
                            .error(R.drawable.ic_baseline_broken_image)
                    )
                    .into(newsImage)
               newsTitle.text = news.title

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, WebViewNewsActivity::class.java)
                    intent.putExtra(WebViewNewsActivity.EXTRA_WV, news.url)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NewsViewHolder {
        val itemNewsList = NewsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(itemNewsList)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = mData[position]
        if (news != null) {
            holder.bind(news)
        }

    }

    override fun getItemCount(): Int {
        return mData.size
    }
}