package com.sberkozd.lettervault.ui.add

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberkozd.lettervault.data.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import java.util.Calendar.*
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(val repository: AddRepository) : ViewModel() {

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

    fun getDateTimeCalendar() {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"))
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.addNote(note)
        }
    }

    fun timeMenuItemClicked() {

    }

    fun onSaveMenuItemClicked(title: Editable, description: Editable) {

        val unlockTime : String

        if(savedYear != 0 && savedMinute != 0) {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"))
            calendar.set(DAY_OF_MONTH, savedDay)
            calendar.set(MONTH, savedMonth)
            calendar.set(YEAR, savedYear)
            calendar.set(HOUR_OF_DAY, savedHour)
            calendar.set(MINUTE, savedMinute)
            unlockTime = calendar.time.time.toString()
        }else{
            unlockTime = ""
        }

        val note = Note(
            0,  unlockTime ,
            description.toString(), title.toString(), if(unlockTime.isEmpty()) 0 else 1)

        addNote(note)
    }
}
