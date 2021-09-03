package com.sberkozd.lettervault.ui.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.text.format.DateFormat
import android.view.*
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.sberkozd.lettervault.R
import com.sberkozd.lettervault.databinding.FragmentAddBinding
import com.sberkozd.lettervault.notification.NotifyWorker
import com.sberkozd.lettervault.observeInLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit


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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add, menu)

        addViewModel.eventsFlow.onEach {
            when (it) {
                is AddViewModel.Event.noteAdded -> {
                    it.difference?.let { diff ->
                        scheduleToast(diff, it.noteId)
                    }
                    findNavController().popBackStack()
                }
                is AddViewModel.Event.showNoteEmptyToast -> {
                    noteEmptyToast()
                }
                is AddViewModel.Event.showTitleEmptyToast -> {
                    emptyTitleToast()
                }
                is AddViewModel.Event.showDescriptionEmptyToast -> {
                    emptyDescriptionToast()
                }
            }
        }.observeInLifecycle(viewLifecycleOwner)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val noteTVEditText = binding.noteTV as EditText
        val noteTitleTVeditText = binding.noteTitleTV as EditText

        noteTVEditText.inputType = InputType.TYPE_CLASS_TEXT
        noteTitleTVeditText.inputType = InputType.TYPE_CLASS_TEXT

        return when (item.itemId) {
            R.id.add_menu_item_tick -> {
                addViewModel.onSaveMenuItemClicked(noteTitleTVeditText.text, noteTVEditText.text)
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

    private fun scheduleToast(difference: Long, noteId: Int) {
        //we set a tag to be able to cancel all work of this type if needed
        val workTag = "notificationWork"

        //store DBEventID to pass it to the PendingIntent and open the appropriate event page on notification click

        val inputData: Data = Data.Builder().putInt("noteId", noteId).build()

        // we then retrieve it inside the NotifyWorker with:
        // final int DBEventID = getInputData().getInt(DBEventIDTag, ERROR_VALUE);

        val notificationWork = OneTimeWorkRequest.Builder(NotifyWorker::class.java)
            .setInitialDelay(difference, TimeUnit.SECONDS)
            .setInputData(inputData)
            .addTag(workTag)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(notificationWork);

    }

    private fun emptyTitleToast(){
        Toast.makeText(requireContext(), "You can not leave title blank!", Toast.LENGTH_SHORT).show()
    }

    private fun noteEmptyToast(){
        Toast.makeText(requireContext(), "You can not save an empty note!", Toast.LENGTH_SHORT).show()
    }

    private fun emptyDescriptionToast(){
        Toast.makeText(requireContext(), "You can not leave description blank!", Toast.LENGTH_SHORT).show()
    }

    private fun pickDate() {
        addViewModel.getDateTimeCalendar()

        DatePickerDialog(
            requireContext(),
            R.style.DatePickerTheme,
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

        TimePickerDialog(
            requireContext(),
            R.style.TimePickerTheme,
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
