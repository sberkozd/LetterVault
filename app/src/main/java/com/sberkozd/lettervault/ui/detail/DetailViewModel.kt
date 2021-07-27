package com.sberkozd.lettervault.ui.detail

import androidx.lifecycle.ViewModel
import com.sberkozd.lettervault.ui.add.AddRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val repository: AddRepository) : ViewModel() {


    init {

    }
}
