package com.example.newsfeed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsfeed.R
import com.example.newsfeed.data.models.Article
import com.example.newsfeed.databinding.ArticleItemViewBinding

class ArticlesPagingAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<Article, ArticlesPagingAdapter.ArticleViewHolder>(
    ARTICLE_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ArticleItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class ArticleViewHolder(private val binding: ArticleItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_image_loading_error)
                    .into(articleImageView)

                articleHeadlineText.text = article.title
                articlePreviewText.text = article.description
                dateAndTime.text = article.publishedAt
                sourceNameText.text = article.source?.name
            }
        }
    }


    interface OnItemClickListener {
        fun onRecyclerViewItemClicked(article: Article)
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article) =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Article, newItem: Article) =
                oldItem == newItem
        }
    }
}
