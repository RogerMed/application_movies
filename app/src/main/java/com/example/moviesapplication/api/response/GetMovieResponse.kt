package com.example.moviesapplication.api.response

import com.google.gson.annotations.SerializedName

class GetMovieResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
)