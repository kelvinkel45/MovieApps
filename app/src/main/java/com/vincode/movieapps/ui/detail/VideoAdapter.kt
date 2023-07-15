package com.vincode.movieapps.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.vincode.movieapps.databinding.ItemVideoBinding
import com.vincode.movieapps.domain.model.Video

class VideoAdapter(
    private val list: List<Video>,
    private val lifecycle: Lifecycle
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder = 
        VideoViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false), lifecycle)

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) =
        holder.cueVideo(list[position].key)

    inner class VideoViewHolder(
        binding: ItemVideoBinding,
        lifecycle: Lifecycle
    ): RecyclerView.ViewHolder(binding.root){

        private var youTubePlayer: YouTubePlayer? = null
        private var currentVideoId: String? = null

        init{
            lifecycle.addObserver(binding.youtubeView)
            binding.youtubeView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    // store youtube player reference for later
                    this@VideoViewHolder.youTubePlayer = youTubePlayer
                    // cue the video if it's available
                    currentVideoId?.let { youTubePlayer.cueVideo(it, 0f) }
                }

            })
            //binding.youtubeView.toggleFullScreen()
        }

        fun cueVideo(videoId: String) {
            currentVideoId = videoId
            // cue the video if the youtube player is available
            youTubePlayer?.cueVideo(videoId, 0f)
        }

    }
}

