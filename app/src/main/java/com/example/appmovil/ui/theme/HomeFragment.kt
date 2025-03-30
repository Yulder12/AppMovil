package com.example.appmovil.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmovil.databinding.FragmentHomeBinding
import com.example.appmovil.data.SoftwareProduct
import com.example.appmovil.databinding.ItemSoftwareBinding
import com.example.appmovil.viewmodel.MarketplaceViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MarketplaceViewModel by activityViewModels()
    private lateinit var softwareAdapter: SoftwareAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        // Configurar el botón para navegar a la pantalla de configuración
        binding.btnGoToSettings.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
            )
        }
    }

    private fun setupRecyclerView() {
        softwareAdapter = SoftwareAdapter { softwareId ->
            viewModel.selectSoftware(softwareId)
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment()
            )
        }

        binding.recyclerViewSoftware.apply {
            adapter = softwareAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObservers() {
        viewModel.softwareList.observe(viewLifecycleOwner) { softwareList ->
            softwareAdapter.submitList(softwareList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Adapter para la lista de software
class SoftwareAdapter(private val onItemClick: (Int) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<SoftwareProduct, SoftwareAdapter.SoftwareViewHolder>(
        object : androidx.recyclerview.widget.DiffUtil.ItemCallback<SoftwareProduct>() {
            override fun areItemsTheSame(oldItem: SoftwareProduct, newItem: SoftwareProduct): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SoftwareProduct, newItem: SoftwareProduct): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoftwareViewHolder {
        val binding = ItemSoftwareBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SoftwareViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SoftwareViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SoftwareViewHolder(private val binding: ItemSoftwareBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val bindingAdapterPosition = 0
                val position = bindingAdapterPosition
                if (position != androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position).id)
                }
            }
        }

        fun bind(software: SoftwareProduct) {
            binding.textViewSoftwareName.text = software.name
            binding.textViewDeveloper.text = software.developer
            binding.textViewPrice.text = "$${software.price}"
            binding.ratingBar.rating = software.rating
        }
    }
}