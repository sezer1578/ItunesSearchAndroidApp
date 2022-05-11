package com.ozaltun.myitunesapp.model

import com.google.gson.annotations.SerializedName

data class ResponseResult(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val results: ArrayList<Result>
) {
}