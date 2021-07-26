package com.sberkozd.lettervault.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberkozd.lettervault.ui.grid.GridRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(val repository: AddRepository) : ViewModel() {


    init {
        viewModelScope.launch {

        }
    }
}
