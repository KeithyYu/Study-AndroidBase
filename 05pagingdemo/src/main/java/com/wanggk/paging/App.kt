package com.wanggk.paging

import android.app.Application
import kotlin.properties.Delegates

object App : Application(){
    lateinit var mContext: Application

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}