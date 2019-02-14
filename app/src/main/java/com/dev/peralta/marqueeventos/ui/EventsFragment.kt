package com.dev.peralta.marqueeventos.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.dev.peralta.marqueeventos.R
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.fragment_events.*

class EventsFragment : Fragment() {
    private lateinit var viewModel: EventViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        viewModel.documentSnapshotList.observe(this, Observer {
            buildList(it)
        })
    }

    private fun buildList(pagedList: PagedList<DocumentSnapshot>) {
        val adapter = EventAdapter()
        adapter.submitList(pagedList)
        list.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.events_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.action_update -> {
                viewModel.updateAllEventsList()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
