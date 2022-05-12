package com.ozaltun.myitunesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.ozaltun.myitunesapp.R
import com.ozaltun.myitunesapp.databinding.FragmentDetailBinding
import com.ozaltun.myitunesapp.model.Result
import com.ozaltun.myitunesapp.utils.Constant
import org.jsoup.Jsoup

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    var result: Result? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
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

    private fun setViews() { //Ana sayfada seçilen item ın türüne göre detay sayfasının düzenlenmesi
        when (result?.kind) {
            Constant.TYPE_SONG, Constant.TYPE_MUSICVIDEO -> {
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
            firstText.setText(result?.trackName)
            secondText.setText("Collection: " + result?.collectionName)
            thirdText.setText("Artist: " + result?.artistName)
            fourthText.setText("Country: " + result?.country)
            line5.visibility = View.VISIBLE
        }
    }

    private fun setMovieViews() {
        binding.apply {
            firstText.setText(result?.trackName)
            secondText.setText(result?.primaryGenreName)
            thirdText.setText(result?.longDescription)
            thirdText.textSize = 14F
            fourthText.setText("Country: " + result?.country)
            line.visibility = View.VISIBLE
            line2.visibility = View.VISIBLE
        }
    }

    private fun setBooksViews() {
        binding.apply {
            firstText.setText(result?.trackName)
            secondText.setText("Author: " + result?.artistName)
           /* var genres = ""
            for (genre in result!!.genres) {
                genres += genre + ","
            }
            genres = genres.substring(0, genres.length - 1)
            thirdText.setText(genres)
            */

            val description = Jsoup.parse(result!!.longDescription).text()
            fourthText.setText(description)
            fourthText.textSize = 14F

            line2.visibility = View.VISIBLE
            line3.visibility = View.VISIBLE
            line5.visibility = View.VISIBLE
        }
    }

    private fun setSoftwareViews() {
        binding.apply {
            firstText.setText(result?.trackName)
            secondText.setText(result?.primaryGenreName)
            thirdText.setText(result?.longDescription)
            thirdText.textSize = 14F
            line.visibility = View.VISIBLE
            line2.visibility = View.VISIBLE
            fourthText.setText("Seller: " + result?.trackName)
        }
    }

    fun backNavListener(view: View) {
        val action = DetailFragmentDirections.actionDetailFragmentToHomeFragment()
        view.findNavController().navigate(action)

    }
}