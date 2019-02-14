package com.dev.peralta.marqueeventos.di

import com.dev.peralta.marqueeventos.ui.EventViewModel
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun eventViewModelComponentBuilder(): EventViewModelComponent.Builder
}

@EventViewModelScope
@Subcomponent(modules = [EventViewModelModule::class])
interface EventViewModelComponent {
    @Subcomponent.Builder
    interface Builder {
        fun eventViewModelModule(eventViewModelModule: EventViewModelModule): Builder
        fun build(): EventViewModelComponent
    }

    fun inject(eventViewModel: EventViewModel)
}