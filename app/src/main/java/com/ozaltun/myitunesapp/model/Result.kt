package com.ozaltun.myitunesapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    @SerializedName("artworkUrl100")
    val artworkUrl100: String?,
    @SerializedName("collectionName")
    val collectionName: String?,
    @SerializedName("collectionPrice")
    val collectionPrice: Double?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("releaseDate")
    val releaseDate: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("longDescription")
    val longDescription: String?,
    @SerializedName("trackName")
    val trackName: String?,
    @SerializedName("artistName")
    val artistName: String?,
    @SerializedName("primaryGenreName")
    val primaryGenreName: String?,
   // @SerializedName("sellerName")
   // val sellerName: String,
   // @SerializedName("description")
   // val description: String,
   // @SerializedName("genres")
    //val genres: List<String>,
    @SerializedName("trackId")
    val trackId: String
) : Parcelable
