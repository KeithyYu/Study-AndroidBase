package com.wanggk.words.rv

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wanggk.words.room.Word
import com.wanggk.words.viewmodel.WordViewModel
import com.wanggk.words.R

class MyAdapter(var viewModel: WordViewModel) :
    ListAdapter<Word, MyAdapter.MyViewHolder>(object : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.chinese.equals(newItem.chinese) && oldItem.english.equals(newItem.english)
        }
    }) {

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

        // 放在此处提高性能
        val holder = MyViewHolder(view)
        holder.itemView.setOnClickListener {
            val uri =
                Uri.parse("https://dict.youdao.com/result?word=${holder.mEnglish.text}&lang=en")
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(uri)
            holder.itemView.context.startActivity(intent)
        }

        holder.mSwitch.setOnCheckedChangeListener { _, isChecked ->
            val word = holder.itemView.getTag(R.id.view_holder_tag) as Word
            word.chineseVisible = isChecked

            if (isChecked) {
                holder.mChinese.visibility = View.GONE
            } else {
                holder.mChinese.visibility = View.VISIBLE
            }

            viewModel.updateWords(holder.itemView.context, word)
        }

        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val word = getItem(position)
        holder.itemView.setTag(R.id.view_holder_tag, word)
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
    }
}