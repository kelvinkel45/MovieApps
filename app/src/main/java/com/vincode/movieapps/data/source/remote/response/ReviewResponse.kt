package com.vincode.movieapps.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @field:SerializedName("results")
    val results: List<ReviewResultResponse>,
)

data class ReviewResultResponse(

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("content")
    val content: String,

    @field:SerializedName("created_at")
    val createdAt:String
)
