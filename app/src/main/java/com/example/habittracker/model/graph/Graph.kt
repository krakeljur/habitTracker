package com.example.habittracker.model.graph

import com.example.habittracker.view.adapters.ItemList
import java.util.Date

data class Graph(
    val data: MutableMap<Date, Int>
) : ItemList