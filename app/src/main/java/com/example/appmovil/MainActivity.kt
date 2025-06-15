package com.example.appmovil

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.appmovil.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar primero para acceder a la raíz
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aplicar el color de fondo al layout raíz (main_layout)
        val prefs = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val colorResId = prefs.getInt("backgroundColor", R.color.theme_dark_gray)
        val actualColor = ContextCompat.getColor(this, colorResId)

        binding.root.setBackgroundColor(actualColor)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }
}
