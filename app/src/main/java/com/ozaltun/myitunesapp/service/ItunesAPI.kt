package com.ozaltun.myitunesapp.service

import com.ozaltun.myitunesapp.model.ResponseResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesAPI {

    @GET("/search")
    suspend fun getSearchResults(
        @Query("term") term: String,
        @Query("entity") entity: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<ResponseResult>

}