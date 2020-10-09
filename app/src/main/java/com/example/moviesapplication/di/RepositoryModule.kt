package com.example.moviesapplication.di


import com.example.moviesapplication.repository.DataRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { DataRepository(moviesApi = get()) }
}