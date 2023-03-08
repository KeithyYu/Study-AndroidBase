package com.wanggk.paging.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {
    companion object {
        private var mInstance: StudentDatabase? = null
        fun getStudentDataBase(context: Context): StudentDatabase {
            if (mInstance == null) {
                synchronized(this) {
                    if (mInstance == null) {
                        mInstance =
                            Room.databaseBuilder(context, StudentDatabase::class.java, "page.db")
                                .build()
                    }
                }
            }

            return mInstance!!
        }
    }

    abstract fun getStudentDao(): StudentDao
}