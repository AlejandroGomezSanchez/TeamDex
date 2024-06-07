package com.main.teamdex

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.main.teamdex.databinding.FragmentDetailItemBinding
import com.main.teamdex.databinding.FragmentUserInfoBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding : FragmentUserInfoBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserInfoBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.Main).launch {
            setUsu()
        }

        binding.cerrarSesion.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                this@UserInfoFragment.context?.let { it1 -> LoginActivity.borrarUsuario(it1) }
                startActivity(Intent(this@UserInfoFragment.context,LoginActivity::class.java))
            }
        }

        return binding.root
    }

    suspend fun setUsu(){
        val nombre: String

        withContext(Dispatchers.IO) {
            nombre = ConectorDB.getNombreUsu(LoginActivity.usuarioId)
        }

        // Cargar las imágenes en los ImageView después de que todos los sprites se hayan descargado
        withContext(Dispatchers.Main) {
            binding.user.text = getString(R.string.usuario2) + " " + nombre
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}