package com.example.habittracker.viewmodel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.habittracker.model.Repositories

class MainViewModelFactory(private val repositories: Repositories, owner: SavedStateRegistryOwner) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return when (modelClass) {
            HabitViewModel::class.java -> HabitViewModel(repositories.roomRepository) as T
            CasesViewModel::class.java -> CasesViewModel(repositories.roomRepository, handle) as T
            AddCaseViewModel::class.java -> AddCaseViewModel(repositories.roomRepository, handle) as T
            else -> throw IllegalStateException("I DONT KNOW THIS VIEWMODEL CLASS")
        }
    }

}