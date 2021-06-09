package com.dicoding.kabar.data.source.local

import androidx.paging.DataSource
import com.dicoding.kabar.data.source.local.entity.NewsEntity
import com.dicoding.kabar.data.source.local.room.KabarDao

class LocalDataSource private constructor(private val mKabarDao: KabarDao){

    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(kabarDao: KabarDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(kabarDao)
    }

    fun getNews(): DataSource.Factory<Int, NewsEntity> = mKabarDao.getNews()

    fun insertNews(news: List<NewsEntity>) = mKabarDao.insertNews(news)
}