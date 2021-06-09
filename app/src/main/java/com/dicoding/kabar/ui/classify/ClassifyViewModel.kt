package com.dicoding.kabar.ui.classify

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.dicoding.kabar.utils.ApiKey
import com.dicoding.kabar.utils.ApiService
import com.loopj.android.http.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.AccessController.getContext


class ClassifyViewModel (): ViewModel() {

    private var image : Bitmap? = null

    fun setImg(bitmap: Bitmap?){
        Log.d("URIVIEWMODEL", bitmap.toString())
        this.image = bitmap
        Log.d("URIVIEWMODELSET", this.image.toString())

    }


    fun getImg(): Bitmap?{
        Log.d("URIVIEWMODELGET", this.image.toString())
        return this.image
    }

    fun getFileName(uri: Uri?): String {
        try {
            val path = uri?.getLastPathSegment()
            return path?.substring(path.lastIndexOf("/") + 1) ?: "unknown"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "unknown"
    }

}