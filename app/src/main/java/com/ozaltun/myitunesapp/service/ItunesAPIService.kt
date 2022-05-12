package com.ozaltun.myitunesapp.service

import com.ozaltun.myitunesapp.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItunesAPIService {

    companion object {
        fun createInstance(): Retrofit {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build()

            return retrofit
        }
    }


}