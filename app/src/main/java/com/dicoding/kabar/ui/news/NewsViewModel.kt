package com.dicoding.kabar.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.kabar.data.KabarRepository
import com.dicoding.kabar.data.source.local.entity.NewsEntity
import com.dicoding.kabar.data.source.remote.ApiResponse
import com.dicoding.kabar.data.source.remote.response.NewsResponse
import com.dicoding.kabar.utils.ApiKey
import com.dicoding.moviecatalogue.valueobject.Resource
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class NewsViewModel() : ViewModel() {

    val listNews = MutableLiveData<ArrayList<NewsResponse>>()

    fun setNews() {
        val listIt = ArrayList<NewsResponse>()
        val client = AsyncHttpClient()
        val url = "https://newsapi.org/v2/everything?q=waste%20pollution&apiKey=${ApiKey.apiKey}"
        client.addHeader("Authorization", ApiKey.apiKey)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try { //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val articles = responseObject.getJSONArray("articles")

                    for (i in 0 until 20) {
                        val item = articles.getJSONObject(i)

                        val title = item.getString("title")
                        val image = item.getString("urlToImage")
                        val url = item.getString("url")
                        val newsResponse = NewsResponse(title, image, url)
                        listIt.add(newsResponse)
                        Log.d("listIt", listIt.toString())
                    }
                    listNews.postValue(listIt)
                    Log.d("listNews", listNews.toString())
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getNews(): LiveData<ArrayList<NewsResponse>> {
        return listNews
    }
}