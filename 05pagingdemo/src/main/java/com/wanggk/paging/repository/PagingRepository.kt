package com.wanggk.paging.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wanggk.paging.room.Student
import com.wanggk.paging.room.StudentDao
import kotlinx.coroutines.flow.Flow

object PagingRepository {
    const val PAGE_SIZE = 20

    fun getPagingData(userDao: StudentDao): Flow<PagingData<Student>> {
        // PagingConfig的一个参数prefetchDistance，用于表示距离底部多少条数据开始预加载，
        // 设置0则表示滑到底部才加载。默认值为分页大小。
        // 若要让用户对加载无感，适当增加预取阈值即可。 比如调整到分页大小的5倍
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { userDao.getStudent() }).flow
    }

    fun insertStudent(userDao: StudentDao, student : Student) {
        userDao.insertStudent(student)
    }
}