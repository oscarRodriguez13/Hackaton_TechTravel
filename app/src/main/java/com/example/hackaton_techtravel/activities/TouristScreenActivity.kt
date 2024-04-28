package com.example.hackaton_techtravel.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackaton_techtravel.R
import com.example.hackaton_techtravel.adapters.TouristScreenTouristicPlaceAdapter
import com.example.hackaton_techtravel.data.Review
import com.example.hackaton_techtravel.data.TouristicPlace
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
private const val FILE_NAME = "touristicPlaces.txt"

class TouristScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tourist_recycler_view)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val cardList = mutableListOf<TouristicPlace>(
        )
        val places : ArrayList<TouristicPlace> = readTouristicPlacesFromTxtFile(this)
        for(place in places)
        {
            cardList.add(place)
        }
        val adapter = TouristScreenTouristicPlaceAdapter(cardList)
        recyclerView.adapter = adapter

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
    fun readTouristicPlacesFromTxtFile(context: Context): ArrayList<TouristicPlace> {
        val touristicPlaceList = ArrayList<TouristicPlace>()

        try {
            // Open the file from the assets folder
            val inputStream: InputStream = context.assets.open(FILE_NAME)
            val reader = BufferedReader(InputStreamReader(inputStream))

            var line: String?

            // Read each line from the file
            while (reader.readLine().also { line = it } != null) {
                val parts = line?.split(";")

                if (parts?.size == 4) {
                    val name = parts[0]
                    val picture = parts[1]
                    val scoresString = parts[2].split(" ") // Splitting scores separated by space
                    val coordinates = parts[3].split(" ")
                    val scores = ArrayList<Float>()
                    val coordinateArray = ArrayList<String>()
                    // Convert each score to float and add to scores list
                    for (scoreString in scoresString) {
                        val score = scoreString.toFloatOrNull() ?: continue
                        scores.add(score)
                    }
                    for (coord in coordinates) {
                        coordinateArray.add(coord)
                    }
                    // Create a list of Review with predefined data
                    val reviews = listOf(
                        Review("User 1", 4.5f, "Buen lugar", "user_icon.png"),
                        Review("User 2", 3.0f, "le faltaban cosas.", "user_icon.png"),
                        Review("User 3", 4.5f, "Me gusto mucho el ambiente!", "user_icon.png"),
                        Review("User 4", 3.0f, "No me gusto el el estilo .", "user_icon.png")
                        // ...
                    )

                    // Create a TouristicPlace object and add it to the list
                    val touristicPlace = TouristicPlace(name, picture, scores, coordinateArray, reviews)
                    touristicPlaceList.add(touristicPlace)
                }
            }

            // Close the InputStream when done
            inputStream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return touristicPlaceList
    }
}