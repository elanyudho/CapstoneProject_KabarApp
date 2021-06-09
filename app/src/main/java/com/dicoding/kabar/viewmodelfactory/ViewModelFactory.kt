package com.dicoding.kabar.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.kabar.data.KabarRepository
import com.dicoding.kabar.dependencyinjection.Injection
import com.dicoding.kabar.ui.classify.ClassifyViewModel
import com.dicoding.kabar.ui.favorite.FavoriteViewModel
import com.dicoding.kabar.ui.news.NewsViewModel

class ViewModelFactory private constructor(private val kabarRepository: KabarRepository): ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
        }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                return NewsViewModel() as T
            }
            modelClass.isAssignableFrom(ClassifyViewModel::class.java) -> {
                return ClassifyViewModel() as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                return FavoriteViewModel(kabarRepository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

}



