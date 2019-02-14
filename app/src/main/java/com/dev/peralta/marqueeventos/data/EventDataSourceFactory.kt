package com.dev.peralta.marqueeventos.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class EventDataSourceFactory(private val db: FirebaseFirestore) : DataSource.Factory<DocumentSnapshot, DocumentSnapshot>() {
    private val sourceLiveData = MutableLiveData<EventPageKeyedDataSource>()
    override fun create(): DataSource<DocumentSnapshot, DocumentSnapshot> {
        val source = EventPageKeyedDataSource(db)
        sourceLiveData.postValue(source)
        return source
    }

}