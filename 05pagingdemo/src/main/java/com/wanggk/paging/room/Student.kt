package com.wanggk.paging.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(var name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "age")
    var age: String? = ""

    @ColumnInfo(name = "class")
    var mClass: String? = ""

    @ColumnInfo(name = "grade")
    var mGrade: String? = ""

    @ColumnInfo(name = "time")
    var mTime: Long = 0L
}