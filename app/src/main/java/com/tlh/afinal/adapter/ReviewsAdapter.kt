package com.tlh.afinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tlh.afinal.databinding.ItemReviewBinding
import com.tlh.afinal.model.in_app_service.Review

class ReviewsAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.binding.apply {
            reviewerNameTextView.text = review.reviewerName
            reviewTextView.text = review.comment
            reviewRatingTextView.text = "Rating: ${review.rating}"
     //       reviewDateTextView.text = "Date: ${review.date}"


        }
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}
