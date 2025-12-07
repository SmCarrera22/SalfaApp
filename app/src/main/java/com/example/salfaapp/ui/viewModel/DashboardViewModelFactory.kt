package com.example.salfaapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.salfaapp.domain.model.data.repository.TallerRepository
import com.example.salfaapp.domain.model.data.repository.VehiculoRepository
import com.example.salfaapp.network.repository.TallerRemoteRepository
import com.example.salfaapp.network.repository.VehiculoRemoteRepository

class DashboardViewModelFactory(
    private val vehiculoLocalRepo: VehiculoRepository,
    private val tallerLocalRepo: TallerRepository,
    private val vehiculoRemoteRepo: VehiculoRemoteRepository,
    private val tallerRemoteRepo: TallerRemoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(
                tallerLocalRepo,
                vehiculoLocalRepo,
                tallerRemoteRepo,
                vehiculoRemoteRepo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}