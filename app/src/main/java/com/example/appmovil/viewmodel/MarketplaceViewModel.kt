package com.example.appmovil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmovil.data.SoftwareProduct

class MarketplaceViewModel : ViewModel() {

    private val _selectedSoftware = MutableLiveData<SoftwareProduct>()
    val selectedSoftware: LiveData<SoftwareProduct> = _selectedSoftware

    private val _softwareList = MutableLiveData<List<SoftwareProduct>>()
    val softwareList: LiveData<List<SoftwareProduct>> = _softwareList

    init {
        loadSoftwareProducts()
    }

    private fun loadSoftwareProducts() {
        // En una app real, esto vendría de una API o base de datos
        val softwareProducts = listOf(
            SoftwareProduct(
                id = 1,
                name = "CodeMaster IDE",
                description = "Un potente entorno de desarrollo integrado para programadores profesionales. Soporta más de 50 lenguajes de programación y ofrece funciones avanzadas de depuración y análisis de código.",
                price = 149.99,
                developer = "DevTech Solutions",
                rating = 4.8f,
                downloads = 50000
            ),
            SoftwareProduct(
                id = 2,
                name = "DesignPro Suite",
                description = "Suite completa de diseño gráfico para profesionales creativos. Incluye herramientas para edición de imágenes, diseño vectorial y maquetación de páginas.",
                price = 299.99,
                developer = "Creative Software Inc.",
                rating = 4.7f,
                downloads = 35000
            ),
            SoftwareProduct(
                id = 3,
                name = "DataAnalyzer Pro",
                description = "Herramienta avanzada para análisis de datos y visualización. Perfecto para científicos de datos y analistas de negocios.",
                price = 199.99,
                developer = "Data Insights LLC",
                rating = 4.5f,
                downloads = 28000
            ),
            SoftwareProduct(
                id = 4,
                name = "SecurityGuard",
                description = "Software de seguridad todo en uno: antivirus, firewall, protección contra ransomware y herramientas de privacidad integradas.",
                price = 79.99,
                developer = "CyberShield Security",
                rating = 4.6f,
                downloads = 120000
            ),
            SoftwareProduct(
                id = 5,
                name = "CloudBackup Unlimited",
                description = "Solución de respaldo en la nube con almacenamiento ilimitado para empresas y profesionales. Sincronización automática y acceso multiplataforma.",
                price = 59.99,
                developer = "CloudSafe Technologies",
                rating = 4.4f,
                downloads = 85000
            )
        )

        _softwareList.value = softwareProducts
    }

    fun selectSoftware(id: Int) {
        _softwareList.value?.find { it.id == id }?.let {
            _selectedSoftware.value = it
        }
    }
}