package com.ozaltun.myitunesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozaltun.myitunesapp.R
import com.ozaltun.myitunesapp.databinding.RecyclerRowBinding
import com.ozaltun.myitunesapp.model.Result
import com.ozaltun.myitunesapp.view.HomeFragmentDirections

class HomeFragmentAdapter :
    PagingDataAdapter<Result, HomeFragmentAdapter.ViewHolder>(DiffUtilCallBack()) {
    class ViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun myBind(result: Result) {
            binding.apply {
                rowData = result
                executePendingBindings()
                itemClickListener(result)

            }
        }

        fun itemClickListener(result: Result) {
            binding.itemCardview.apply {
                setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(result)
                    findNavController().navigate(action)
                }
            }
        }

    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Result>() {

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.trackId.equals(newItem.trackId)
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.trackId.equals(newItem.trackId)
        }

    }

    override fun onBindViewHolder(holder: HomeFragmentAdapter.ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.myBind(it)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeFragmentAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RecyclerRowBinding>(
            inflater,
            R.layout.recycler_row,
            parent,
            false
        )
        return ViewHolder(view)
    }
}