package com.wanggk.words

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.wanggk.words.room.Word
import com.wanggk.words.viewmodel.WordViewModel
import com.wanggk.words.rv.MyAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }

    private lateinit var mViewModel: WordViewModel

    private lateinit var mRv: RecyclerView

    private lateinit var mQueryWordLiveData: LiveData<List<Word>>

    private var mAllWords = listOf<Word>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        setHasOptionsMenu(true)
        mViewModel = ViewModelProvider(requireActivity()).get(WordViewModel::class.java)
        mRv = view.findViewById(R.id.recyclerView)
        mRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        mRv.adapter = MyAdapter(mViewModel)
        mQueryWordLiveData = mViewModel.getQueryWordLiveData(requireContext(), "")
        mQueryWordLiveData.observe(viewLifecycleOwner) { list ->
            if (list.size != mRv.adapter?.itemCount) {
                Log.d(TAG, "initView list size : ${list.size}")
                mAllWords = list
                (mRv.adapter as MyAdapter).submitList(list)
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val word = mAllWords[viewHolder.adapterPosition]
                mViewModel.deleteWords(requireContext(), word) { rows ->
                    GlobalScope.launch(Dispatchers.Main) {
                        if (rows != 0) {
                            Snackbar.make(
                                requireActivity().findViewById(R.id.frameLayout),
                                "删除成功",
                                Snackbar.LENGTH_SHORT
                            ).setAction("撤销") {
                                mViewModel.insertWords(requireContext(), word)
                            }
                                .show()
                        } else {
                            Toast.makeText(requireContext(), "删除失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }).attachToRecyclerView(mRv)

        val mFloatActionButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        mFloatActionButton.setOnClickListener {
            val controller = Navigation.findNavController(it)
            controller.navigate(R.id.action_homeFragment_to_addFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
        val search = (menu.findItem(R.id.app_bar_search).actionView as SearchView)
        search.maxWidth = 750
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                var queryWord = newText.trim()
                Log.d(TAG, "onQueryTextChange queryWord : $queryWord")
                mQueryWordLiveData.removeObservers(viewLifecycleOwner)
                if (queryWord.isEmpty()) {
                    queryWord = ""
                }
                mQueryWordLiveData = mViewModel.getQueryWordLiveData(requireContext(), queryWord)
                mQueryWordLiveData.observe(viewLifecycleOwner) { list ->
                    queryWord = ""
                    if (list.size != mRv.adapter?.itemCount) {
                        Log.d(TAG, "onQueryTextChange list size : ${list.size}")
                        mAllWords = list
                        (mRv.adapter as MyAdapter).submitList(list)
                    }
                }
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear_data -> {
                mViewModel.deleteAllWords(requireContext()) { rows ->
                    GlobalScope.launch(Dispatchers.Main) {
                        if (rows != 0) {
                            Toast.makeText(requireContext(), "清除成功", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "清除失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}