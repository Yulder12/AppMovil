package com.example.appmovil.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.appmovil.databinding.FragmentChatbotBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ChatbotFragment : Fragment() {

    private var _binding: FragmentChatbotBinding? = null
    private val binding get() = _binding!!
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatbotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnEnviar.setOnClickListener {
            val mensajeUsuario = binding.etMensaje.text.toString().trim()
            if (mensajeUsuario.isNotEmpty()) {
                binding.tvConversacion.append("Tú: $mensajeUsuario\n")
                binding.etMensaje.text.clear()
                obtenerRespuestaIA(mensajeUsuario)
            }
        }
    }

    private fun obtenerRespuestaIA(mensaje: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val jsonBody = JSONObject()
            jsonBody.put("inputs", mensaje)

            val mediaType = "application/json".toMediaType()
            val body = jsonBody.toString().toRequestBody(mediaType)

            val request = Request.Builder()
                .url("https://api-inference.huggingface.co/models/microsoft/DialoGPT-medium")
                .post(body)
                .build()

            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (!response.isSuccessful) {
                    mostrarError("Error: ${response.code}")
                    return@launch
                }

                val jsonArray = JSONArray(responseBody)
                val textoRespuesta = jsonArray.getJSONObject(0).getString("generated_text")

                delay(1000) // simulamos un tiempo de "pensar"

                withContext(Dispatchers.Main) {
                    binding.tvConversacion.append("Chatbot: $textoRespuesta\n\n")
                }
            } catch (e: IOException) {
                mostrarError("Error de red: ${e.message}")
            }
        }
    }

    private suspend fun mostrarError(mensaje: String) = withContext(Dispatchers.Main) {
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
