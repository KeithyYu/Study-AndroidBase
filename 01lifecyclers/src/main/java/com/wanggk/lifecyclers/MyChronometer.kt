package com.wanggk.lifecyclers

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.widget.Chronometer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class MyChronometer : Chronometer, LifecycleEventObserver {
    private var mElapsedTime : Long = 0L
    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                base = SystemClock.elapsedRealtime() - mElapsedTime
                start()
            }
            Lifecycle.Event.ON_PAUSE -> {
                mElapsedTime = SystemClock.elapsedRealtime() - base
                stop()
            }
            else -> {}
        }
    }
}