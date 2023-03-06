package com.wanggk.room.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.wanggk.room.room.Word
import com.wanggk.room.room.WordDao
import com.wanggk.room.room.WordDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object WordRepository {
    fun insertWords(context: Context, word: Word, callback: ((rows: Int) -> Unit)? = null) {
        GlobalScope.launch(Dispatchers.IO) {
            val mWordDao: WordDao = WordDataBase.getInstance(context).getWordDao()
            val list = mWordDao.insertWords(word)
            callback?.invoke(list.size)
        }
    }

    fun deleteWords(context: Context, word: Word, callback: ((rows: Int) -> Unit)? = null) {
        GlobalScope.launch(Dispatchers.IO) {
            val mWordDao: WordDao = WordDataBase.getInstance(context).getWordDao()
            val rows = mWordDao.deleteWord(word)
            callback?.invoke(rows)
        }
    }

    fun updateWord(context: Context, word: Word, callback: ((rows: Int) -> Unit)? = null) {
        GlobalScope.launch(Dispatchers.IO) {
            val mWordDao: WordDao = WordDataBase.getInstance(context).getWordDao()
            val rows = mWordDao.updateWord(word)
            callback?.invoke(rows)
        }
    }

    fun deleteAllWords(context: Context, callback: ((rows: Int) -> Unit)? = null) {
        GlobalScope.launch(Dispatchers.IO) {
            val mWordDao: WordDao = WordDataBase.getInstance(context).getWordDao()
            val rows = mWordDao.deleteAllWords()
            callback?.invoke(rows)
        }
    }

    fun queryAllWord(
        context: Context,
        callback: ((liveData: LiveData<List<Word>>) -> Unit)? = null
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val mWordDao: WordDao = WordDataBase.getInstance(context).getWordDao()
            callback?.invoke(mWordDao.queryAllWord())
        }
    }
}