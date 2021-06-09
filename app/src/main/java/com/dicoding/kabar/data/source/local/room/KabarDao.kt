package com.dicoding.kabar.data.source.local.room

import androidx.room.*
import androidx.paging.DataSource
import com.dicoding.kabar.data.source.local.entity.NewsEntity

@Dao
interface KabarDao {

    @Query("SELECT * FROM newsentities")
    fun getNews(): DataSource.Factory<Int, NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: List<NewsEntity>)

}