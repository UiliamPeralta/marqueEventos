package com.dev.peralta.marqueeventos.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.dev.peralta.marqueeventos.R
import com.google.firebase.firestore.DocumentSnapshot

class EventAdapter : PagedListAdapter<DocumentSnapshot, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var isProgress = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.event_adapter) EventViewHolder.create(parent)
        else ProgressViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            R.layout.event_adapter -> (holder as EventViewHolder).bind(getItem(position))
            R.layout.progress_adapter -> {
                (holder as ProgressViewHolder).bind(isProgress)
            }
        }
    }

    override fun getItemCount() =
        super.getItemCount() + if (isProgress) 1 else 0


    fun setStatus(newStatus: Boolean) {
        isProgress = newStatus
        notifyItemChanged(itemCount)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isProgress && position == itemCount - 1) {
            R.layout.progress_adapter
        }
        else R.layout.event_adapter
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DocumentSnapshot>() {
            override fun areItemsTheSame(oldItem: DocumentSnapshot, newItem: DocumentSnapshot): Boolean {
                return oldItem.id == newItem.id
            }


            override fun areContentsTheSame(oldItem: DocumentSnapshot, newItem: DocumentSnapshot): Boolean {
                return oldItem == newItem
            }
        }
    }
}

