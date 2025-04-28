package com.example.appmovil.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmovil.data.SoftwareProduct
import com.google.firebase.firestore.FirebaseFirestore

class MarketplaceViewModel : ViewModel() {
    private val _selectedSoftware = MutableLiveData<SoftwareProduct>()
    val selectedSoftware: LiveData<SoftwareProduct> = _selectedSoftware

    private val _softwareList = MutableLiveData<List<SoftwareProduct>>()
    val softwareList: LiveData<List<SoftwareProduct>> = _softwareList

    init {
        loadSoftwareProducts()
    }

    private fun loadSoftwareProducts() {
        val db = FirebaseFirestore.getInstance()
        db.collection("softwares") //colección firestore
            .get()
            .addOnSuccessListener { result ->
                val products = result.documents.map { document ->
                    SoftwareProduct(
                        id = document.id.hashCode(),  // O usa document.id si lo prefieres
                        name = document.getString("name") ?: "",
                        description = document.getString("description") ?: "",
                        price = document.getDouble("price") ?: 0.0,
                        developer = document.getString("developer") ?: "",
                        rating = (document.getDouble("rating") ?: 0.0).toFloat(),
                        downloads = (document.getLong("downloads") ?: 0).toInt()
                    )
                }
                _softwareList.value = products
            }
            .addOnFailureListener { exception ->
                // Manejo de errores en la lectura de los datos
                Log.e("MarketplaceViewModel", "Error fetching software products", exception)
            }
    }

    fun selectSoftware(id: Int) {
        _softwareList.value?.find { it.id == id }?.let {
            _selectedSoftware.value = it
        }
    }
}
