package com.example.salfaapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.salfaapp.domain.model.data.repository.TallerRepository
import com.example.salfaapp.domain.model.data.repository.VehiculoRepository

class DashboardViewModelFactory(
    private val vehiculoRepo: VehiculoRepository,
    private val tallerRepo: TallerRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(tallerRepo, vehiculoRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}