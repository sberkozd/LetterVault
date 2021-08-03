package com.sberkozd.lettervault.ui.detail

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import com.sberkozd.lettervault.data.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository,
    state: SavedStateHandle
) : ViewModel(){

    var note: MutableLiveData<Note> = MutableLiveData()

    private val noteId = state.get<Int>("id")



    init {
        viewModelScope.launch {
            note.value = repository.getNoteByID(noteId!!) //
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

}