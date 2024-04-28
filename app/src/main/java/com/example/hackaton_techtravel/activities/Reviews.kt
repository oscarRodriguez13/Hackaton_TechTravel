package com.example.hackaton_techtravel.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackaton_techtravel.R
import com.example.hackaton_techtravel.adapters.ReviewsAdapter
import com.example.hackaton_techtravel.data.TouristicPlace

class Reviews : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_recycle_view)

        val myIntent = intent
        val touristicPlace = myIntent.getSerializableExtra("object") as TouristicPlace
        val reviews = touristicPlace.reviews // lista de reviews

        val recyclerView: RecyclerView = findViewById(R.id.ReviewsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ReviewsAdapter(reviews)
    }
}