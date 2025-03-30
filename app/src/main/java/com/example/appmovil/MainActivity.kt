package com.example.appmovil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.appmovil.databinding.ActivityMainBinding
import com.example.appmovil.util.ThemeManager
import com.example.appmovil.viewmodel.MainViewModel
import com.example.appmovil.viewmodel.MarketplaceViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: MarketplaceViewModel
    private lateinit var themeManager: ThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el ViewModel
        viewModel = ViewModelProvider(this)[MarketplaceViewModel::class.java]

        // Obtener la instancia de ThemeManager
        themeManager = (application as MyApplication).themeManager

        // Configurar el Navigation Controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Configurar la ActionBar con el NavController
        setupActionBarWithNavController(navController)

        // Configurar título de la ActionBar
        supportActionBar?.title = "SoftMarket"

        // Observar cambios en el color de fondo
        themeManager.backgroundColor.observe(this) { color ->
            binding.root.setBackgroundColor(color)
        }

        // Observar cambios en el modo de tema
        themeManager.themeMode.observe(this) { themeMode ->
            // Aquí se podrían aplicar otros cambios al tema global
            when (themeMode) {
                ThemeManager.ThemeMode.DARK -> {
                    // Aplicar cambios adicionales para tema oscuro
                }
                ThemeManager.ThemeMode.LIGHT -> {
                    // Aplicar cambios adicionales para tema claro
                }
                ThemeManager.ThemeMode.BLUE -> {
                    // Aplicar cambios adicionales para tema azul
                }
                ThemeManager.ThemeMode.GREEN -> {
                    // Aplicar cambios adicionales para tema verde
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}