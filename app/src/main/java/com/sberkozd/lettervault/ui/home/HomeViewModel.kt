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

    val noteList: MutableLiveData<List<Note>> = MutableLiveData()

    fun onCreate() {
        viewModelScope.launch {
            noteList.postValue(repository.getAllNotes())
        }
    }
}

