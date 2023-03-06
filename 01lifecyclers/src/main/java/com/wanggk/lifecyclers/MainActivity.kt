package com.wanggk.lifecyclers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var myChronometer : MyChronometer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myChronometer = findViewById(R.id.my_chronometer)
        lifecycle.addObserver(myChronometer)
    }
}