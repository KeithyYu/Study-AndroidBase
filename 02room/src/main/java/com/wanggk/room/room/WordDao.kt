package com.wanggk.room.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWords(vararg word: Word): List<Long>

    @Query("SELECT * FROM word ORDER BY ID DESC")
    fun queryAllWord(): LiveData<List<Word>>

    @Update
    fun updateWord(vararg word: Word): Int

    @Delete
    fun deleteWord(vararg word: Word): Int

    @Query("DELETE FROM word")
    fun deleteAllWords(): Int
}