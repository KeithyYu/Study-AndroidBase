package com.wanggk.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wanggk.paging.room.Student
import com.wanggk.paging.room.StudentDao
import com.wanggk.paging.room.StudentDatabase
import com.wanggk.paging.rv.MyAdapter
import com.wanggk.paging.view.MyItemTouchHelper
import com.wanggk.paging.viewmodel.StudentViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

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

//        GlobalScope.launch(Dispatchers.IO) {
//            for (index in 0 until 100) {
//                val student = Student("Student")
//                delay(100)
//                student.mTime = System.currentTimeMillis()
//                mViewModel.insertStudent(mStudentDao, student)
//            }
//        }
//        Log.d(TAG, "WDY onCreate load data end")

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

        //添加拖拽事件
        val mItemTouchHelper = ItemTouchHelper(MyItemTouchHelper(mAdapter, mStudentDao))
        mItemTouchHelper.attachToRecyclerView(mRv)

        mStudentDao.getStudent3().observe(this) {
            Log.d(TAG, "WDY observe data change")
//            mAdapter.refresh()
        }

        Log.d(TAG, "WDY onCreate end")
    }

    private fun swapStudent(from: Int, to: Int) {
        val student = mAdapter.peek(from)
        student?.let {
            val time = if (from > to) {
                mAdapter.peek(to)?.mTime?.plus(1)
            } else if (from < to) {
                mAdapter.peek(to)?.mTime?.minus(1)
            } else {
                mAdapter.peek(from)?.mTime
            }

            it.mTime = time!!
            Log.d(TAG, "swapStudent from = $from, to = $to, time = $time")
            GlobalScope.launch(Dispatchers.IO) {
                val rows = mStudentDao.updateStudent(it)
                Log.d(TAG, "swapStudent update student rows : $rows")

                withContext(Dispatchers.Main) {
                    // 受影响的itemd都刷新下
                    mAdapter.notifyItemRangeChanged(
                        Math.min(from, to),
                        Math.abs(from - to) + 1
                    )
                }
            }
        }
    }
}