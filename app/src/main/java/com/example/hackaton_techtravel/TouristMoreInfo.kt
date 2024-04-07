package com.example.hackaton_techtravel

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class TouristMoreInfo: AppCompatActivity() {
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
            val latitude = derivedObject.coordinates[0]
            val longitude = derivedObject.coordinates[1]

            // Construct the URI for Google Maps navigation
            val uri = "http://maps.google.com/maps?saddr=Current+Location&daddr=$latitude,$longitude"

            // Create an Intent with ACTION_VIEW and the URI
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))

            // Set the package to Google Maps
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

    }
}