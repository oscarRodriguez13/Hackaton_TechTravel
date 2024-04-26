package com.example.hackaton_techtravel.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.hackaton_techtravel.R

class IniciarSesionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        findViewById<Button>(R.id.register_button).setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    CrearCuentaActivity::class.java
                )
            )
        }

        findViewById<Button>(R.id.login_button).setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    TouristScreenActivity::class.java
                )
            )
        }
    }
}