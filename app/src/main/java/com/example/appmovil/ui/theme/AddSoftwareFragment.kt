package com.example.appmovil.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appmovil.databinding.FragmentAddSoftwareBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddSoftwareFragment : Fragment() {

    private var _binding: FragmentAddSoftwareBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSoftwareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveSoftware.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val description = binding.editTextDescription.text.toString().trim()
            val developer = binding.editTextDeveloper.text.toString().trim()

            // Validaciones básicas
            if (name.isEmpty() || description.isEmpty() || developer.isEmpty()) {
                Toast.makeText(context, "Por favor, completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price = binding.editTextPrice.text.toString().toDoubleOrNull()
            val rating = binding.editTextRating.text.toString().toFloatOrNull()
            val downloads = binding.editTextDownloads.text.toString().toIntOrNull()

            if (price == null || rating == null || downloads == null) {
                Toast.makeText(context, "Verifica que precio, calificación y descargas sean números válidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val software = hashMapOf(
                "name" to name,
                "description" to description,
                "price" to price,
                "developer" to developer,
                "rating" to rating,
                "downloads" to downloads
            )

            db.collection("softwares")
                .add(software)
                .addOnSuccessListener {
                    Toast.makeText(context, "Software guardado exitosamente", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
