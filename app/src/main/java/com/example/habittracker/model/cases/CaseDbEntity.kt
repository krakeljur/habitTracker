package com.example.habittracker.model.cases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.habittracker.model.habits.HabitDbEntity

@Entity(
    tableName = "cases",
    foreignKeys = [ForeignKey(
        entity = HabitDbEntity::class,
        parentColumns = ["id"],
        childColumns = ["habit_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)]
)
data class CaseDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var comment: String,
    var date: Long,
    @ColumnInfo(name = "habit_id", index = true) val habitId: Long
) {
    fun toCase(): Case = Case(
        id,
        comment,
        date,
        habitId
    )

    companion object {
        fun addCaseDb(case: Case) : CaseDbEntity = CaseDbEntity(
            0,
            case.comment,
            case.date,
            case.habitId
        )
    }
}