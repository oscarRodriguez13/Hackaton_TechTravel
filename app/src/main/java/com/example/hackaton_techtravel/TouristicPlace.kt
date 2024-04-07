package com.example.hackaton_techtravel

import java.io.Serializable

data class TouristicPlace(val name: String, val picture: String, val scores:ArrayList<Float>, val coordinates:ArrayList<String>): Serializable