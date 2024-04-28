package com.example.hackaton_techtravel.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hackaton_techtravel.R
import com.example.hackaton_techtravel.data.Review
import com.example.hackaton_techtravel.data.TouristicPlace
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class TouristMoreInfoActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.place_info)

        // Obtén la referencia al RecyclerView
        recyclerView = findViewById(R.id.recycler_view)

        val myIntent = intent
        val derivedObject = myIntent.getSerializableExtra("object") as TouristicPlace

        val myTextView = findViewById<TextView>(R.id.InfoPlaceName)
        val myImageView = findViewById<ImageView>(R.id.InfoPlaceImage)
        val viewReviews = findViewById<FloatingActionButton>(R.id.viewReviews)
        val navigateButton = findViewById<FloatingActionButton>(R.id.navigate)

        // Set the text for the TextView
        myTextView.text = derivedObject.name

        // Load the image into the ImageView using Picasso
        Picasso.get().load(derivedObject.picture).into(myImageView)

        viewReviews.setOnClickListener{
            val intent = Intent(this, Reviews::class.java)
            intent.putExtra("object", derivedObject)
            startActivity(intent)
        }

        navigateButton.setOnClickListener {
            // Create an Intent with ACTION_VIEW and the URI
            val intent = Intent(this, MapaEventosActivity::class.java)
            startActivity(intent)
        }

        // Calcula la puntuación media de las reseñas
        val averageScore = derivedObject.reviews.map { it.score }.average()

        // Encuentra el RatingBar y establece la puntuación media
        val ratingBar: RatingBar = findViewById(R.id.rating_bar)
        ratingBar.rating = averageScore.toFloat()

        // Configura el adaptador del RecyclerView con las reseñas del lugar turístico
        recyclerView.adapter = ReviewAdapter(derivedObject.reviews)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        // Set the map menu item as selected
        bottomNavigationView.selectedItemId = R.id.navigation_home

        // Set listener for BottomNavigationView items
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, TouristScreenActivity::class.java))
                    true
                }
                R.id.navigation_search -> {
                    startActivity(Intent(this, TouristSearchActivity::class.java))
                    true
                }
                R.id.navigation_map -> {
                    startActivity(Intent(this, MapsActivity::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    inner class ReviewAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

        inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val userPhoto: ImageView = itemView.findViewById(R.id.user_photo)
            private val userName: TextView = itemView.findViewById(R.id.user_name)
            private val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)
            private val reviewContent: TextView = itemView.findViewById(R.id.text_review_content)

            fun bind(review: Review) {
                userName.text = review.userName
                ratingBar.rating = review.score
                reviewContent.text = review.comment

                // Cargar la imagen del usuario en userPhoto usando Picasso
                Picasso.get().load(review.userPhotoUrl).into(userPhoto)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.place_reviews, parent, false)
            return ReviewViewHolder(view)
        }

        override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
            holder.bind(reviews[position])
        }

        override fun getItemCount() = reviews.size
    }
}