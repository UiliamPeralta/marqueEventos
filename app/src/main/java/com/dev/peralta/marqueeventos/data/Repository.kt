package com.dev.peralta.marqueeventos.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dev.peralta.marqueeventos.model.Event
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executor
const val PAGE_SIZE = 30

class Repository(
    private val fireStore: FirebaseFirestore,
    private val executor: Executor,
    private val eventDataSourceFactory: EventDataSourceFactory,
    private val eventDataSource: DataSource<DocumentSnapshot, DocumentSnapshot>) {

    val documentSnapshotList: LiveData<PagedList<DocumentSnapshot>>
        get() = LivePagedListBuilder(eventDataSourceFactory, PAGE_SIZE)
            .setFetchExecutor(executor)
            .build()


    fun insertEvent(event: Event) {
        val docReference = fireStore.collection("events").document()
        docReference.set(event)
            .addOnSuccessListener {

            }
    }

    fun deleteEvent(event: Event) {
        val docReference = fireStore.collection("events").document()
        docReference.delete()
            .addOnSuccessListener {

            }
    }

    fun invalidateDataSource() = eventDataSource.invalidate()
}

//    private var listenerRegistration: ListenerRegistration? = null

//    private fun getEventos() {
//        db.collection("events")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//
//                    task.result?.let { query ->
//                        for (doc in query) {
//
////                            Log.i(TAG, "${doc.getTimestamp("time")}")
////                            Log.i(TAG, "${doc.getGeoPoint("local")}")
//                              Log.i(TAG, "${doc.get("cat")}")
//                        }
//                    }
//                }
//            }
//    }
//
//    private fun receberAtualTempoReal() {
//       listenerRegistration = db.collection("events")
//            .addSnapshotListener { querySnapshot, _ /*fireBaseFireStoreException*/ ->
//                for (doc in querySnapshot!!) {
//                    Log.i(TAG, "${doc.data}")
//                }
//            }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        listenerRegistration?.remove()
//    }

