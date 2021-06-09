package com.dicoding.kabar.dependencyinjection

import android.content.Context
import com.dicoding.kabar.data.KabarRepository
import com.dicoding.kabar.data.source.local.LocalDataSource
import com.dicoding.kabar.data.source.local.room.KabarDatabase
import com.dicoding.kabar.data.source.remote.RemoteDataSource
import com.dicoding.kabar.utils.AppExecutors
import com.dicoding.kabar.utils.JsonHelper

object Injection {

    fun provideRepository(context: Context): KabarRepository {

        val database = KabarDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper())

        val localDataSource = LocalDataSource.getInstance(database.kabarDao())

        val appExecutors = AppExecutors()

        return  KabarRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}