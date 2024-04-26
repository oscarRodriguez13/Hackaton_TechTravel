package com.example.hackaton_techtravel.activities

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import com.example.hackaton_techtravel.data.Avistamiento
import com.example.hackaton_techtravel.R
import com.example.hackaton_techtravel.adapters.SearchAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class TouristSearchActivity : AppCompatActivity() {

    private lateinit var searchAdapter1: SearchAdapter
    private lateinit var searchAdapter2: SearchAdapter
    private lateinit var searchAdapter3: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_view)

        // Definir la lista de datos (Avistamiento) para el primer RecyclerView
        val avistamientos1 = listOf(
            Avistamiento(R.drawable.img_malacoptila, "Malacoptila", "Familia de los Bucconidae", "08/01/2024"),
            Avistamiento(R.drawable.img_guacharaca, "Guacharaca", "Ave de bosque", "02/03/2024"),
            Avistamiento(R.drawable.img_loro_multicolor, "Loro multicolor", "Ave insignia de Caldas", "25/06/2024"),
        )

        // Instanciar y configurar el adaptador y RecyclerView para el primer conjunto de datos
        val recyclerView1: RecyclerView = findViewById(R.id.recyclerView1)
        searchAdapter1 = SearchAdapter(avistamientos1)
        recyclerView1.adapter = searchAdapter1
        recyclerView1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Definir la lista de datos (Avistamiento) para el segundo RecyclerView
        val avistamientos2 = listOf(
            Avistamiento(R.drawable.img_cuclillo_canelo, "Cuclillo Canelo", "Vive en tierras bajas tropicales", "03/04/2023"),
            Avistamiento(R.drawable.img_paujil_colombiano, "Paujil colombiano", "Ave galliforme de la familia Cracidae", "20/04/2024"),
            Avistamiento(R.drawable.img_pava_andina, "Pava andina", "Especie de ave galliforme", "17/11/2022")
        )

        // Instanciar y configurar el adaptador y RecyclerView para el segundo conjunto de datos
        val recyclerView2: RecyclerView = findViewById(R.id.recyclerView2)
        searchAdapter2 = SearchAdapter(avistamientos2)
        recyclerView2.adapter = searchAdapter2
        recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Definir la lista de datos (Avistamiento) para el tercer RecyclerView
        val avistamientos3 = listOf(
            Avistamiento(R.drawable.img_paloma_colorada, "Paloma colorada", "Es una especie sedentaria", "06/10/2023"),
            Avistamiento(R.drawable.img_garrapatero_pijui, "Garrapatero pijui", "Especie de ave cuculiforme", "15/03/2023"),
            Avistamiento(R.drawable.img_barranquero, "Barranquero", "Ave nativa de America del Sur", "14/07/2023")
        )

        // Instanciar y configurar el adaptador y RecyclerView para el tercer conjunto de datos
        val recyclerView3: RecyclerView = findViewById(R.id.recyclerView3)
        searchAdapter3 = SearchAdapter(avistamientos3)
        recyclerView3.adapter = searchAdapter3
        recyclerView3.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set the map menu item as selected
        bottomNavigationView.selectedItemId = R.id.navigation_search

        // Set listener for BottomNavigationView items
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    finish()
                    startActivity(Intent(this, TouristScreenActivity::class.java))
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

        val searchText: EditText = findViewById(R.id.editText)

        searchText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                val query = searchText.text.toString().toLowerCase(Locale.getDefault())
                searchAdapter1.filter(query)
                searchAdapter2.filter(query)
                searchAdapter3.filter(query)
                true
            } else {
                false
            }
        }
    }
}
