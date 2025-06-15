package com.example.appmovil.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appmovil.R
import com.example.appmovil.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val colorMap = mapOf(
        "Gris oscuro" to R.color.theme_dark_gray,
        "Azul marino" to R.color.theme_navy,
        "Verde oscuro" to R.color.theme_green,
        "Morado" to R.color.purple_700,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Cargar nombres en el Spinner
        val colorNames = colorMap.keys.toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, colorNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerColors.adapter = adapter

        // Navegar a editar perfil
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToEditProfileFragment())
        }

        // Aplicar el color seleccionado
        binding.btnApplyColor.setOnClickListener {
            val selectedName = binding.spinnerColors.selectedItem.toString()
            val colorResId = colorMap[selectedName] ?: R.color.theme_dark_gray

            val prefs = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            prefs.edit().putInt("backgroundColor", colorResId).apply()

            requireActivity().recreate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

