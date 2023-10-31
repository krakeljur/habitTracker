package com.example.habittracker.view.screens

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.databinding.FragmentAddCaseBinding
import com.example.habittracker.model.Repositories
import com.example.habittracker.model.cases.Case
import com.example.habittracker.view.Consts.KEY_HABIT_ID
import com.example.habittracker.viewmodel.AddCaseViewModel
import com.example.habittracker.viewmodel.MainViewModelFactory
import java.util.Calendar
import java.util.GregorianCalendar

class AddCaseFragment: Fragment() {

    private lateinit var binding: FragmentAddCaseBinding
    private lateinit var viewModel: AddCaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, MainViewModelFactory(Repositories, this))[AddCaseViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCaseBinding.inflate(inflater, container,false)

        val calendar = Calendar.getInstance()
        val currentHours = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMin = calendar.get(Calendar.MINUTE)
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        var pickedDate: Long = calendar.timeInMillis

        if (currentMin in 0..9)
            binding.currentTime.text = "$currentHours:0$currentMin"
        else
            binding.currentTime.text = "$currentHours:$currentMin"

        with (binding) {
            cancelButton.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }
            calendarViem.setOnDateChangeListener { _, year, month, dayOfMonth ->
                pickedDate = GregorianCalendar(year, month, dayOfMonth).timeInMillis
            }
            changeTimeButton.setOnClickListener {
                openDialog(currentMin, currentHours)
            }
            saveButton.setOnClickListener {
                val comment = commentET.text.toString()
                val time = currentTime.text.toString().split(":")
                val date = pickedDate + time[0].toLong() * 60 * 60 * 1000 + time[1].toLong() * 60000
                viewModel.createCase(Case(
                    0,
                    comment,
                    date,
                    requireArguments().getLong(KEY_HABIT_ID)
                ))
                activity?.supportFragmentManager?.popBackStack()
            }
        }

        return binding.root
    }

    private fun openDialog(min: Int, hours: Int) {
        val timePickerDialog = TimePickerDialog(context,TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            if (minute in 0..9)
                binding.currentTime.text = "$hourOfDay:0$minute"
            else
                binding.currentTime.text = "$hourOfDay:$minute"
        }, hours, min, true)
        timePickerDialog.show()
    }



    companion object {
        fun newInstance(habitId: Long): AddCaseFragment {
            val args: Bundle = Bundle().apply {
                putLong(KEY_HABIT_ID, habitId)
            }
            val fragment = AddCaseFragment()
            fragment.arguments = args
            return fragment
        }
    }
}