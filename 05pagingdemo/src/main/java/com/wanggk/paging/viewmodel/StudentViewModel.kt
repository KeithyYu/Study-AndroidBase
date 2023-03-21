package com.wanggk.paging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wanggk.paging.repository.PagingRepository
import com.wanggk.paging.room.Student
import com.wanggk.paging.room.StudentDao
import kotlinx.coroutines.flow.Flow


class StudentViewModel : ViewModel() {
    fun insertStudent(userDao: StudentDao, student: Student) {
        PagingRepository.insertStudent(userDao, student)
    }

    fun updateStudent(userDao: StudentDao, student: Student) {
        PagingRepository.updateStudent(userDao, student)
    }

    fun getPagingData(userDao: StudentDao): Flow<PagingData<Student>> {
        return PagingRepository.getPagingData(userDao).cachedIn(viewModelScope)
    }

    fun getPagingData2(userDao: StudentDao): Flow<PagingData<Student>> {
        return PagingRepository.getPagingData2(userDao).cachedIn(viewModelScope)
    }
}