package com.example.habittracker.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.model.RoomRepository
import com.example.habittracker.model.WorkResult
import com.example.habittracker.model.cases.Case
import com.example.habittracker.view.Consts.KEY_HABIT_ID
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CasesViewModel(
    private val repository: RoomRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private var currentHabitId: Long? = savedStateHandle[KEY_HABIT_ID]
    private lateinit var cases: Flow<WorkResult<List<Case>>>
    private val numLoadingItems = MutableStateFlow(0)

    init {
        if (currentHabitId != null) cases = repository.getCasesFlow(currentHabitId!!)
    }

    fun init(habitId: Long) {
        cases = repository.getCasesFlow(habitId)
        savedStateHandle[KEY_HABIT_ID] = habitId
        currentHabitId = habitId
    }



    val uiCasesState by lazy {
        combine(cases, numLoadingItems) { cases, loadingItems ->
            when (cases) {
                is WorkResult.SuccessResult -> CasesListUiState(
                    cases = cases.data.sortedByDescending { it.date },
                    isLoading = loadingItems > 0
                )

                is WorkResult.LoadingResult -> CasesListUiState(isLoading = true)
                is WorkResult.ErrorResult -> CasesListUiState(isError = true)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CasesListUiState(isLoading = true)
        )
    }

    fun updateCases(){
        cases = repository.getCasesFlow(currentHabitId!!)
    }

    fun updateComment(case: Case, newComment: String) {
        viewModelScope.launch {
            withLoading {
                repository.updateCommentCase(case.id, newComment)
            }
        }
    }


    fun deleteCase(case: Case) {
        viewModelScope.launch {
            withLoading {
                repository.deleteCase(case.id)
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



data class CasesListUiState(
    val cases: List<Case> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)