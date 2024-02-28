package com.main.teamdex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.main.teamdex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root




//incluye un controlador de navegacion. fragmentcontainerView2 es el host de navegación.
        val navFragment = supportFragmentManager.findFragmentById(R.id.fcwMain) as NavHostFragment
        val navController = navFragment.navController
//establece la barra con controles

        setSupportActionBar(binding.toolbar)

        NavigationUI.setupActionBarWithNavController(this,navController)


        binding.bottomNavigationView
            .setupWithNavController(navController)

        binding.bottomNavigationView.visibility = INVISIBLE

        setContentView(view)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.fcwMain)
        return navController.navigateUp()
    }

    public fun activaNav(){
        binding.bottomNavigationView.visibility = VISIBLE
    }

}