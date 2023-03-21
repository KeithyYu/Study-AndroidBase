package com.wanggk.paging.view

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.wanggk.paging.room.StudentDao
import com.wanggk.paging.rv.MyAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections


class MyItemTouchHelper(val recyclerViewAdapter: MyAdapter, var studentDao: StudentDao) :
    ItemTouchHelper.Callback() {
    companion object {
        private val TAG = MyItemTouchHelper::class.java.simpleName
    }

    private var mStartPosition = -1

    private var mEndPosition = -1

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = 0
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        recyclerView.parent.requestDisallowInterceptTouchEvent(true)
        //得到当拖拽的viewHolder的Position
        val fromPosition = viewHolder.adapterPosition
        //拿到当前拖拽到的item的viewHolder
        val toPosition = target.adapterPosition

        if (mStartPosition == -1) {
            mStartPosition = fromPosition
        }

        mEndPosition = toPosition
        Log.d(TAG, "onMove fromPosition = $fromPosition, toPosition = $toPosition")
        recyclerViewAdapter.notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    /**
     * 长按选中Item时修改颜色
     *
     * @param viewHolder
     * @param actionState
     */
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            Log.d(TAG, "onMove mStartPosition = $mStartPosition, mEndPosition = $mEndPosition")
            swapStudent(mStartPosition, mEndPosition)
        }
    }

    private fun swapStudent(from: Int, to: Int) {
        val student = recyclerViewAdapter.peek(from)
        student?.let {
            val time = if (from > to) {
                recyclerViewAdapter.peek(to)?.mTime?.plus(1)
            } else if (from < to) {
                recyclerViewAdapter.peek(to)?.mTime?.minus(1)
            } else {
                recyclerViewAdapter.peek(from)?.mTime
            }

            it.mTime = time!!
            Log.d(TAG, "swapStudent from = $from, to = $to, time = $time")
            GlobalScope.launch(Dispatchers.IO) {
                val rows = studentDao.updateStudent(it)
                Log.d(TAG, "swapStudent update student rows : $rows")

//                withContext(Dispatchers.Main) {
//                    // 受影响的itemd都刷新下
//                    recyclerViewAdapter.notifyItemRangeChanged(
//                        Math.min(from, to),
//                        Math.abs(from - to) + 1
//                    )
//                }
            }
        }
    }


    /**
     * 重写拖拽不可用
     * @return
     */
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }
}