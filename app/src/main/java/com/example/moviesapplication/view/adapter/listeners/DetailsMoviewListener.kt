package com.example.moviesapplication.view.adapter.listeners

import android.widget.ImageView
import com.example.moviesapplication.api.response.Movie


interface DetailsMovieListener {

    fun detailsMovieOnClick(movie: Movie, position: Int,imageView: ImageView)
}