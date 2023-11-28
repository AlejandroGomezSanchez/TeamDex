package com.main.teamdex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAnswer = findViewById<Button>(R.id.button)
        btnAnswer.setOnClickListener {
            goToCredit()
        }
    }

    fun goToCredit() {
        //crear el intent
        val intent = Intent(this@MainActivity, CreditActivity::class.java)
        //tomar el valor del cuadro de texto.
        val etNombre = this.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.textInputEditText)
        //añadimos la información necesaria al intent
        intent.putExtra("NAME", etNombre.text.toString())

        //iniciar la nueva actividad
        startActivity(intent)
    }
}