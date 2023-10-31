package com.example.habittracker.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.habittracker.model.cases.CaseDbEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CasesDao {

    @Query("SELECT * FROM cases WHERE habit_id = :habitId")
    fun getCasesList(habitId: Long): Flow<List<CaseDbEntity>>

    @Query("SELECT * FROM cases WHERE id = :caseId")
    suspend fun findCaseById(caseId: Long): CaseDbEntity

    @Update(entity = CaseDbEntity::class)
    suspend fun updateCommentCase(caseUpdateCommentTuple: CaseUpdateCommentTuple)

    @Insert
    suspend fun createCase(caseDbEntity: CaseDbEntity)

    @Delete(entity = CaseDbEntity::class)
    suspend fun deleteCase(caseDbEntity: CaseDbEntity)

}