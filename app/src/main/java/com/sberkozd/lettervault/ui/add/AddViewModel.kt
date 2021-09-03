package com.sberkozd.lettervault.ui.add

import android.os.Build
import android.text.Editable
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberkozd.lettervault.data.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.Calendar.*
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(val repository: AddRepository) : ViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()


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

    fun getDateTimeCalendar(): Calendar {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"))
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
        return cal
    }


    suspend fun addNote(note: Note): Int = withContext(Dispatchers.IO) {
        repository.addNote(note)
    }


    fun timeMenuItemClicked() {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveMenuItemClicked(title: Editable, description: Editable) {

        val unlockTime: String

        var difference: Long? = null

        if (savedYear != 0 && savedMinute != 0) {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"))
            calendar.set(DAY_OF_MONTH, savedDay)
            calendar.set(MONTH, savedMonth)
            calendar.set(YEAR, savedYear)
            calendar.set(HOUR_OF_DAY, savedHour)
            calendar.set(MINUTE, savedMinute)
            unlockTime = calendar.time.time.toString()

            difference = (unlockTime.toLong() - getDateTimeCalendar().time.time) / 1000


        } else { // if no time selected let the current time set as the unlock time
            unlockTime = ""
        }

        when {
            title.toString() + description.toString() == "" -> {
                viewModelScope.launch {
                    val noteIsEmpty = "You can not save an empty note!"
                    eventChannel.send(Event.showNoteEmptyToast(noteIsEmpty))
                }
            }
            title.toString() == "" -> {
                viewModelScope.launch() {
                    val titleIsEmpty = "You can not leave title blank!"
                    eventChannel.send(Event.showTitleEmptyToast(titleIsEmpty))
                }
            }
            description.toString() == "" -> {
                viewModelScope.launch() {
                    val descriptionIsEmpty = "You can not leave description blank!"
                    eventChannel.send(Event.showDescriptionEmptyToast(descriptionIsEmpty))
                }
            }
            else -> {
                val note = Note(
                    0, unlockTime,
                    description.toString(), title.toString(), if (unlockTime.isEmpty()) 0 else 1
                )

                viewModelScope.launch() {
                    val noteId = addNote(note)
                    withContext(Dispatchers.Main) {
                        eventChannel.send(Event.noteAdded(noteId, difference))
                    }
                }

            }
        }
    }


    sealed class Event {
        data class noteAdded(val noteId: Int, val difference: Long?) : Event()
        data class showTitleEmptyToast(val titleIsEmpty: String) : Event()
        data class showDescriptionEmptyToast(val descriptionIsEmpty: String) : Event()
        data class showNoteEmptyToast(val showNoteEmptyToast: String) : Event()

    }

}
