package com.dev.peralta.marqueeventos.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dev.peralta.marqueeventos.TAG
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class EventDataSourceFactory(private val db: FirebaseFirestore) : DataSource.Factory<DocumentSnapshot, DocumentSnapshot>() {
    var cat = ""
    val sourceLiveData = MutableLiveData<EventPageKeyedDataSource>()
    override fun create(): DataSource<DocumentSnapshot, DocumentSnapshot> {
        val source = EventPageKeyedDataSource(db, cat)
        Log.i(TAG, "EventDataSourceFactory")
        sourceLiveData.postValue(source)
        return source
    }

}