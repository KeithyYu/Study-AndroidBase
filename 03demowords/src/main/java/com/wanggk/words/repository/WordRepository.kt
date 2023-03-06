package com.wanggk.words.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.wanggk.words.room.Word
import com.wanggk.words.room.WordDao
import com.wanggk.words.room.WordsDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object WordRepository {
    fun insertWords(context: Context, word: Word, callback: ((rows: Int) -> Unit)? = null) {
        GlobalScope.launch(Dispatchers.IO) {
            val mWordDao: WordDao = WordsDataBase.getInstance(context).getWordDao()
            val list = mWordDao.insertWords(word)
            callback?.invoke(list.size)
        }
    }

    fun deleteWords(context: Context, word: Word, callback: ((rows: Int) -> Unit)? = null) {
        GlobalScope.launch(Dispatchers.IO) {
            val mWordDao: WordDao = WordsDataBase.getInstance(context).getWordDao()
            val rows = mWordDao.deleteWord(word)
            callback?.invoke(rows)
        }
    }

    fun updateWord(context: Context, word: Word, callback: ((rows: Int) -> Unit)? = null) {
        GlobalScope.launch(Dispatchers.IO) {
            val mWordDao: WordDao = WordsDataBase.getInstance(context).getWordDao()
            val rows = mWordDao.updateWord(word)
            callback?.invoke(rows)
        }
    }

    fun deleteAllWords(context: Context, callback: ((rows: Int) -> Unit)? = null) {
        GlobalScope.launch(Dispatchers.IO) {
            val mWordDao: WordDao = WordsDataBase.getInstance(context).getWordDao()
            val rows = mWordDao.deleteAllWords()
            callback?.invoke(rows)
        }
    }

    fun queryAllWord(
        context: Context
    ): LiveData<List<Word>> {
        val mWordDao: WordDao = WordsDataBase.getInstance(context).getWordDao()
        return mWordDao.queryAllWord()
    }

    fun queryWord(
        context: Context,
        queryWord: String
    ): LiveData<List<Word>> {
        val mWordDao: WordDao = WordsDataBase.getInstance(context).getWordDao()
        return mWordDao.queryWord(queryWord)
    }
}