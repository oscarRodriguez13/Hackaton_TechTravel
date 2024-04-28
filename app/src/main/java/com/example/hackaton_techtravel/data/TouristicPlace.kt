package com.example.hackaton_techtravel.data

import java.io.Serializable

data class TouristicPlace(
    val name: String,
    val picture: String,
    val scores: List<Float>,
    val coordinates: List<String>,
    val reviews: List<Review>
): Serializable