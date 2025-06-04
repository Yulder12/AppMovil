package com.example.appmovil.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmovil.R
import com.example.appmovil.databinding.FragmentMarketplaceBinding
import com.example.appmovil.viewmodel.MarketplaceViewModel
import com.example.appmovil.data.Software
import com.example.appmovil.data.SoftwareAdapter

class MarketplaceFragment : Fragment() {

    private var _binding: FragmentMarketplaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MarketplaceViewModel by viewModels()
    private lateinit var adapter: SoftwareAdapter

    private var fullSoftwareList: List<Software> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketplaceBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SoftwareAdapter { software ->
            val action = MarketplaceFragmentDirections.actionMarketplaceFragmentToDetailsFragment(
                software.nombre,
                software.descripcion,
                software.autor,
                software.version,
                software.linkContacto
            )
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.softwares.observe(viewLifecycleOwner) { list ->
            fullSoftwareList = list
            adapter.submitList(list)
        }

        viewModel.loadSoftwares()

        // Acción del botón flotante para abrir el chatbot
        binding.fabChatbot.setOnClickListener {
            findNavController().navigate(R.id.chatbotFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_marketplace, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Buscar software..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { filterSoftwares(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filterSoftwares(it) }
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun filterSoftwares(query: String) {
        val filteredList = fullSoftwareList.filter { software ->
            software.nombre.contains(query, true) ||
                    software.descripcion.contains(query, true) ||
                    software.autor.contains(query, true)
        }
        adapter.submitList(filteredList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(MarketplaceFragmentDirections.actionMarketplaceFragmentToSettingsFragment())
                true
            }
            R.id.action_add -> {
                findNavController().navigate(MarketplaceFragmentDirections.actionMarketplaceFragmentToAddSoftwareFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


