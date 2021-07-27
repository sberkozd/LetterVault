package com.sberkozd.lettervault.ui.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sberkozd.lettervault.R
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import com.sberkozd.lettervault.databinding.FragmentAddBinding
import com.sberkozd.lettervault.ui.add.AddViewModel
import com.sberkozd.lettervault.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Time
import java.util.*

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    private val addViewModel: AddViewModel by viewModels()

    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeIcon.setOnClickListener {
            findNavController().navigate(AddFragmentDirections.actionAddFragmentToHomeFragment())
        }

        binding.confirmIcon.setOnClickListener {
            Toast.makeText(requireContext(),"Note Created!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(AddFragmentDirections.actionAddFragmentToHomeFragment())
        }

        binding.timeIcon.setOnClickListener{
            context?.let { it1 -> DatePickerDialog(it1, this, year, month, day).show() }
        }


    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate(){
        getDateTimeCalendar()

        DatePickerDialog(requireContext(), this, year, month, day).show()
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()

        TimePickerDialog(requireContext(),this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

    }
}
