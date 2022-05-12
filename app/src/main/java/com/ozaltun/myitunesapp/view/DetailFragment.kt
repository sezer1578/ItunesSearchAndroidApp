package com.ozaltun.myitunesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ozaltun.myitunesapp.R
import com.ozaltun.myitunesapp.databinding.FragmentDetaillBinding
import com.ozaltun.myitunesapp.model.Result
import com.ozaltun.myitunesapp.utils.Constant

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetaillBinding
    var result: Result? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detaill, container, false)
        binding.detailsFragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            result = DetailFragmentArgs.fromBundle(it).result
            binding.contentResult = result
            setViews()
        }

    }

    private fun setViews() { //According to home page , show query on the detail page
        when (result?.kind) {
            Constant.TYPE_SONG, Constant.TYPE_MUSIC_VIDEO -> {
                setMusicViews()
            }
            Constant.TYPE_MOVIE -> {
                setMovieViews()
            }
            Constant.TYPE_BOOK -> {
                setBooksViews()
            }
            Constant.TYPE_SOFTWARE -> {
                setSoftwareViews()
            }
        }

    }

    private fun setMusicViews() {
        binding.apply {
            nameDetail.setText(result?.trackName)
            genreDetail.setText(getString(R.string.collection) + " " + result?.collectionName)
            descriptionDetail.setText(getString(R.string.author) + " " + result?.artistName)
            itunesCountryDetail.setText(getString(R.string.country) + " " + result?.country)
        }
    }

    private fun setMovieViews() {
        binding.apply {
            nameDetail.setText(result?.trackName)
            genreDetail.setText(result?.primaryGenreName)
            descriptionDetail.setText(result?.longDescription)
            itunesCountryDetail.setText(getString(R.string.country) + " " + result?.country)
        }
    }

    private fun setBooksViews() {
        binding.apply {
            nameDetail.setText(result?.trackName)
            genreDetail.setText(getString(R.string.author) + " " + result?.artistName)
            val description: String? = null
            if (description == null) {
                itunesCountryDetail.setText(getString(R.string.noDesc))
            } else {
                itunesCountryDetail.setText(description)
            }

        }
    }

    private fun setSoftwareViews() {
        binding.apply {
            nameDetail.setText(result?.trackName)
            genreDetail.setText(result?.primaryGenreName)
            descriptionDetail.setText(result?.longDescription)
            itunesCountryDetail.setText("Country: " + result?.country)
        }
    }

}