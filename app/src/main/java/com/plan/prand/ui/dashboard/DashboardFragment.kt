package com.plan.prand.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.plan.prand.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        // Mostramos directamente el contenido de "Próximos" al entrar
        selectProximos()

        // Listeners para los botones
        binding.buttonProximos.setOnClickListener {
            selectProximos()
        }

        binding.buttonPasados.setOnClickListener {
            binding.buttonProximos.isSelected = false
            binding.buttonPasados.isSelected = true
            // Cargar contenido de "Pasados"
            binding.textDashboard.text = "No tienes servicios pasados."
        }

        dashboardViewModel.text.observe(viewLifecycleOwner) {
            // Este observer puede actualizar el contenido si lo deseas más dinámico
            // Pero si no lo usas, puedes eliminarlo
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun selectProximos() {
        binding.buttonProximos.isSelected = true
        binding.buttonPasados.isSelected = false
        // Cargar contenido de "Próximos"
        binding.textDashboard.text = "No tienes servicios próximos."
    }
}
