package com.example.salfaapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.salfaapp.domain.model.data.repository.TallerRepository

class TallerViewModelFactory(
    private val repo: TallerRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TallerViewModel::class.java)) {
            return TallerViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}