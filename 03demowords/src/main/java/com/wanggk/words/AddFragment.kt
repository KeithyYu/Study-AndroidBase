package com.wanggk.words

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.wanggk.words.viewmodel.WordViewModel
import com.wanggk.words.room.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddFragment : Fragment() {
    private lateinit var mEnglishET: EditText
    private lateinit var mChineseET: EditText
    private lateinit var mAddBT: Button

    private lateinit var mWordViewModel: WordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        mWordViewModel = ViewModelProvider(requireActivity()).get(WordViewModel::class.java)
        mEnglishET = view.findViewById(R.id.english_et)
        mChineseET = view.findViewById(R.id.chinese_et)
        mAddBT = view.findViewById(R.id.add_bt)
        mAddBT.isEnabled = false

        mEnglishET.requestFocus()
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEnglishET, 0)

        val editListener = object : TextWatcher {
            override fun beforeTextChanged(char: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(char: CharSequence, start: Int, before: Int, count: Int) {
                val englishStr = mEnglishET.text.toString().trim()
                val chineseStr = mChineseET.text.toString().trim()
                mAddBT.isEnabled = englishStr.isNotEmpty() && chineseStr.isNotEmpty()
            }

            override fun afterTextChanged(char: Editable) {
            }
        }

        mEnglishET.addTextChangedListener(editListener)
        mChineseET.addTextChangedListener(editListener)

        mAddBT.setOnClickListener { view ->
            val englishStr = mEnglishET.text.toString().trim()
            val chineseStr = mChineseET.text.toString().trim()
            val word = Word()
            word.chinese = chineseStr
            word.english = englishStr

            mWordViewModel.insertWords(requireContext(), word) { rows ->
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                GlobalScope.launch(Dispatchers.Main) {
                    if (rows != 0) {
                        Toast.makeText(requireContext(), "添加成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "添加失败", Toast.LENGTH_SHORT).show()
                    }

                    val controller = Navigation.findNavController(view)
                    controller.navigateUp()
                }
            }
        }
    }
}