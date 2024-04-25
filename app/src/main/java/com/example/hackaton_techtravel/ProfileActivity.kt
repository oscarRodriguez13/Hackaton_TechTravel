package com.example.hackaton_techtravel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Arrays

class ProfileActivity : AppCompatActivity() {

    private var isNotified = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val avistamientos = Arrays.asList(
            Avistamiento(R.drawable.img_barranquero, "Barranquero", "Fue vista mientras estaba de excursión con mi familia", "09/12/2023"),
            Avistamiento(R.drawable.img_loro_multicolor, "Loro multicolor", "Vista en las inmediaciones de la estación Awaq", "12/02/2024"),
            Avistamiento(R.drawable.img_guacharaca, "Guacharaca", "Vista en medio del bosque", "31/01/2024")
        )

        val adapter = SolicitudPaseoAdapter(avistamientos)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val notifyButton = findViewById<CircleImageView>(R.id.icn_notificacion)
        if (isNotified) {
            notifyButton.setImageResource(R.drawable.icn_notificacion_inactiva)
        } else {
            notifyButton.setImageResource(R.drawable.icn_notificacion)
        }
        isNotified = !isNotified

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set the map menu item as selected
        bottomNavigationView.selectedItemId = R.id.navigation_profile

        // Set listener for BottomNavigationView items
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, TouristScreen::class.java))
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
}
