package com.sberkozd.lettervault.ui.add

import android.widget.TimePicker
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.ui.grid.GridRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
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
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    init {

    }

    fun savedDate(): String {
        return ("$savedDay/$savedMonth/$savedYear")
    }

    fun savedTime(): String{
        return ("$savedHour:$savedMinute")
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.addNote(note)
        }
    }
}
