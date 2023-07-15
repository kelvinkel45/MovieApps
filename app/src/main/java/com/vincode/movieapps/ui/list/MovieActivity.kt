package com.vincode.movieapps.ui.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vincode.movieapps.util.ViewModelFactory
import com.vincode.movieapps.databinding.ActivityMovieBinding
import com.vincode.movieapps.domain.model.Movie
import com.vincode.movieapps.ui.DataResource
import com.vincode.movieapps.ui.detail.DetailMovieActivity

class MovieActivity : AppCompatActivity() {

    private lateinit var viewModel : MovieViewModel
    private lateinit var binding : ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        startObserver()
        binding.swMain.setOnRefreshListener {
            viewModel.refreshData()
        }

    }

    private fun startObserver(){
        viewModel.getMovies().observe(this) { movies ->
            if (movies != null) {
                when (movies) {
                    is DataResource.Loading -> {
                        onLoading()
                    }

                    is DataResource.Error -> {
                        onError()
                    }

                    is DataResource.Success -> {
                        movies.data?.let { setAdapter(it) }
                        onSuccess()
                    }

                    is DataResource.Empty ->{
                        onEmpty()
                    }
                }
            }

        }
    }


    private fun setAdapter(data: List<Movie>) {
        binding.apply {
            val adapter = MovieAdapter(data){
                val intent = Intent(this@MovieActivity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.MOVIE_ID, it.id)
                startActivity(intent)
            }
            rvMovie.visibility = VISIBLE
            rvMovie.layoutManager = LinearLayoutManager(this@MovieActivity, LinearLayoutManager.HORIZONTAL, false)
            rvMovie.setHasFixedSize(true)
            rvMovie.adapter = adapter
        }
    }

    private fun onSuccess(){
        binding.apply {
            rvMovie.visibility = VISIBLE
            viewLoad.smMainLayout.visibility = GONE
            viewError.root.visibility = GONE
            swMain.isRefreshing = false
            viewEmpty.root.visibility = GONE
        }
    }

    private fun onLoading(){
        binding.apply {
            rvMovie.visibility = GONE
            viewLoad.smMainLayout.visibility = VISIBLE
            viewError.root.visibility = GONE
            swMain.isRefreshing = true
            viewEmpty.root.visibility = GONE
        }
    }

    private fun onError(){
        binding.apply {
            rvMovie.visibility = GONE
            viewLoad.smMainLayout.visibility = GONE
            viewError.root.visibility = VISIBLE
            swMain.isRefreshing = false
            viewEmpty.root.visibility = GONE
        }
    }

    private fun onEmpty(){
        binding.apply {
            rvMovie.visibility = GONE
            viewLoad.smMainLayout.visibility = GONE
            viewError.root.visibility = GONE
            swMain.isRefreshing = false
            viewEmpty.root.visibility = VISIBLE
        }
    }
}