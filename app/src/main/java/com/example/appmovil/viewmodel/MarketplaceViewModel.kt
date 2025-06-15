package com.example.appmovil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmovil.data.Software
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class MarketplaceViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _softwares = MutableLiveData<List<Software>>()
    val softwares: LiveData<List<Software>> get() = _softwares

    private var listenerRegistration: ListenerRegistration? = null

    fun loadSoftwares() {
        listenerRegistration = db.collection("softwares")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    // Error al escuchar cambios
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val lista = snapshot.documents.map { doc ->
                        Software(
                            nombre = doc.getString("nombre") ?: "",
                            descripcion = doc.getString("descripcion") ?: "",
                            autor = doc.getString("autor") ?: "",
                            version = doc.getString("version") ?: "",
                            linkContacto = doc.getString("linkContacto") ?: ""
                        )
                    }
                    _softwares.value = lista
                } else {
                    _softwares.value = emptyList()
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }
}

