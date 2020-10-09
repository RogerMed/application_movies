package com.example.moviesapplication.di


import com.example.moviesapplication.ViewModel.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel( get()) }
}