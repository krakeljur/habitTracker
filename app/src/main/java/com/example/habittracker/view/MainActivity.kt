package com.example.habittracker.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.R
import com.example.habittracker.databinding.ActivityMainBinding
import com.example.habittracker.model.Repositories
import com.example.habittracker.view.screens.HabitsFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Repositories.init(applicationContext)


        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerView, HabitsFragment())
            .commit()
    }

}