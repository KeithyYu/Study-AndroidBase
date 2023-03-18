package com.wanggk.paging.room

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(vararg student: Student)

    @Query("DELETE FROM student")
    fun deleteAllStudent()

    @Query("SELECT * FROM student ORDER BY id")
    fun getStudentLive() : DataSource.Factory<Int, Student>

    @Query("SELECT * FROM student ORDER BY time desc")
    fun getStudent() : PagingSource<Int, Student>
}