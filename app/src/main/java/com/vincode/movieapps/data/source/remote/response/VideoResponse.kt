package com.vincode.movieapps.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @field:SerializedName("results")
    val results:List<VideoResultResponse>
)

data class VideoResultResponse(
    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("key")
    val key: String
)