package com.example.hackaton_techtravel.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.hackaton_techtravel.R
import com.example.hackaton_techtravel.data.TouristicPlace
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TouristMoreInfoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.place_info)

        val myIntent = intent
        val derivedObject = myIntent.getSerializableExtra("object") as TouristicPlace

        val myTextView = findViewById<TextView>(R.id.InfoPlaceName)
        val myImageView = findViewById<ImageView>(R.id.InfoPlaceImage)
        val viewReviews = findViewById<FloatingActionButton>(R.id.viewReviews)
        val navigateButton = findViewById<FloatingActionButton>(R.id.navigate)
        // Set the text for the TextView
        myTextView.text = derivedObject.name

        // Load the image into the ImageView using Picasso
       /* Picasso.get().load(derivedObject.picture).into(myImageView)
        viewReviews.setOnClickListener{
            val intent = Intent(this, Reviews::class.java)
            intent.putExtra("object", derivedObject)
            startActivity(intent)
        }*/
        navigateButton.setOnClickListener {
            // Create an Intent with ACTION_VIEW and the URI
            val intent = Intent(this, MapaEventosActivity::class.java)
            startActivity(intent)
        }

    }
}