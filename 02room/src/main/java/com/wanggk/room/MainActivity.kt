package com.wanggk.room

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wanggk.room.room.Word
import com.wanggk.room.rv.MyAdapter
import com.wanggk.room.viewmodel.WordViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mViewModel: WordViewModel

    private lateinit var mDelete: Button
    private lateinit var mUpdate: Button
    private lateinit var mInsert: Button
    private lateinit var mClear: Button

    var english = arrayOf(
        "Hello",
        "World",
        "Android",
        "Google",
        "Studio",
        "Project",
        "Database",
        "Recycler",
        "View",
        "String",
        "Value",
        "Integer"
    )
    var chinese = arrayOf(
        "你好", "世界", "安卓系统", "谷歌公司", "工作室", "项目", "数据库", "回收站", "视图", "字符串", "价值", "整数类型"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProvider(this)[WordViewModel::class.java]
        mRecyclerView = findViewById(R.id.rv)
        mRecyclerView.adapter = MyAdapter(mViewModel)
        mRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        mDelete = findViewById(R.id.bt_delete)
        mClear = findViewById(R.id.bt_clear_all)
        mInsert = findViewById(R.id.bt_insert)
        mUpdate = findViewById(R.id.bt_update)

        initView()
    }

    private fun initView() {
        mViewModel.getLiveData(this) { liveData ->
            GlobalScope.launch(Dispatchers.Main) {
                liveData.observe(this@MainActivity) { list ->
                    if (list.size != mRecyclerView.adapter?.itemCount) {
                        Log.d("WDY", "list size : ${list.size}")
                        (mRecyclerView.adapter as MyAdapter).updateDataList(list)
                    }
                }
            }
        }

        mDelete.setOnClickListener {
            val word = Word()
            word.english = "Hi"
            word.chinese = "你好"
            word.id = 2
            mViewModel.deleteWords(this, word)
        }

        mInsert.setOnClickListener {
            for (index in english.indices) {
                val word = Word()
                word.english = english[index]
                word.chinese = chinese[index]
                mViewModel.insertWords(this, word)
            }
        }

        mUpdate.setOnClickListener {
            val word = Word()
            word.id = 2
            word.chinese = "安卓"
            mViewModel.updateWords(this, word)
        }

        mClear.setOnClickListener {
            mViewModel.deleteAllWords(this)
        }
    }
}