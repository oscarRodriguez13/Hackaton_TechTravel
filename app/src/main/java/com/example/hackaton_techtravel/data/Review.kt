package com.example.hackaton_techtravel.data

import java.io.Serializable

data class Review(
    val userName: String,
    val score: Float,
    val comment: String,
    val userPhotoUrl: String
) : Serializable