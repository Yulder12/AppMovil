package com.example.appmovil.ui.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.appmovil.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var inputName: EditText
    private lateinit var inputAge: EditText
    private lateinit var inputCity: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSave: Button
    private lateinit var outputText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Cargar el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        emailInput = view.findViewById(R.id.emailInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        inputName = view.findViewById(R.id.inputName)
        inputAge = view.findViewById(R.id.inputAge)
        inputCity = view.findViewById(R.id.inputCity)
        btnLogin = view.findViewById(R.id.btnLogin)
        btnSave = view.findViewById(R.id.btnSave)
        outputText = view.findViewById(R.id.outputText)

        btnLogin.setOnClickListener {
            val email = emailInput.text.toString()
            val pass = passwordInput.text.toString()
            loginOrRegister(email, pass)
        }

        btnSave.setOnClickListener {
            val name = inputName.text.toString()
            val age = inputAge.text.toString()
            val city = inputCity.text.toString()

            if (auth.currentUser != null && name.isNotEmpty() && age.isNotEmpty() && city.isNotEmpty()) {
                saveUserData(name, age.toInt(), city)
            } else {
                Toast.makeText(this.context, "Faltan datos o no has iniciado sesión", Toast.LENGTH_SHORT).show()
            }
        }

        if (auth.currentUser != null) {
            loadUsers()
        }
        return view
    }

    private fun loginOrRegister(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            Toast.makeText(requireContext(), "Sesión iniciada", Toast.LENGTH_SHORT).show()
            loadUsers()
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }.addOnFailureListener {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(requireContext(), "Usuario registrado", Toast.LENGTH_SHORT).show()
                loadUsers()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserData(name: String, age: Int, city: String) {
        val userId = auth.currentUser?.uid ?: return

        val data = hashMapOf(
            "nombre" to name,
            "edad" to age,
            "ciudad" to city,
            "userId" to userId
        )

        db.collection("usuarios")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Datos guardados", Toast.LENGTH_SHORT).show()
                loadUsers()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUsers() {
        db.collection("usuarios")
            .get()
            .addOnSuccessListener { result ->
                val data = StringBuilder()
                for (doc in result) {
                    val nombre = doc.getString("nombre")
                    val edad = doc.getLong("edad")
                    val ciudad = doc.getString("ciudad")
                    data.append("- $nombre, $edad años, $ciudad\n")
                }
                outputText.text = data.toString()
            }
            .addOnFailureListener {
                outputText.text = "Error al leer datos"
            }
    }
}
