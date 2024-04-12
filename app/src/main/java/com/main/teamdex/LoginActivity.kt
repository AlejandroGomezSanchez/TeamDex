package com.main.teamdex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.main.teamdex.databinding.ActivityLoginBinding
import com.main.teamdex.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    companion object{
        var usuario=0
    }

    private var _binding: ActivityLoginBinding? = null
    private val binding
        get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EquipoProvider.rellenaLista(resources.configuration.locale.language)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root

        binding.button.setOnClickListener {
            val conex = ConectorDB.connectToDatabase()

            usuario = ConectorDB.login(conex, binding.textInputEditText.text.toString(), binding.textInputEditText2.text.toString())

            conex?.close()

            if (usuario!=0) {

                startActivity(Intent(this, MainActivity::class.java))
            }

        }

        binding.button5.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        setContentView(view)

    }
}