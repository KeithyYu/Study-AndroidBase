package com.wanggk.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wanggk.paging.room.Student
import com.wanggk.paging.room.StudentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class StudentPagingSource(var studentDao: StudentDao) : PagingSource<Int, Student>() {
    companion object {
        private val TAG = StudentPagingSource::class.java.simpleName
    }

    override fun getRefreshKey(state: PagingState<Int, Student>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Student> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val page = params.key ?: 1
                val pageSize = params.loadSize
                val offset = (page - 1) * pageSize
                val studentList = studentDao.getStudent(offset, pageSize)
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (studentList.isNotEmpty()) page + 1 else null
                Log.d(
                    TAG,
                    "load page : $page, pageSize : $pageSize, offset, $offset, list size : ${studentList.size}, prevKey : $prevKey, nextKey : $nextKey"
                )
                LoadResult.Page(studentList, prevKey, nextKey)
            } catch (e: IOException) {
                e.printStackTrace()
                LoadResult.Error(e)
            }
        }
    }
}