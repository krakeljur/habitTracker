package com.example.habittracker.model.cases

import com.example.habittracker.view.adapters.ItemList


data class Case (
    val id: Long,
    var comment: String,
    var date: Long,
    val habitId: Long
) : ItemList