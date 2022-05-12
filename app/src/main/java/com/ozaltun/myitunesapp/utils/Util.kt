package com.ozaltun.myitunesapp.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ozaltun.myitunesapp.R
import com.ozaltun.myitunesapp.model.Result
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.downloadImage(url: String, placeholder: CircularProgressDrawable) {

    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)

    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun myPlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadImage")
fun downloadImage(view: ImageView, url: String) {
    view.downloadImage(url, myPlaceHolder(view.context))
}

@BindingAdapter("android:collectionName")
fun collectionName(view: TextView, result: Result) {
    if (!result.trackName.isNullOrEmpty()) {
        view.text = result.trackName
    } else if (!result.collectionName.isNullOrEmpty()) {
        view.text = result.collectionName
    }
}

@BindingAdapter("android:setPrice")
fun setPrice(view: TextView, result: Result) {
    if (result.collectionPrice.toString().isNullOrEmpty()) {
        if (result.price!! <= 0) {
            view.text = view.context.getString(R.string.free)
        } else {
            view.text = result.price.toString() + " " + view.context.getString(R.string.dolar)
        }
    }
}

@BindingAdapter("android:setReleaseDate")
fun setReleaseDate(view: TextView, date: String) {
    val dateParse = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val text = dateFormat.format(dateParse.parse(date))
    view.text = view.context.getString(R.string.releaseDate) + " " + text
}