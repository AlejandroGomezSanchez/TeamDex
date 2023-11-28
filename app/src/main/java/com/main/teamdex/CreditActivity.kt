package com.main.teamdex

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class CreditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit)

        //obtener una referencia a la etiqueta de la actividad
        val tvVersion = findViewById<TextView>(R.id.textView3)

        val contact = findViewById<Button>(R.id.btCont)

        //recogemos el nombre del intent
        val nombre = intent.getStringExtra("NAME")
        //creamos la cadena del saludo
        tvVersion.apply {
            //toma el valor de la cadena "greeting" y le incorpora el valor del nombre.
            text = nombre + " " + getString(R.string.version)
        }

        contact.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO);

            intent.data = Uri.parse("mailto:"); // Only email apps should handle this

            intent.putExtra(Intent.EXTRA_EMAIL, "agom   san2009@g.educaand.es");

            intent.putExtra(Intent.EXTRA_SUBJECT, "Consulta de la app TeamDex");



                startActivity(intent);
        }
    }
}