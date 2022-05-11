package com.ozaltun.myitunesapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItunesAPIService {

    companion object {
        fun createInstance(baseUrl: String): Retrofit {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()

            return retrofit
        }
    }


}