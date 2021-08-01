package com.sberkozd.lettervault.ui.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sberkozd.lettervault.R
import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.databinding.FragmentAddBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private val addViewModel: AddViewModel by viewModels()

    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val noteTVEditText = binding.noteTV as EditText
        val noteTitleTVeditText = binding.noteTitleTV as EditText

        noteTVEditText.inputType = InputType.TYPE_CLASS_TEXT
        noteTitleTVeditText.inputType = InputType.TYPE_CLASS_TEXT

        return when (item.itemId) {
            R.id.add_menu_item_tick -> {
                addViewModel.onSaveMenuItemClicked(noteTitleTVeditText.text,noteTVEditText.text)
                findNavController().navigate(AddFragmentDirections.actionAddFragmentToHomeFragment())
                Toast.makeText(requireContext(), "${noteTVEditText.text}", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.add_menu_item_time -> {
                addViewModel.timeMenuItemClicked()

                pickDate()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


    private fun pickDate() {
        addViewModel.getDateTimeCalendar()

        DatePickerDialog(
            requireContext(),
            this,
            addViewModel.year,
            addViewModel.month,
            addViewModel.day
        ).show()
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        //addViewModel.onDateSet(year,month,dayOfMonth)
        addViewModel.savedDay = dayOfMonth
        addViewModel.savedMonth = month
        addViewModel.savedYear = year

        TimePickerDialog(
            requireContext(),
            this,
            addViewModel.hour,
            addViewModel.minute,
            true
        ).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        addViewModel.savedHour = hourOfDay
        addViewModel.savedMinute = minute
    }
}
