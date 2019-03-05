package com.dev.peralta.marqueeventos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.peralta.marqueeventos.R
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.event_adapter.view.*

class EventViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(doc: DocumentSnapshot?) {
        view.desc.text = doc?.getString("desc") ?: "Null"
    }

    companion object {
        fun create(parent: ViewGroup):  RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.event_adapter, parent, false)

            return EventViewHolder(view)
        }
    }
}