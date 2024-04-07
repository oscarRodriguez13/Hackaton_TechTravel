package com.example.hackaton_techtravel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class EditarPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        val buttonGoToApp: Button = findViewById(R.id.button_go_to_app)

        buttonGoToApp.setOnClickListener {
            val intent = Intent(this, TouristScreen::class.java)
            startActivity(intent)
        }
    }
}
