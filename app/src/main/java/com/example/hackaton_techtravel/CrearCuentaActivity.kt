package com.example.hackaton_techtravel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CrearCuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)


        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    IniciarSesionActivity::class.java
                )
            )
        }
    }
}