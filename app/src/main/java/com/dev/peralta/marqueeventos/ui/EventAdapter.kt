package com.dev.peralta.marqueeventos.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.dev.peralta.marqueeventos.R
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.event_adapter.view.*

class EventAdapter : PagedListAdapter<DocumentSnapshot, EventAdapter.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doc = getItem(position)
        doc?.let {
            holder.desc.text = doc.getString("desc")
        }
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val desc: TextView = mView.desc
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DocumentSnapshot>() {
            override fun areItemsTheSame(oldItem: DocumentSnapshot, newItem: DocumentSnapshot) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DocumentSnapshot, newItem: DocumentSnapshot) =
                oldItem == newItem

        }
    }
}

