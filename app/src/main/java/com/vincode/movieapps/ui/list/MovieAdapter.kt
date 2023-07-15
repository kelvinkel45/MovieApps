package com.vincode.movieapps.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vincode.movieapps.databinding.ItemMovieBinding
import com.vincode.movieapps.domain.model.Movie
import com.vincode.movieapps.ui.list.MovieAdapter.MovieViewHolder

class MovieAdapter(
    private val list: List<Movie>,
    private val onClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =  ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size


    inner class MovieViewHolder (
        private val binding : ItemMovieBinding,
        val onClick: (Movie) -> Unit): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick(list[adapterPosition])
            }
        }

        fun bind(movie : Movie){
            binding.tvTitleMovie.text = movie.title
            Glide.with(binding.imgPoster)
                .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
                .centerCrop()
                .into(binding.imgPoster)
        }

    }
}