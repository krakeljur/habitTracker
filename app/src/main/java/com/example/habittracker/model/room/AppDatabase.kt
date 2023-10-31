package com.example.habittracker.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habittracker.model.cases.CaseDbEntity
import com.example.habittracker.model.habits.HabitDbEntity


@Database(
    version = 1,
    entities = [
        CaseDbEntity::class,
        HabitDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCasesDao(): CasesDao

    abstract fun getHabitsDao(): HabitDao

}