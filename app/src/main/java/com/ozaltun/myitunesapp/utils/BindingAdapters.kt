package com.ozaltun.myitunesapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ozaltun.myitunesapp.model.Result
import java.text.SimpleDateFormat
import java.util.*

class BindingAdapters {
    companion object {


        @BindingAdapter("android:downloadImage")
        @JvmStatic
        fun downloadImage(view: ImageView, url: String) {
            view.downloadImage(url, myPlaceHolder(view.context))
        }

        @BindingAdapter("android:collectionName")
        @JvmStatic
        fun collectionName(view: TextView, result: Result) {
            if (!result.trackName.isNullOrEmpty()) {
                view.text = result.trackName
            } else if (!result.collectionName.isNullOrEmpty()) {
                view.text = result.collectionName
            }
        }

        @BindingAdapter("android:setPrice")
        @JvmStatic
        fun setPrice(view: TextView, result: Result) {
            if (result.collectionPrice.toString().isNullOrEmpty()) {
                if (result.price!! <= 0) {
                    view.text = "Free"
                } else {
                    view.text = result.price.toString() + "$"
                }
            }
        }

        @BindingAdapter("android:setReleaseDate")
        @JvmStatic
        fun setReleaseDate(view: TextView, date: String) {
            val dateParse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val text = dateFormat.format(dateParse.parse(date))
            view.text = "Release Date: " + text
        }
    }
}