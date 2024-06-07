package com.main.teamdex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        CoroutineScope(Dispatchers.IO).launch {
            conex()
        }
        CoroutineScope(Dispatchers.IO).launch {
            rellenaNom()
        }
        CoroutineScope(Dispatchers.IO).launch {
            rellenaPoke()
        }
Thread.sleep(3000)
        startActivity(Intent(this,LoginActivity::class.java))
    }

    suspend fun conex(){
        ConectorDB.connectToDatabase()
        EquipoProvider.rellenaLista()
        EquipoProvider.rellenaNombres()

    }
    suspend fun rellenaNom(){
        ConectorDB.connectToDatabase()
        EquipoProvider.rellenaLista()
        EquipoProvider.rellenaNombres()

    }
    suspend fun rellenaPoke(){
        ConectorDB.connectToDatabase()
        EquipoProvider.rellenaLista()
        EquipoProvider.rellenaNombres()

    }
}