package com.example.salfaapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.salfaapp.domain.model.data.repository.VehiculoRepository
import com.example.salfaapp.network.repository.VehiculoRemoteRepository

class VehiculoViewModelFactory(
    private val localRepo: VehiculoRepository,
    private val remoteRepo: VehiculoRemoteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehiculoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VehiculoViewModel(localRepo, remoteRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}