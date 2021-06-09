package com.dicoding.kabar.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.kabar.data.source.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)

abstract class KabarDatabase: RoomDatabase() {
    abstract fun kabarDao(): KabarDao

    companion object{
        @Volatile
        private var INSTANCE: KabarDatabase? = null

        fun getInstance(context: Context): KabarDatabase =
                INSTANCE ?: synchronized(this) {
                    Room.databaseBuilder(
                            context.applicationContext,
                            KabarDatabase::class.java,
                            "Kabar.db"
                    ).build().apply {
                        INSTANCE = this
                    }
                }
    }
}