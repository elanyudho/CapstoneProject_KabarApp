package com.dicoding.kabar.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.kabar.data.source.local.entity.NewsEntity
import com.dicoding.moviecatalogue.valueobject.Resource

interface KabarDataSource {

    //fun getNews(): LiveData<Resource<PagedList<NewsEntity>>>
}