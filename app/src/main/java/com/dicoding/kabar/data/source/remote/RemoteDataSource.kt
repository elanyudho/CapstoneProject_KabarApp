package com.dicoding.kabar.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.kabar.data.source.remote.response.NewsResponse
import com.dicoding.kabar.utils.JsonHelper
import com.loopj.android.http.AsyncHttpClient.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    companion object {

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

    //fun getAllNews(): LiveData<ApiResponse<List<NewsResponse>>>{

    //}

}