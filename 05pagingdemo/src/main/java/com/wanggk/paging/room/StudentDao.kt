package com.wanggk.paging.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(vararg student: Student)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateStudent(vararg student: Student) : Int

    @Query("DELETE FROM student")
    fun deleteAllStudent()

    @Query("SELECT * FROM student ORDER BY id")
    fun getStudentLive() : DataSource.Factory<Int, Student>

    @Query("SELECT * FROM student ORDER BY time desc")
    fun getStudent() : PagingSource<Int, Student>

    @Query("SELECT * FROM student ORDER BY time desc")
    fun getStudent3() : LiveData<Student>

    @Query("SELECT * FROM student ORDER BY time desc limit :limit offset :offset")
    fun getStudent(offset: Int, limit: Int): List<Student>
}