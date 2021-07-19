package com.example.newsfeed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsfeed.R
import com.example.newsfeed.data.models.Article
import com.example.newsfeed.databinding.ArticleItemViewBinding

class SavedArticlesRecycleViewAdapter(
    private val listener: OnItemClickListener
): ListAdapter<Article,
        SavedArticlesRecycleViewAdapter.ArticleViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ArticleViewHolder(private val binding: ArticleItemViewBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val article = getItem(position)
                    if (article != null) {
                        listener.onRecyclerViewItemClicked(article)
                    }
                }
            }
        }

        fun bind(article: Article) {
            binding.apply {
                sourceNameText.text = article.source?.name
                dateAndTime.text = article.publishedAt
                articleHeadlineText.text = article.title
                articlePreviewText.text = article.description
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_image_loading_error)
                    .into(articleImageView)
            }
        }
    }

    interface OnItemClickListener {
        fun onRecyclerViewItemClicked(article: Article)
    }

    class DiffCallback: DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return if (oldItem.id != null && newItem.id != null) {
                 oldItem.id == newItem.id
            } else return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }
}
