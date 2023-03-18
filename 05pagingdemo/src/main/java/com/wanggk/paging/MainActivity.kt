package com.wanggk.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wanggk.paging.room.Student
import com.wanggk.paging.room.StudentDao
import com.wanggk.paging.room.StudentDatabase
import com.wanggk.paging.rv.MyAdapter
import com.wanggk.paging.viewmodel.StudentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var mRv: RecyclerView

    private lateinit var mAdapter: MyAdapter

    private lateinit var mViewModel: StudentViewModel

    private lateinit var mStudentDao: StudentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "WDY onCreate enter")

        mViewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        mStudentDao = StudentDatabase.getStudentDataBase(this@MainActivity).getStudentDao()

        GlobalScope.launch(Dispatchers.IO) {
            for (index in 0 until 10) {
                val student = Student("Student")
                delay(100)
                student.mTime = System.currentTimeMillis()
                mViewModel.insertStudent(mStudentDao, student)
            }
        }
        Log.d(TAG, "WDY onCreate load data end")

        mRv = findViewById(R.id.rv)
        mRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mAdapter = MyAdapter()
        mRv.adapter = mAdapter

        lifecycleScope.launch {
            mViewModel.getPagingData(mStudentDao).collect { students ->
                Log.d(TAG, "WDY getPagingData collect")
                mAdapter.submitData(students)
            }
        }
        Log.d(TAG, "WDY onCreate end")
    }
}