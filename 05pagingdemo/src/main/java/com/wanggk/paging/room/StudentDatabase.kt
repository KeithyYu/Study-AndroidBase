package com.wanggk.paging.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Student::class], version = 2, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {
    companion object {
        private var mInstance: StudentDatabase? = null
        fun getStudentDataBase(context: Context): StudentDatabase {
            if (mInstance == null) {
                synchronized(this) {
                    if (mInstance == null) {
                        mInstance =
                            Room.databaseBuilder(context, StudentDatabase::class.java, "page.db")
                                .addMigrations(MIGRATION_1_2)
                                .build()
                    }
                }
            }

            return mInstance!!
        }

        val MIGRATION_1_2 : Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE student ADD COLUMN time TEXT"
                )
            }
        }
    }

    abstract fun getStudentDao(): StudentDao
}