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
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_view)

        // Definir la lista de datos (Avistamiento)
        val avistamientos = listOf(
            Avistamiento(R.drawable.img_malacoptila, "Malacoptila", "Familia de los Bucconidae", "08/01/2024"),
            Avistamiento(R.drawable.img_guacharaca, "Guacharaca", "Ave de bosque", "02/03/2024"),
            Avistamiento(R.drawable.img_loro_multicolor, "Loro multicolor", "Ave insignia de Caldas", "25/06/2024"),
            Avistamiento(R.drawable.img_cuclillo_canelo, "Cuclillo Canelo", "Vive en tierras bajas tropicales", "03/04/2023"),
            Avistamiento(R.drawable.img_paujil_colombiano, "Paujil colombiano", "Ave galliforme de la familia Cracidae", "20/04/2024"),
            Avistamiento(R.drawable.img_pava_andina, "Pava andina", "Especie de ave galliforme", "17/11/2022"),
            Avistamiento(R.drawable.img_paloma_colorada, "Paloma colorada", "Es una especie sedentaria", "06/10/2023"),
            Avistamiento(R.drawable.img_garrapatero_pijui, "Garrapatero pijui", "Especie de ave cuculiforme", "15/03/2023"),
            Avistamiento(R.drawable.img_barranquero, "Barranquero", "Ave nativa de America del Sur", "14/07/2023")
        )

        // Instanciar el adaptador
        searchAdapter = SearchAdapter(avistamientos)

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = searchAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


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


}