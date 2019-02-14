package com.dev.peralta.marqueeventos

import android.app.Application
import com.dev.peralta.marqueeventos.di.AppComponent
import com.dev.peralta.marqueeventos.di.DaggerAppComponent
import com.dev.peralta.marqueeventos.di.EventViewModelComponent

class App: Application() {
    private lateinit var appComponent: AppComponent
    private var eventViewModelComponent: EventViewModelComponent? = null

    companion object {
        private lateinit var INSTANCE: App
        fun getInstance() = INSTANCE
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appComponent = DaggerAppComponent.create()
    }

    fun getEventViewModelComponent(): EventViewModelComponent {
        if (eventViewModelComponent == null) {
            eventViewModelComponent = appComponent
                .eventViewModelComponentBuilder()
                .build()
        }

        return eventViewModelComponent!!
    }

}