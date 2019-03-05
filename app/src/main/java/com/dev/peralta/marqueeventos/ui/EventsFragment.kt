package com.dev.peralta.marqueeventos.ui

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.dev.peralta.marqueeventos.R
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.fragment_events.*

class EventsFragment : Fragment() {
    private lateinit var viewModel: EventViewModel
    private var adapter: EventAdapter? = null
    private var category = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        setHasOptionsMenu(true)
        return view
    }

    private fun spinnerCreate() {
        val adapter = ArrayAdapter.createFromResource(
            activity!!,
            R.array.categorias,
            R.layout.support_simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = getOnItemSelectedListener()

    }

    private fun getOnItemSelectedListener() = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val cats = resources.getStringArray(R.array.categorias)
            category = if (position == 0) "" else cats[position]
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        spinnerCreate()
        documentSnapshotListObserve()
        progressLiveDataObserve()
        progressLoadInitialObserve()
        isEmptyListObserve()
        btBuscar.setOnClickListener {
            viewModel.refreshAllEventsList(category)
        }
    }

    private fun documentSnapshotListObserve() {
        viewModel.documentSnapshotList.observe(this, Observer {
            buildList(it)
        })
    }

    private fun progressLiveDataObserve() {
        viewModel.progressLiveData.observe(this, Observer {
            adapter?.setStatus(it)
        })
    }
    private fun progressLoadInitialObserve() {
        viewModel.progressLoadInitial.observe(this, Observer {
            if(it) {
                progressBar.visibility = View.VISIBLE
                viewMessage.visibility = View.VISIBLE
                viewMessage.text = getString(R.string.load)
            } else {
                progressBar.visibility = View.INVISIBLE
                viewMessage.visibility = View.INVISIBLE
            }
        })
    }

    private fun isEmptyListObserve() {
        viewModel.isEmptyList.observe(this, Observer {
            if(it) {
                viewMessage.visibility = View.VISIBLE
                viewMessage.text = getString(R.string.nenhum_dado)
            } else {

                viewMessage.visibility = View.INVISIBLE
            }
        })
    }

    private fun buildList(pagedList: PagedList<DocumentSnapshot>) {
        val adapter = EventAdapter()
        this.adapter = adapter
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
                viewModel.refreshAllEventsList(category)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
