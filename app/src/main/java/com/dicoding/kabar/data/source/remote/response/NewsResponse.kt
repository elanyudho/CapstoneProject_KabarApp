package com.dicoding.kabar.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(
    var title: String,
    var image: String,
    var url: String
) : Parcelable