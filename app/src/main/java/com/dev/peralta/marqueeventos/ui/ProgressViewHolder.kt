package com.dev.peralta.marqueeventos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.peralta.marqueeventos.R
import kotlinx.android.synthetic.main.progress_adapter.view.*

class ProgressViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(isProgress: Boolean) {
        view.progress.visibility = if (isProgress) View.VISIBLE else View.GONE
    }

    companion object {
        fun create(parent: ViewGroup): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.progress_adapter, parent, false)

            return ProgressViewHolder(view)
        }
    }
}