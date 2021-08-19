package com.sberkozd.lettervault.ui.add

import android.os.Build
import android.text.Editable
import android.util.Log
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


    fun timeRemainingToUnlockNote() {

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

//            val l = LocalDate.parse(unlockTime, DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm"))
//
//            val c = LocalDate.parse(
//                getDateTimeCalendar().time.time.toString(),
//                DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm")
//            )

            difference = (unlockTime.toLong() - getDateTimeCalendar().time.time) / 1000

            Log.e("Michy", difference.toString())

//            notificationHelper.createSampleDataNotification(context,1,true)


//            val currentTimeUnix: Long = c.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond

//            val unlockTimeUnix: Long  = l.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond

//            val timeLeftToUnlock: Long = unlockTimeUnix - currentTimeUnix


        } else { // if no time selected let the current time set as the unlock time
            unlockTime = ""
        }

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

    sealed class Event {
        data class noteAdded(val noteId: Int, val difference: Long?) : Event()
    }

}
