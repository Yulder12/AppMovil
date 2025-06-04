package com.example.appmovil.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.util.LinkifyCompat
import androidx.fragment.app.Fragment
import com.example.appmovil.R
import com.example.appmovil.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var nombre: String? = null
    private var descripcion: String? = null
    private var autor: String? = null
    private var version: String? = null
    private var linkContacto: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nombre = it.getString("nombre")
            descripcion = it.getString("descripcion")
            autor = it.getString("autor")
            version = it.getString("version")
            linkContacto = it.getString("linkContacto")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Setear datos
        binding.tvNombre.text = nombre ?: "Sin nombre"
        binding.tvDescripcion.text = descripcion ?: "Sin descripción"
        binding.tvAutor.text = "Autor: ${autor ?: "Desconocido"}"
        binding.tvVersion.text = "Versión: ${version ?: "N/A"}"

        // Mostrar link como texto clickeable
        if (!linkContacto.isNullOrEmpty()) {
            binding.tvLinkContacto.text = linkContacto
            // Permitir que el link sea clickable
            binding.tvLinkContacto.movementMethod = LinkMovementMethod.getInstance()

            // También abrir link al clickear el texto
            binding.tvLinkContacto.setOnClickListener {
                val url = linkContacto!!.takeIf { it.startsWith("http") } ?: "https://${linkContacto}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        } else {
            binding.tvLinkContacto.text = "Sin contacto"
            binding.tvLinkContacto.isClickable = false
        }

        // Botón compartir (opcional)
        binding.root.findViewById<View>(R.id.btnCompartir)?.setOnClickListener {
            shareSoftware()
        }
    }

    private fun shareSoftware() {
        val shareText = """
            Software: $nombre
            Versión: $version
            Autor: $autor
            Más info: $linkContacto
        """.trimIndent()

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Compartir software vía")
        startActivity(shareIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

