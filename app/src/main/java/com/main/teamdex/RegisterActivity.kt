package com.main.teamdex

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.main.teamdex.databinding.ActivityLoginBinding
import com.main.teamdex.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {


    private var _binding: ActivityRegisterBinding? = null
    private val binding
        get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)



        binding.button3.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.button4.setOnClickListener{
            val conex = ConectorDB.connectToDatabase()
            try{

                val comp = ConectorDB.añadeUsuario(conex, binding.textInputEditText.text.toString(), binding.textInputEditText2.text.toString())

                if(comp){
                    startActivity(Intent(this, LoginActivity::class.java))
                }else{
                    println("ErrorRegistro")
                    binding.error.text="Nombre no disponible"
                    binding.error.visibility= View.VISIBLE
                }
                conex?.close()

            }catch (e:Exception){
                println("CatchRegistro")
                conex?.close();

            }
        }

        setContentView(binding.root)
    }
}