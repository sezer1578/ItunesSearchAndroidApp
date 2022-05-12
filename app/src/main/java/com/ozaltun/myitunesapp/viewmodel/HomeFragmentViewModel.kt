package com.ozaltun.myitunesapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ozaltun.myitunesapp.model.Result
import com.ozaltun.myitunesapp.service.ItunesAPI
import com.ozaltun.myitunesapp.service.ItunesAPIService
import com.ozaltun.myitunesapp.service.ResultPagingSource
import com.ozaltun.myitunesapp.utils.Constant
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {

    val retrofit: Retrofit = ItunesAPIService.createInstance()
    val service: ItunesAPI = retrofit.create(ItunesAPI::class.java)

    fun refreshData(term: String, entity: String): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = Constant.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { ResultPagingSource(service, term, entity) })
            .flow.cachedIn(viewModelScope)
    }

    fun checkInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

            else -> false
        }
    }
}