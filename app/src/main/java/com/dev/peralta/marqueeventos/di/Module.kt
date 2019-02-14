package com.dev.peralta.marqueeventos.di

import androidx.paging.DataSource
import com.dev.peralta.marqueeventos.data.EventDataSourceFactory
import com.dev.peralta.marqueeventos.data.EventPageKeyedDataSource
import com.dev.peralta.marqueeventos.data.Repository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideExecutor(): Executor = Executors.newSingleThreadExecutor()

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideEventDataSourceFactory(fireStore: FirebaseFirestore) = EventDataSourceFactory(fireStore)

    @Provides
    fun provideEventDataSource(eventDataSourceFactory: EventDataSourceFactory): DataSource<DocumentSnapshot, DocumentSnapshot> =
        eventDataSourceFactory.create()

    @Singleton
    @Provides
    fun provideRepository(
        firestore: FirebaseFirestore,
        executor: Executor,
        eventDataSourceFactory: EventDataSourceFactory,
        data: DataSource<DocumentSnapshot, DocumentSnapshot>
        ): Repository =
        Repository(firestore, executor, eventDataSourceFactory, data)
}









