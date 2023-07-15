package com.vincode.movieapps.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vincode.movieapps.databinding.ItemReviewBinding
import com.vincode.movieapps.domain.model.ReviewMovie

class ReviewAdapter(
    private val list: List<ReviewMovie>
) : RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder =
        ReviewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size
    inner class ReviewHolder(
        private val binding : ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root){


        fun bind(reviewMovie: ReviewMovie) {
            binding.apply {
                tvReviewAuthor.text = reviewMovie.author
                tvContentReview.text = reviewMovie.content
            }
        }

    }
}