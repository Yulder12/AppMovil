package com.example.appmovil.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appmovil.R

class SoftwareAdapter(
    private val onClick: (Software) -> Unit
) : ListAdapter<Software, SoftwareAdapter.SoftwareViewHolder>(DiffCallback) {

    inner class SoftwareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.tvName)
        private val authorText: TextView = itemView.findViewById(R.id.tvAuthor)

        fun bind(software: Software) {
            nameText.text = software.nombre
            authorText.text = "Autor: ${software.autor}"
            itemView.setOnClickListener { onClick(software) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoftwareViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_software, parent, false)
        return SoftwareViewHolder(view)
    }

    override fun onBindViewHolder(holder: SoftwareViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Software>() {
            override fun areItemsTheSame(oldItem: Software, newItem: Software): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Software, newItem: Software): Boolean {
                return oldItem == newItem
            }
        }
    }
}


