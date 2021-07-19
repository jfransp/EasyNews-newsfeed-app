package com.example.newsfeed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.data.models.Source
import com.example.newsfeed.databinding.SourceItemViewBinding

class SourcesRecyclerViewAdapter(
    private val listener: OnItemClickListener
): ListAdapter<Source, SourcesRecyclerViewAdapter.SourceViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val binding = SourceItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SourceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class SourceViewHolder(private val binding: SourceItemViewBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                sourceNameButton.setOnClickListener{
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val source = getItem(position)
                        if (source != null) {
                            listener.sourceItemClicked(source)
                        }
                    }
                }
                navigateButton.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val url = getItem(position).url
                        listener.navigateButtonClicked(url)

                    }
                }
            }
        }

        fun bind(source: Source) {
            binding.apply {
                sourceNameButton.text = source.name
            }
        }
    }

    interface OnItemClickListener {
        fun sourceItemClicked(source: Source)
        fun navigateButtonClicked(url: String)
    }

    class DiffCallback: DiffUtil.ItemCallback<Source>() {

        override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
            return if (oldItem.id != null && newItem.id != null) {
                oldItem.id == newItem.id
            } else return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Source, newItem: Source) =
            oldItem == newItem
    }
}
