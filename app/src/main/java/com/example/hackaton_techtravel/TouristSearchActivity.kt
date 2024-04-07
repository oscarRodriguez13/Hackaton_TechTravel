package com.example.hackaton_techtravel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
private const val FILE_NAME = "touristicPlaces.txt"
class TouristSearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: TouristScreenTouristicPlaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_view)

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val cardList = mutableListOf<TouristicPlace>()
        // Load touristic places from the text file
        val places: ArrayList<TouristicPlace> = readTouristicPlacesFromTxtFile(this)
        for (place in places) {
            cardList.add(place)
        }
        adapter = TouristScreenTouristicPlaceAdapter(cardList)
        recyclerView.adapter = adapter

        // Set up SearchView listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                adapter.filter.filter(query)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set listener for BottomNavigationView items
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    finish()
                    startActivity(Intent(this, TouristScreen::class.java))
                    true
                }
                R.id.navigation_search -> {
                    // Handle the search icon click
                    finish()
                    startActivity(Intent(this, TouristSearchActivity::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    //startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun readTouristicPlacesFromTxtFile(context: Context): ArrayList<TouristicPlace> {
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
                    // Create an TouristicPlace object and add it to the list
                    val TouristicPlace = TouristicPlace(name, picture, scores, coordinateArray)
                    touristicPlaceList.add(TouristicPlace)
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