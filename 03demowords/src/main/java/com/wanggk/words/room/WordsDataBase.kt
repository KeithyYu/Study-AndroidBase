package com.wanggk.words.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordsDataBase : RoomDatabase() {
    abstract fun getWordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordsDataBase? = null

        fun getInstance(context: Context): WordsDataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordsDataBase::class.java,
                        "words"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE word ADD COLUMN visible INTEGER NOT NULL DEFAULT 1")
            }
        }
    }
}