package com.ozaltun.myitunesapp.service

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ozaltun.myitunesapp.model.Result
import com.ozaltun.myitunesapp.utils.Constant

class ResultPagingSource(val service: ItunesAPI, val term: String, val entity: String) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: Constant.INITIAL_LOAD_SIZE
        val offset = if (params.key != null) ((position - 1) * Constant.NETWORK_PAGE_SIZE) + 1 else Constant.INITIAL_LOAD_SIZE
        return try {
            val response = service.getSearchResults(term = term,entity = entity,offset = offset-1, limit = params.loadSize).body()!!.results
            Log.d("LOAD SIZE",params.loadSize.toString())
            Log.d("KEY",params.key.toString())
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                position + (params.loadSize / Constant.NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}