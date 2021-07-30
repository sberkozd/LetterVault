package com.sberkozd.lettervault.ui.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sberkozd.lettervault.R
import com.sberkozd.lettervault.adapter.NoteAdapter
import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.databinding.FragmentAddBinding
import com.sberkozd.lettervault.db.LetterDao
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private val addViewModel: AddViewModel by viewModels()

    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!

    private val isClicked: Boolean? = null


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
        return when (item.itemId) {
            R.id.add_menu_item_tick -> {
                    val note = Note(0, "${addViewModel.savedDay} + ${addViewModel.savedMonth} +" +
                            " ${addViewModel.savedYear} + ${addViewModel.savedHour} + ${addViewModel.savedMinute}",
                        binding.noteTV.toString(), binding.noteTitleTV.toString(), 0)
                    addViewModel.addNote(note)
                    findNavController().navigate(AddFragmentDirections.actionAddFragmentToHomeFragment())
                    Toast.makeText(requireContext(), "Note created!", Toast.LENGTH_SHORT).show()
                    true
                }
            R.id.add_menu_item_time -> {
                DatePickerDialog(
                    requireContext(),
                    this,
                    addViewModel.year,
                    addViewModel.month,
                    addViewModel.day
                ).show()
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
        addViewModel.savedDay = dayOfMonth
        addViewModel.savedMonth = month
        addViewModel.savedYear = year

        addViewModel.getDateTimeCalendar()

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
