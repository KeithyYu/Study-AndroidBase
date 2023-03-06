package com.wanggk.room.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wanggk.room.R
import com.wanggk.room.room.Word
import com.wanggk.room.viewmodel.WordViewModel

class MyAdapter(var viewModel: WordViewModel) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var mDataList = mutableListOf<Word>()

    fun updateDataList(list: List<Word>) {
        mDataList.clear()
        mDataList.addAll(list)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mId: TextView

        var mEnglish: TextView

        var mChinese: TextView

        var mSwitch: Switch

        init {
            mId = itemView.findViewById(R.id.item_id)
            mEnglish = itemView.findViewById(R.id.item_tv_english)
            mChinese = itemView.findViewById(R.id.item_tv_chinese)
            mSwitch = itemView.findViewById(R.id.item_switch)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_adapter, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position < 0 || position > mDataList.size) {
            return
        }

        val word = mDataList[position]
        holder.mSwitch.setOnCheckedChangeListener(null)
        if (word.chineseVisible) {
            holder.mSwitch.isChecked = word.chineseVisible
            holder.mChinese.visibility = View.GONE
        } else {
            holder.mSwitch.isChecked = word.chineseVisible
            holder.mChinese.visibility = View.VISIBLE
        }

        holder.mId.text = position.toString()
        holder.mEnglish.text = word.english
        holder.mChinese.text = word.chinese

        holder.mSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            word.chineseVisible = isChecked

            if (isChecked) {
                holder.mChinese.visibility = View.GONE
            } else {
                holder.mChinese.visibility = View.VISIBLE
            }

            viewModel.updateWords(holder.itemView.context, word)
        }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }
}