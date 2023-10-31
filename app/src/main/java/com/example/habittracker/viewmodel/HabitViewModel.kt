package com.example.habittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.model.RoomRepository
import com.example.habittracker.model.WorkResult
import com.example.habittracker.model.habits.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class HabitViewModel(
    private val repository: RoomRepository
) : ViewModel() {

    private var habits = repository.getHabitsFlow()

    private val numLoadingItems = MutableStateFlow(0)

    val uiHabitsState = combine(habits, numLoadingItems){ habits, loadingItems ->
        when (habits) {
            is WorkResult.SuccessResult -> HabitsListUiState(habits = habits.data, isLoading = loadingItems > 0)
            is WorkResult.LoadingResult -> HabitsListUiState(isLoading = true)
            is WorkResult.ErrorResult -> HabitsListUiState(isError = true)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HabitsListUiState(isLoading = true)
    )

    fun updateHabits() {
        habits = repository.getHabitsFlow()
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            withLoading {
                repository.deleteHabit(habit.id)
            }
        }
    }


    fun renameHabit(id: Long, newName: String) {
        viewModelScope.launch {
            withLoading {
                repository.refactorHabit(id, newName)
            }
        }
    }


    fun addHabit(name: String) {
        viewModelScope.launch {
            withLoading {
                repository.createHabit(Habit(0, name))
            }
        }
    }



    private suspend fun withLoading(block: suspend () -> Unit ) {
        try {
            addLoadingElement()
            block()
        }
        finally {
            removeLoadingElement()
        }
    }

    private fun removeLoadingElement() = numLoadingItems.getAndUpdate { num -> num - 1 }

    private fun addLoadingElement() = numLoadingItems.getAndUpdate { num -> num + 1 }
}


data class HabitsListUiState(
    val habits: List<Habit> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)




