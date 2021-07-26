package com.sberkozd.lettervault.ui.home

import com.sberkozd.lettervault.db.LetterDao
import javax.inject.Inject

class HomeRepository @Inject constructor(private val letterDao: LetterDao) {

}