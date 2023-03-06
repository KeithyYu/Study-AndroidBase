package com.wanggk.words.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
class Word {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    @ColumnInfo(name = "english")
    var english : String? = ""

    @ColumnInfo(name = "chinese")
    var chinese : String? = ""

    @ColumnInfo(name = "visible")
    var chineseVisible : Boolean = true
}