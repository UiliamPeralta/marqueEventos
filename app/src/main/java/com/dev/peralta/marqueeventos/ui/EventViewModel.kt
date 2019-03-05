package com.dev.peralta.marqueeventos.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dev.peralta.marqueeventos.App
import com.dev.peralta.marqueeventos.data.Repository
import com.dev.peralta.marqueeventos.model.Event
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint
import java.util.*

import javax.inject.Inject

class EventViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    private val refreshLiveData = MutableLiveData<String>()

    val progressLiveData: LiveData<Boolean>
    val progressLoadInitial:  LiveData<Boolean>
    val isEmptyList: LiveData<Boolean>

    val documentSnapshotList: LiveData<PagedList<DocumentSnapshot>> =
        Transformations.switchMap(refreshLiveData) {
            repository.invalidateDataSource()
            repository.updateList(it)
        }

    init {
        App.getInstance()
            .getEventViewModelComponent()
            .inject(this)
        refreshAllEventsList()
        progressLiveData = repository.progressLiveData
        progressLoadInitial = repository.progressLoadInitial
        isEmptyList = repository.isEmptyList
    }

    fun refreshAllEventsList(cat: String = "") {
        refreshLiveData.postValue(cat)
    }

    private fun insertEvents() {
        val time = Timestamp(Date())
        val geo = GeoPoint(2.0, 2.0)
        val cidade = hashMapOf("nome" to "Bagé", "estado" to "RS")
        for( i in 1..200) {
            val event = Event("$i-A", "TA", time, geo, cidade)
            repository.insertEvent(event)
        }
    }

}



