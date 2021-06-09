package com.dicoding.kabar.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.kabar.data.source.local.LocalDataSource
import com.dicoding.kabar.data.source.local.entity.NewsEntity
import com.dicoding.kabar.data.source.remote.ApiResponse
import com.dicoding.kabar.data.source.remote.RemoteDataSource
import com.dicoding.kabar.data.source.remote.response.NewsResponse
import com.dicoding.kabar.utils.AppExecutors
import com.dicoding.moviecatalogue.valueobject.Resource

class KabarRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : KabarDataSource {

    companion object {
        @Volatile
        private var instance: KabarRepository? = null


        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): KabarRepository =
            instance ?: synchronized(this) {
                instance
                    ?: KabarRepository(remoteData, localData, appExecutors).apply {
                        instance = this
                    }
            }
    }

    /*override fun getNews(): LiveData<Resource<PagedList<NewsEntity>>> {
        return object :
            NetworkBoundResource<PagedList<NewsEntity>, List<NewsResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<NewsEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getNews(), config).build()
            }

            override fun shouldFetch(data: PagedList<NewsEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<NewsResponse>>> {
                return remoteDataSource.getAllNews()
            }

            override fun saveCallResult(newsResponse: List<NewsResponse>) {
                val newsList = ArrayList<NewsEntity>()
                for (response in newsResponse) {
                    val news = NewsEntity(
                        response.title,
                        response.image,
                        response.url
                    )
                    newsList.add(news)
                }
                Log.d("savecall", newsList.toString())
                localDataSource.insertNews(newsList)
            }
        }.asLiveData()
    }*/
}