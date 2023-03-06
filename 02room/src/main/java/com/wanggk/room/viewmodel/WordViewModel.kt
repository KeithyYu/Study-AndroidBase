package com.wanggk.room.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wanggk.room.repository.WordRepository
import com.wanggk.room.room.Word

class WordViewModel : ViewModel() {
    fun getLiveData(context: Context, callback: ((liveData: LiveData<List<Word>>) -> Unit)) {
        WordRepository.queryAllWord(context, callback)
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