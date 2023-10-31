package com.example.habittracker.model

import android.content.Context
import androidx.room.Room
import com.example.habittracker.model.room.AppDatabase
import kotlinx.coroutines.Dispatchers

object Repositories {

    private lateinit var applicationContext: Context

    private val ioDispatcher = Dispatchers.IO

    private val database: AppDatabase by lazy<AppDatabase> {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .build()
    }

    val roomRepository: RoomRepository by lazy {
        RoomRepository(database.getCasesDao(), database.getHabitsDao(), ioDispatcher)
    }

    fun init(context: Context){
        applicationContext = context
    }



}