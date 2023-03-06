package com.wanggk.words.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wanggk.words.repository.WordRepository
import com.wanggk.words.room.Word

class WordViewModel : ViewModel() {
    companion object{
        private val TAG = WordViewModel::class.java.simpleName
    }
    fun getLiveData(context: Context): LiveData<List<Word>> {
        return WordRepository.queryAllWord(context)
    }

    fun getQueryWordLiveData(
        context: Context,
        queryWord: String
    ) : LiveData<List<Word>> {
        val word = "%$queryWord%"
        Log.d(TAG, "getQueryWordLiveData : $word")
        return WordRepository.queryWord(context, word)
    }

    fun insertWords(context: Context, word: Word, callback: ((rows: Int) -> Unit)? = null) {
        WordRepository.insertWords(context, word, callback)
    }

    fun deleteWords(context: Context, word: Word, callback: ((rows: Int) -> Unit)? = null) {
        WordRepository.deleteWords(context, word, callback)
    }

    fun updateWords(context: Context, word: Word, callback: ((rows: Int) -> Unit)? = null) {
        WordRepository.updateWord(context, word, callback)
    }

    fun deleteAllWords(context: Context, callback: ((rows: Int) -> Unit)? = null) {
        WordRepository.deleteAllWords(context, callback)
    }
}