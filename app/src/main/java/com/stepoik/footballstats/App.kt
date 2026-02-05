package com.stepoik.footballstats

import android.app.Application
import android.content.Context
import com.stepoik.footballstats.di.rootModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            val appModule = module {
                single<Context> { this@App }
            }
            modules(rootModule, appModule)
        }
    }
}