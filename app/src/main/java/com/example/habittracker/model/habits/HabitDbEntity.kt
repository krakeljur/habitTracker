package com.example.habittracker.model.habits

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "habits",
    indices = [Index(value = ["name"], unique = true)]
)
data class HabitDbEntity (
  @PrimaryKey(true) val id:Long,
  @ColumnInfo(collate = ColumnInfo.NOCASE) var name: String
) {
    fun toHabit(): Habit = Habit(id, name)

    companion object {
        fun addHabitDb(habit: Habit) : HabitDbEntity = HabitDbEntity(
            0,
            habit.name
        )
    }
}