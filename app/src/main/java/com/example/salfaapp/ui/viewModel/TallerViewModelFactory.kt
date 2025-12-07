package com.example.salfaapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.salfaapp.domain.model.data.repository.TallerRepository
import com.example.salfaapp.network.repository.TallerRemoteRepository

class TallerViewModelFactory(
    private val localRepo: TallerRepository,
    private val remoteRepo: TallerRemoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TallerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TallerViewModel(localRepo, remoteRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}