package com.example.appmovil.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmovil.MyApplication
import com.example.appmovil.databinding.FragmentSettingsBinding
import com.example.appmovil.util.ThemeManager

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var themeManager: ThemeManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener la instancia de ThemeManager
        themeManager = (requireActivity().application as MyApplication).themeManager

        // Configurar los botones de tema
        binding.btnThemeDark.setOnClickListener {
            themeManager.setBackgroundColor(Color.rgb(30, 30, 30)) // Dark theme
            themeManager.setThemeMode(ThemeManager.ThemeMode.DARK)
        }

        binding.btnThemeLight.setOnClickListener {
            themeManager.setBackgroundColor(Color.WHITE) // Light theme
            themeManager.setThemeMode(ThemeManager.ThemeMode.LIGHT)
        }

        binding.btnThemeBlue.setOnClickListener {
            themeManager.setBackgroundColor(Color.rgb(235, 245, 255)) // Light Blue theme
            themeManager.setThemeMode(ThemeManager.ThemeMode.BLUE)
        }

        binding.btnThemeGreen.setOnClickListener {
            themeManager.setBackgroundColor(Color.rgb(240, 255, 240)) // Light Green theme
            themeManager.setThemeMode(ThemeManager.ThemeMode.GREEN)
        }

        // Configurar opciones de visualización
        binding.switchCompactView.isChecked = themeManager.getCompactViewMode()
        binding.switchCompactView.setOnCheckedChangeListener { _, isChecked ->
            themeManager.setCompactViewMode(isChecked)
        }

        // Botón para volver a la pantalla principal
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}