package com.example.hackaton_techtravel.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.hackaton_techtravel.R

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