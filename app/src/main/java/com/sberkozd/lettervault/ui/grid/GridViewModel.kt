package com.sberkozd.lettervault.ui.grid

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberkozd.lettervault.data.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GridViewModel @Inject constructor(val repository: GridRepository) : ViewModel() {

    val noteList: MutableLiveData<List<Note>> = MutableLiveData()

    fun onCreate() {
        viewModelScope.launch {
            noteList.value = repository.getAllNotes()
        }
    }
}
