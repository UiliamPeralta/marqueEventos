package com.dev.peralta.marqueeventos.data

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.dev.peralta.marqueeventos.TAG
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class EventPageKeyedDataSource(private val db: FirebaseFirestore) : PageKeyedDataSource<DocumentSnapshot, DocumentSnapshot>() {
    override fun loadInitial(
        params: LoadInitialParams<DocumentSnapshot>,
        callback: LoadInitialCallback<DocumentSnapshot, DocumentSnapshot>
    ) {
        val first: Query = db.collection("events").limit(params.requestedLoadSize.toLong())
        first.get().addOnSuccessListener {

            if (!it.isEmpty) {
                val lastVisible: DocumentSnapshot = it.documents.last()
                callback.onResult(it.documents, null, lastVisible)
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<DocumentSnapshot>,
        callback: LoadCallback<DocumentSnapshot, DocumentSnapshot>
    ) {
        val next: Query = db.collection("events")
            .startAfter(params.key)
            .limit(params.requestedLoadSize.toLong())

        next.get().addOnSuccessListener {
            if (!it.isEmpty) {
                val lastVisible: DocumentSnapshot = it.documents.last()
                callback.onResult(it.documents, lastVisible)
                Log.i(TAG, "${params.requestedLoadSize}")
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<DocumentSnapshot>,
        callback: LoadCallback<DocumentSnapshot, DocumentSnapshot>
    ) {
    }
}