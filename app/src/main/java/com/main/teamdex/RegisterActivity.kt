package com.main.teamdex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                }
                conex?.close()

            }catch (e:Exception){
                conex?.close();
            }
        }

        setContentView(binding.root)
    }
}