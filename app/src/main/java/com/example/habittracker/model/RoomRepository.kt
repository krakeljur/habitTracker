package com.example.habittracker.model


import com.example.habittracker.model.cases.Case
import com.example.habittracker.model.cases.CaseDbEntity
import com.example.habittracker.model.habits.Habit
import com.example.habittracker.model.habits.HabitDbEntity
import com.example.habittracker.model.room.CaseUpdateCommentTuple
import com.example.habittracker.model.room.CaseUpdateDateTuple
import com.example.habittracker.model.room.CasesDao
import com.example.habittracker.model.room.HabitDao
import com.example.habittracker.model.room.HabitUpdateNameTuple
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomRepository(
    private val casesDao: CasesDao,
    private val habitDao: HabitDao,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun createCase(case: Case) = withContext<Unit>(ioDispatcher){
        val entity = CaseDbEntity.addCaseDb(case)
        casesDao.createCase(entity)
    }

    fun getCasesFlow(habitId: Long): Flow<WorkResult<List<Case>>> {
        return casesDao.getCasesList(habitId).map {
            WorkResult.SuccessResult(it.map { entity -> entity.toCase()})
        }
    }

    suspend fun updateCommentCase(id: Long, newComment: String) = withContext<Unit>(ioDispatcher){
        casesDao.updateCommentCase(CaseUpdateCommentTuple(id, newComment))
    }

    suspend fun deleteCase(id: Long) = withContext<Unit>(ioDispatcher){
        casesDao.deleteCase(casesDao.findCaseById(id))
    }

    suspend fun createHabit(habit: Habit) = withContext<Unit>(ioDispatcher){
        habitDao.createHabit(HabitDbEntity.addHabitDb(habit))
    }

    suspend fun refactorHabit(id: Long, newName: String) = withContext<Unit>(ioDispatcher){
        habitDao.updateHabitName(HabitUpdateNameTuple(id, newName))
    }

    fun getHabitsFlow(): Flow<WorkResult<List<Habit>>> {
        return habitDao.getHabitsFlow().map {
            WorkResult.SuccessResult(it.map { entity -> entity.toHabit() })
        }
    }


    suspend fun deleteHabit(id: Long) = withContext<Unit>(ioDispatcher) {
        habitDao.deleteHabit(habitDao.findHabitById(id))
    }

}