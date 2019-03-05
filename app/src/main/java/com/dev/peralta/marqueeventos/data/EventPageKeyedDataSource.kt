package com.dev.peralta.marqueeventos.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class EventPageKeyedDataSource(private val db: FirebaseFirestore, private val cat: String) : PageKeyedDataSource<DocumentSnapshot, DocumentSnapshot>() {

    private val progressLiveData = MutableLiveData<Boolean>()
    private val progressLoadInitial = MutableLiveData<Boolean>()
    private val isEmptyList = MutableLiveData<Boolean>()

    val getProgressLiveData: LiveData<Boolean>
        get() = progressLiveData

    val getProgressLoadInitial: LiveData<Boolean>
        get() = progressLoadInitial

    val getIsEmptyList: LiveData<Boolean>
        get() = isEmptyList

    override fun loadInitial(
        params: LoadInitialParams<DocumentSnapshot>,
        callback: LoadInitialCallback<DocumentSnapshot, DocumentSnapshot>
    ) {
        progressLoadInitial.postValue(true)
        val first: Query = if (cat.isEmpty()) {
            db.collection("events")
                .orderBy("time")
                .limit(params.requestedLoadSize.toLong())

        } else {
            db.collection("events")
                .whereEqualTo("cat", cat)
                .limit(params.requestedLoadSize.toLong())
        }

        first.get().addOnSuccessListener {
            progressLoadInitial.postValue(false)
            if (!it.isEmpty) {
                val lastVisible: DocumentSnapshot = it.documents.last()
                callback.onResult(it.documents, null, lastVisible)
                isEmptyList.postValue(false)
            } else isEmptyList.postValue(true)

        }.addOnFailureListener {
            progressLoadInitial.postValue(false)
        }
    }

    override fun loadAfter(
        params: LoadParams<DocumentSnapshot>,
        callback: LoadCallback<DocumentSnapshot, DocumentSnapshot>
    ) {
        progressLiveData.postValue(true)
        val next: Query = if (cat.isEmpty()) {
            db.collection("events")
                .orderBy("time")
                .startAfter(params.key)
                .limit(params.requestedLoadSize.toLong())

        } else {
            db.collection("events")
                .whereEqualTo("cat", cat)
                .startAfter(params.key)
                .limit(params.requestedLoadSize.toLong())
        }

        next.get().addOnSuccessListener {
            if (!it.isEmpty) {
                val lastVisible: DocumentSnapshot = it.documents.last()
                callback.onResult(it.documents, lastVisible)
            }
            progressLiveData.postValue(false)
        }.addOnFailureListener {
            progressLiveData.postValue(false)
        }
    }


    override fun loadBefore(
        params: LoadParams<DocumentSnapshot>,
        callback: LoadCallback<DocumentSnapshot, DocumentSnapshot>
    ) {
    }
}