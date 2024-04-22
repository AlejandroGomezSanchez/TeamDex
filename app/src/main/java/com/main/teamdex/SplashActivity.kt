package com.main.teamdex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import okhttp3.internal.wait

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        EquipoProvider.rellenaLista(resources.configuration.locale.language)
        Thread.sleep(5000)
        startActivity(Intent(this,LoginActivity::class.java))
    }
}