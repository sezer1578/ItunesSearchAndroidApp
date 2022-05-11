package com.ozaltun.myitunesapp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ozaltun.myitunesapp.R
import com.ozaltun.myitunesapp.adapter.HomeFragmentAdapter
import com.ozaltun.myitunesapp.databinding.FragmentHomeBinding
import com.ozaltun.myitunesapp.utils.Constant
import com.ozaltun.myitunesapp.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.flow.collectLatest

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeFragmentAdapter
    private val viewModel: HomeFragmentViewModel by viewModels()
    var sharedPreferences: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homeFragment = this
        loadRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSearch()

    }

    fun getPreferences() {
        sharedPreferences =
            activity?.getSharedPreferences("com.ozaltun.myitunesapp", Context.MODE_PRIVATE)
        binding.apply {
            textFieldInput.setText(
                sharedPreferences?.getString(
                    Constant.SHARED_PREF_SEARCHTERM_KEY,
                    ""
                )
            )
            toggleButton.check(
                getSelectedGenre(
                    sharedPreferences?.getString(
                        Constant.SHARED_PREF_CATEGORY_KEY,
                        "movie"
                    )!!
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sharedPreferences?.edit()?.putString(Constant.SHARED_PREF_CATEGORY_KEY, getSelectedType())
            ?.apply()
        sharedPreferences?.edit()
            ?.putString(Constant.SHARED_PREF_SEARCHTERM_KEY, binding.textFieldInput.text.toString())
            ?.apply()
    }

    private fun search(term: String, entity: String) {
        if (viewModel.checkInternetConnection()) {
            lifecycleScope.launchWhenCreated {
                viewModel.refreshData(term, entity).collectLatest {
                    adapter?.submitData(it)
                }
            }
        } else {
            Toast.makeText(context, "Check internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSearch() {
        binding.textFieldInput.apply {
            if (text.isNullOrEmpty()) {
                search(Constant.DEFAULT_SEARCH_TERM, getSelectedType())
            } else {
                search(text.toString(), getSelectedType())
            }
        }
        binding.textFieldInput.doOnTextChanged { text, start, before, count ->
            if (text?.length!! > 2) {
                search(binding.textFieldInput.text.toString(), getSelectedType())
            } else {
                search(Constant.DEFAULT_SEARCH_TERM, getSelectedType())
            }
        }
    }

    fun getSelectedType(): String {
        when (binding.toggleButton.checkedButtonId) {
            binding.MoviesButton.id -> {
                return Constant.QUERY_MOVIES
            }
            binding.AppsButton.id -> {
                return Constant.QUERY_APPS
            }
            binding.BooksButton.id -> {
                return Constant.QUERY_BOOKS
            }
            binding.MusicsButton.id -> {
                return Constant.QUERY_MUSIC
            }
            else -> return Constant.QUERY_MUSIC
        }
    }

    fun getSelectedGenre(genre: String): Int {
        when (genre) {
            Constant.QUERY_MOVIES -> {
                return binding.MoviesButton.id
            }
            Constant.QUERY_APPS -> {
                return binding.AppsButton.id
            }
            Constant.QUERY_BOOKS -> {
                return binding.BooksButton.id
            }
            Constant.QUERY_MUSIC -> {
                return binding.MusicsButton.id
            }
            else -> return binding.MoviesButton.id
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
        adapter = HomeFragmentAdapter()
        binding.recyclerView.adapter = adapter
    }


}