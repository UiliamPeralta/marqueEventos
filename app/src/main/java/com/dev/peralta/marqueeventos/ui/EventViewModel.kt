package com.dev.peralta.marqueeventos.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dev.peralta.marqueeventos.App
import com.dev.peralta.marqueeventos.data.Repository
import com.google.firebase.firestore.DocumentSnapshot

import javax.inject.Inject

class EventViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository
    val documentSnapshotList: LiveData<PagedList<DocumentSnapshot>>

    init {
        App.getInstance()
            .getEventViewModelComponent()
            .inject(this)
        documentSnapshotList = repository.documentSnapshotList
    }

    fun updateAllEventsList() {
        repository.invalidateDataSource()
    }
}
