package com.robert.movieapp

import android.app.Application
import com.robert.movieapp.di.databaseModule
import com.robert.movieapp.di.networkModule
import com.robert.movieapp.di.repositoryModule
import com.robert.movieapp.di.useCaseModule
import com.robert.movieapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
