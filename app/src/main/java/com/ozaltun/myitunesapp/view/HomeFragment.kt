package com.ozaltun.myitunesapp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ozaltun.myitunesapp.R
import com.ozaltun.myitunesapp.adapter.HomeFragmentAdapter
import com.ozaltun.myitunesapp.databinding.FragmentHomeBinding
import com.ozaltun.myitunesapp.utils.Constant
import com.ozaltun.myitunesapp.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.flow.collectLatest

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var homeFragmentAdapter: HomeFragmentAdapter? = null
    private val viewModel: HomeFragmentViewModel by viewModels()
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homeFragment = this
        loadRecyclerView()
        moviesOnClick()
        getDataFromSharedPreferences()
        return binding.root
    }

    private fun search(term: String, entity: String) {
        if (viewModel.checkInternetConnection()) {
            lifecycleScope.launchWhenCreated {
                viewModel.refreshData(term, entity).collectLatest {
                    homeFragmentAdapter?.submitData(it)
                }
            }
        } else {
            Toast.makeText(context, getString(R.string.internet), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() { //Saved the category and search value when go to the detail page
        super.onDestroyView()
        sharedPreferences?.edit()?.putString(Constant.SHARED_PREF_CATEGORY_KEY, getGenre())?.apply()
        sharedPreferences?.edit()
            ?.putString(Constant.SHARED_PREF_SEARCHTERM_KEY, binding.textFieldInput.text.toString())
            ?.apply()
    }

    private fun getDataFromSharedPreferences() {
        sharedPreferences =
            activity?.getSharedPreferences("com.ozaltun.myitunesapp", Context.MODE_PRIVATE)
        binding.apply {
            textFieldInput.setText(
                sharedPreferences?.getString(
                    Constant.SHARED_PREF_SEARCHTERM_KEY,
                    ""
                )
            )

        }
    }

    private fun getGenre(): String {
        when (binding.toggleButton.checkedButtonId) {
            binding.moviesButton.id -> {
                return Constant.QUERY_MOVIES
            }
            binding.appsButton.id -> {
                return Constant.QUERY_APPS
            }
            binding.booksButton.id -> {
                return Constant.QUERY_BOOKS
            }
            binding.musicButton.id -> {
                return Constant.QUERY_MUSIC
            }
            else -> return Constant.QUERY_MOVIES
        }
    }

    fun booksOnClick() {

        binding.textFieldInput.apply {
            if (text.isNullOrEmpty()) {
                search(Constant.DEFAULT_SEARCH_TERM, Constant.QUERY_BOOKS)
            } else {
                search(binding.textFieldInput.text.toString(), Constant.QUERY_BOOKS)
            }
        }
    }

    fun musicOnClick() {
        binding.textFieldInput.apply {
            if (text.isNullOrEmpty()) {
                search(Constant.DEFAULT_SEARCH_TERM, Constant.QUERY_MUSIC)
            } else {
                search(binding.textFieldInput.text.toString(), Constant.QUERY_MUSIC)
            }
        }
    }

    fun appsOnClick() {
        binding.textFieldInput.apply {
            if (text.isNullOrEmpty()) {
                search(Constant.DEFAULT_SEARCH_TERM, Constant.QUERY_APPS)
            } else {
                search(binding.textFieldInput.text.toString(), Constant.QUERY_APPS)
            }
        }
    }

    fun moviesOnClick() {
        binding.textFieldInput.apply {
            if (text.isNullOrEmpty()) {
                search(Constant.DEFAULT_SEARCH_TERM, Constant.QUERY_MOVIES)
            } else {
                search(binding.textFieldInput.text.toString(), Constant.QUERY_MOVIES)
            }
        }
    }

    private fun loadRecyclerView() {
        homeFragmentAdapter = HomeFragmentAdapter()
        binding.recyclerView.adapter = homeFragmentAdapter
    }
}