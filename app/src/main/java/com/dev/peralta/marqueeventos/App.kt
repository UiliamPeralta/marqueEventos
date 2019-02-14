package com.dev.peralta.marqueeventos

import android.app.Application
import com.dev.peralta.marqueeventos.di.AppComponent
import com.dev.peralta.marqueeventos.di.DaggerAppComponent

class App: Application() {
    private lateinit var appComponent: AppComponent


    companion object {
        private lateinit var INSTANCE: App
        fun getInstance() = INSTANCE
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appComponent = DaggerAppComponent.create()
    }

}