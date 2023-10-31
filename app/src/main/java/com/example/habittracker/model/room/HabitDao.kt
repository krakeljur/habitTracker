package com.example.habittracker.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habittracker.model.habits.HabitDbEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface HabitDao {

    @Query("SELECT * FROM habits")
    fun getHabitsFlow(): Flow<List<HabitDbEntity>>

    @Query("SELECT * FROM habits WHERE id = :id")
    fun getHabitFlow(id: Long): Flow<HabitDbEntity?>

    @Query("SELECT * FROM habits WHERE id = :id")
    suspend fun findHabitById(id: Long) : HabitDbEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createHabit(habitDbEntity: HabitDbEntity)

    @Update(entity = HabitDbEntity::class)
    suspend fun updateHabitName(habitUpdateNameTuple: HabitUpdateNameTuple)

    @Delete(entity = HabitDbEntity::class)
    suspend fun deleteHabit(habitDbEntity: HabitDbEntity)
}