package com.example.appmovil.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appmovil.databinding.FragmentAddSoftwareBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddSoftwareFragment : Fragment() {

    private var _binding: FragmentAddSoftwareBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddSoftwareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnGuardar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val descripcion = binding.etDescripcion.text.toString()
            val autor = binding.etAutor.text.toString()
            val version = binding.etVersion.text.toString()
            val linkContacto = binding.etLink.text.toString()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty()) {
                val software = hashMapOf(
                    "nombre" to nombre,
                    "descripcion" to descripcion,
                    "autor" to autor,
                    "version" to version,
                    "linkContacto" to linkContacto
                )
                db.collection("softwares")
                    .add(software)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Software guardado", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(requireContext(), "Error: ${exception.message}", Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(requireContext(), "Nombre y descripción son requeridos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
