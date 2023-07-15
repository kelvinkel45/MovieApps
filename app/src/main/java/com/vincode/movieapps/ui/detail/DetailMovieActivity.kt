package com.vincode.movieapps.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.vincode.movieapps.R
import com.vincode.movieapps.databinding.ActivityDetailMovieBinding
import com.vincode.movieapps.domain.model.DetailMovie
import com.vincode.movieapps.domain.model.ReviewMovie
import com.vincode.movieapps.domain.model.Video
import com.vincode.movieapps.ui.DataResource
import com.vincode.movieapps.util.ViewModelFactory

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailMovieBinding
    private lateinit var viewModel: DetailMovieViewModel

    companion object{
        const val MOVIE_ID = "movie_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        val id = intent.getStringExtra(MOVIE_ID).toString()
        startObserverData(id)
        startObserverReview(id)
        startObserverVideo(id)

        binding.swMain.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun startObserverData(movieId: String) {
        viewModel.getDetailMovie(movieId).observe(this) { movie ->
            if (movie != null) {
                when (movie) {
                    is DataResource.Success -> {
                        onSuccess()
                        movie.data?.let { setDetailDataMovie(it) }
                    }

                    is DataResource.Loading -> onLoading()


                    is DataResource.Error -> onError()


                    is DataResource.Empty -> onEmpty()
                }
            }

        }
    }

    private fun startObserverReview(movieId: String){
        viewModel.getAllReviewMovie(movieId).observe(this) { dataReview ->
            if (dataReview != null) {
                when (dataReview) {
                    is DataResource.Success -> {
                        dataReview.data?.let { setAdapterReview(it) }
                    }
                    else -> binding.rvReview.visibility = GONE
                }
            }

        }
    }

    private fun startObserverVideo(movieId: String){
        viewModel.getVideoMovies(movieId).observe(this){dataVideo->
            if (dataVideo != null){
                when(dataVideo){
                    is DataResource.Success -> {
                        dataVideo.data?.let { setAdapterVideo(it) }
                    }
                    else -> binding.rvVideos.visibility = GONE
                }
            }

        }

    }

    private fun onSuccess(){
        binding.apply {
            swMain.isRefreshing = false
            progressBar.visibility = GONE
            viewContent.visibility = VISIBLE
            imgDetailBanner.visibility = VISIBLE
            viewError.root.visibility = GONE
            viewEmpty.root.visibility = GONE
        }
    }

    private fun onLoading(){
        binding.apply {
            swMain.isRefreshing = false
            progressBar.visibility = VISIBLE
            viewContent.visibility = GONE
            imgDetailBanner.visibility = GONE
            viewError.root.visibility = GONE
            viewEmpty.root.visibility = GONE
        }
    }

    private fun onError(){
        binding.apply {
            swMain.isRefreshing = false
            progressBar.visibility = GONE
            viewContent.visibility = GONE
            imgDetailBanner.visibility = GONE
            viewError.root.visibility = VISIBLE
            viewEmpty.root.visibility = GONE

        }
    }

    private fun onEmpty(){
        binding.apply {
            swMain.isRefreshing = false
            progressBar.visibility = GONE
            viewContent.visibility = GONE
            imgDetailBanner.visibility = GONE
            viewError.root.visibility = GONE
            viewEmpty.root.visibility = VISIBLE
        }
    }

    private fun setDetailDataMovie(data: DetailMovie) {
        binding.apply {
            val runtime = "${data.runtime} Minutes"
            tvTitleMovieDetail.text = data.title
            tvRatingMovieDetail.text = data.voteAverage.toString()
            tvRuntimeMovieDetail.text = runtime
            tvOverviewMovieDetail.text = data.overview

            setImage(imgDetailBanner, data.backdropPath)
            setImage(imgPosterDetail, data.posterPath)
            setChipGroup(cgGenre, data.genres)

        }
    }

    private fun setImage(imageView: ImageView, path :String){
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/$path")
            .into(imageView)
    }

    private fun setChipGroup(chipGroup: ChipGroup, list: List<String>) {
        chipGroup.removeAllViews()
        for (text in list) {
            val chip =
                layoutInflater.inflate(R.layout.item_genre, chipGroup, false) as Chip
            chip.text = text
            chipGroup.addView(chip)
        }
    }

    private fun setAdapterReview(data: List<ReviewMovie>){
        binding.apply {
            val adapter = ReviewAdapter(data)
            rvReview.visibility = VISIBLE
            rvReview.layoutManager = LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
            rvReview.setHasFixedSize(true)
            rvReview.adapter = adapter
        }
    }

    private fun setAdapterVideo(videos: List<Video>) {
        binding.apply {
            val adapter = VideoAdapter(videos, lifecycle)
            rvVideos.visibility = VISIBLE
            rvVideos.layoutManager = LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
            rvVideos.setHasFixedSize(true)
            rvVideos.adapter = adapter
        }
    }
}