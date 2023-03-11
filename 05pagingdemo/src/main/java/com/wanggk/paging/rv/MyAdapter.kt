package com.wanggk.paging.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wanggk.paging.R
import com.wanggk.paging.room.Student

class MyAdapter : PagingDataAdapter<Student, MyAdapter.ViewHolder>(COMPARATOR) {
    companion object {
        //因为Paging 3在内部会使用DiffUtil来管理数据变化，所以这个COMPARATOR是必须的
        private val COMPARATOR = object : DiffUtil.ItemCallback<Student>() {
            override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mName : TextView
        init {
            mName = itemView.findViewById(R.id.item_name)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.mName.text = item?.name + item?.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false)
        return ViewHolder(view)
    }
}