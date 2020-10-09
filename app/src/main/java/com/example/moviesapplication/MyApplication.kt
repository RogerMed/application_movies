package com.example.moviesapplication

import android.app.Application
import com.example.moviesapplication.di.appModule
import com.example.moviesapplication.di.repositoryModule
import com.example.moviesapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


/**
 * classe onde sera inicializado koin
 */

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(repositoryModule, viewModelModule, appModule))
        }
    }
}
