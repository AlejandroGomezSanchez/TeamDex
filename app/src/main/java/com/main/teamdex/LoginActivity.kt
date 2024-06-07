package com.main.teamdex

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.fragment.findNavController
import com.main.teamdex.databinding.ActivityLoginBinding
import com.main.teamdex.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    companion object {
        val usuarioKey = intPreferencesKey("usuario")
        var usuarioId = 0
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

        suspend fun borrarUsuario(context: Context) {
            val dataStore = context.dataStore
            dataStore.edit { settings ->
                settings.remove(usuarioKey)
            }
        }
    }

    private lateinit var dataStore: DataStore<Preferences>
    private var _binding: ActivityLoginBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            println("hilo")
            usuarioId = obtenerUsuarioId(this@LoginActivity)
            if (usuarioId!=0){
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root

        // Inicializar el DataStore

        dataStore = applicationContext.dataStore

        binding.button.setOnClickListener {

            usuarioId = ConectorDB.login(binding.textInputEditText.text.toString(), binding.textInputEditText2.text.toString())

            if (usuarioId != 0) {
                if (binding.checkBox.isChecked) {
                    // Guardar el ID del usuario en DataStore
                    CoroutineScope(Dispatchers.IO).launch {
                        guardaUsu(this@LoginActivity, usuarioId)
                    }
                }
                // Iniciar la actividad principal
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        binding.button5.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        setContentView(view)
    }

    private suspend fun guardaUsu(context: Context, usuarioId: Int) {
        println("guardar")
        dataStore.edit { settings ->
            settings[usuarioKey] = usuarioId
        }
    }

    suspend fun obtenerUsuarioId(context: Context): Int {
        val dataStore = context.dataStore
        val settings = dataStore.data.first()
        println("saca "+settings[usuarioKey])
        return settings[usuarioKey] ?: 0 // Si no hay valor, retorna 0 por defecto
    }

}
