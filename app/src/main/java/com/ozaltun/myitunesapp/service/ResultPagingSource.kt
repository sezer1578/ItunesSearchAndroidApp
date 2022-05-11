package com.ozaltun.myitunesapp.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ozaltun.myitunesapp.model.Result
import com.ozaltun.myitunesapp.utils.Constant

class ResultPagingSource(val service: ItunesAPI, val term: String, val entity: String) :
    PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {

        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: Constant.LOAD_SIZE
        val offset =
            if (params.key != null) ((position - 1) * Constant.PAGE_SIZE) + 1 else Constant.LOAD_SIZE
        return try {
            val response = service.getSearchResults(
                term = term,
                entity = entity,
                offset = offset-1,
                limit = params.loadSize
            ).body()!!.results
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                position + (params.loadSize / Constant.PAGE_SIZE)
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