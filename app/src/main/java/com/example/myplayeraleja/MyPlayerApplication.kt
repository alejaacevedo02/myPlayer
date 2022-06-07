package com.example.myplayeraleja

import android.app.Application
import com.example.myplayeraleja.data.MediaProvider
import com.example.myplayeraleja.data.MediaProviderImpl
import com.example.myplayeraleja.ui.detail.DetailActivity
import com.example.myplayeraleja.ui.detail.DetailViewModel
import com.example.myplayeraleja.ui.main.MainActivity
import com.example.myplayeraleja.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<MediaProvider> { MediaProviderImpl }
    single(named("ioDispatcher")) { Dispatchers.IO }
    viewModel { MainViewModel(get(), get(named("ioDispatcher")))}
        viewModel { DetailViewModel(get(), get(named("ioDispatcher"))) }
}


class MyPlayerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyPlayerApplication)
            modules(appModule)
        }
    }
}