package com.sberkozd.lettervault.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberkozd.lettervault.data.Note
import com.sberkozd.lettervault.ui.add.AddRepository
import com.sberkozd.lettervault.ui.grid.GridRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val repository: DetailRepository) : ViewModel() {

    var noteList: MutableLiveData<List<Note>> = MutableLiveData()

    init {
        viewModelScope.launch {
            noteList.value = repository.getAllNotes()
        }
    }

    fun updateNote(id: Int, note: Note) {
        viewModelScope.launch {

            noteList.value = repository.getAllNotes()
        }
    }
}

