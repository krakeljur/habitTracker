package com.example.habittracker.model.room

data class CaseUpdateCommentTuple(
    val id: Long,
    var comment: String
)

data class CaseUpdateDateTuple(
    val id: Long,
    var date: Long
)

data class HabitUpdateNameTuple(
    val id: Long,
    var name: String
)