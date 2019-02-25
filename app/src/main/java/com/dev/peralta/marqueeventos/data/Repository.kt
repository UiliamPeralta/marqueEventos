package com.dev.peralta.marqueeventos.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dev.peralta.marqueeventos.model.Event
import com.dev.peralta.marqueeventos.model.MyEvent
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executor
const val PAGE_SIZE = 30

class Repository(
    private val fireStore: FirebaseFirestore,
    private val executor: Executor,
    private val eventDataSourceFactory: EventDataSourceFactory) {

    private var eventDataSource: DataSource<DocumentSnapshot, DocumentSnapshot>? = null

    val documentSnapshotList: LiveData<PagedList<DocumentSnapshot>>
        get() = LivePagedListBuilder(eventDataSourceFactory, PAGE_SIZE)
            .setFetchExecutor(executor)
            .build()

    fun updateList(cat: String = ""): LiveData<PagedList<DocumentSnapshot>>{
        eventDataSourceFactory.cat = cat
        eventDataSource = eventDataSourceFactory.create()
        return  LivePagedListBuilder(eventDataSourceFactory, PAGE_SIZE)
            .setFetchExecutor(executor)
            .build()
    }

//    val progress = Transformations.switchMap(eventDataSourceFactory.sourceLiveData) {
//        liveData em dataSource indicando progresso
//    }

    fun invalidateDataSource() = eventDataSource?.invalidate()


    fun insertEvent(event: Event) {
        val docReference = fireStore.collection("events").document()
        docReference.set(event)
            .addOnSuccessListener {
                fireStore.collection("myEvents").add(MyEvent(docReference))
            }
    }

    fun DocumentSnapshot.deleteMyEvent() {
        val docReferenceMyEvent = fireStore.collection("myEvents").document(id)
        docReferenceMyEvent.get().addOnCompleteListener {
            it.result?.getDocumentReference("documentReference")?.delete()?.addOnSuccessListener {
                docReferenceMyEvent.delete()
            }
        }
    }


    private fun DocumentSnapshot.updateMyEvent(myEvent: MyEvent) {
        val docReferenceMyEvent = fireStore.collection("myEvents").document(id)
        docReferenceMyEvent.get().addOnCompleteListener {
            it.result?.getDocumentReference("documentReference")?.set(myEvent)
        }
    }
}

