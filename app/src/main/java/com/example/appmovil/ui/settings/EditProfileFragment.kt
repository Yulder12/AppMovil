package com.example.appmovil.ui.settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.appmovil.databinding.FragmentEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.FileOutputStream


class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    private var imageUri: Uri? = null

    // Selector de imagen
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            imageUri = result.data!!.data
            binding.imageViewProfile.setImageURI(imageUri)
            Log.d("UploadDebug", "Imagen seleccionada: $imageUri")
        } else {
            Log.d("UploadDebug", "No se seleccionó imagen.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(requireContext(), "Debes iniciar sesión para editar tu perfil", Toast.LENGTH_SHORT).show()
            return
        }

        loadUserProfile()

        binding.imageViewProfile.setOnClickListener {
            pickImageFromGallery()
        }

        binding.btnSave.setOnClickListener {
            saveUserProfile()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun loadUserProfile() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("usuarios").document(userId).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    binding.etNombre.setText(doc.getString("name") ?: "")
                    binding.etBio.setText(doc.getString("bio") ?: "")

                    val imagePath = doc.getString("imagePath")
                    if (!imagePath.isNullOrEmpty()) {
                        val file = File(imagePath)
                        if (file.exists()) {
                            Glide.with(this)
                                .load(file)
                                .into(binding.imageViewProfile)
                        }
                    }
                } else {
                    Log.d("LoadProfile", "No existe perfil para el usuario $userId")
                }
            }
            .addOnFailureListener { e ->
                Log.e("LoadProfile", "Error al cargar perfil: ${e.message}", e)
                Toast.makeText(requireContext(), "Error al cargar perfil", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserProfile() {
        val name = binding.etNombre.text.toString().trim()
        val bio = binding.etBio.text.toString().trim()
        val userId = auth.currentUser?.uid ?: return

        val updateData = hashMapOf(
            "name" to name,
            "bio" to bio
        )

        if (imageUri != null) {
            try {
                val inputStream = requireContext().contentResolver.openInputStream(imageUri!!)
                val file = File(requireContext().filesDir, "$userId.png")
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()

                updateData["imagePath"] = file.absolutePath
            } catch (e: Exception) {
                Log.e("SaveImage", "Error al guardar imagen localmente: ${e.message}", e)
                Toast.makeText(requireContext(), "No se pudo guardar la imagen", Toast.LENGTH_SHORT).show()
            }
        }

        firestore.collection("usuarios").document(userId).set(updateData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error al guardar perfil", Toast.LENGTH_SHORT).show()
                Log.e("SaveProfile", "Error Firestore: ${e.message}", e)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}