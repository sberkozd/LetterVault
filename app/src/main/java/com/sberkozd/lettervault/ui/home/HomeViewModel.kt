package com.sberkozd.lettervault.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberkozd.lettervault.data.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: HomeRepository) : ViewModel() {

    var noteList: MutableLiveData<List<Note>> = MutableLiveData()

    init {
        viewModelScope.launch {
            noteList.value = repository.getAllNotes()
        }
    }


}

