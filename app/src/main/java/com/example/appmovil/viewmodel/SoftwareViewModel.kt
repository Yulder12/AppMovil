package com.example.appmovil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appmovil.data.Software
import com.google.firebase.firestore.FirebaseFirestore

class SoftwareViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    fun agregarSoftware(software: Software, onResult: (Boolean) -> Unit) {
        val docRef = db.collection("softwares").document()
        val softwareConId = software.copy(id = docRef.id)

        docRef.set(softwareConId)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}
