package com.example.hackaton_techtravel.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import com.example.hackaton_techtravel.R
import com.example.hackaton_techtravel.data.Review

class ReviewsAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_reviews, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userPhoto: ImageView = itemView.findViewById(R.id.user_photo)
        private val userName: TextView = itemView.findViewById(R.id.user_name)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)
        private val reviewContent: TextView = itemView.findViewById(R.id.text_review_content)

        fun bind(review: Review) {
            userName.text = review.userName
            ratingBar.rating = review.score
            reviewContent.text = review.comment
        }
    }
}
