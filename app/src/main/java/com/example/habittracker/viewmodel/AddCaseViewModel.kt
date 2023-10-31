package com.example.habittracker.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.model.RoomRepository
import com.example.habittracker.model.cases.Case
import com.example.habittracker.view.Consts
import kotlinx.coroutines.launch

class AddCaseViewModel(
    private val repository: RoomRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentHabitId: Long? = savedStateHandle[Consts.KEY_HABIT_ID]

    fun init(habitId: Long) {
        currentHabitId = habitId
        savedStateHandle[Consts.KEY_HABIT_ID] = habitId
    }

    fun createCase(case: Case) {
        viewModelScope.launch {
            repository.createCase(case)
        }
    }


}