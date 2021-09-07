package com.sberkozd.lettervault.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberkozd.lettervault.data.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository,
    state: SavedStateHandle
) : ViewModel() {

    var note: MutableLiveData<Note> = MutableLiveData()

    private val noteId = state.get<Int>("id")


    init {
        viewModelScope.launch {
            note.postValue(repository.getNoteByID(noteId!!)) //
        }
    }

    fun updateNote(title: String, desc: String) {
        viewModelScope.launch(Dispatchers.IO) {
            note.value?.apply {
                noteTitle = title
                noteContext = desc
                repository.updateNote(this)
            }
        }
    }


    fun deleteNote() {
        viewModelScope.launch(Dispatchers.IO) {
            note.value?.let {
                repository.deleteNote(it)
            }

        }
    }


}