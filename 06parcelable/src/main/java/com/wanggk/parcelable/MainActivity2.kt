package com.wanggk.parcelable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val tvName = findViewById<TextView>(R.id.tvName)
        val tvAge = findViewById<TextView>(R.id.tvAge)
        val tvMath = findViewById<TextView>(R.id.tvMath)
        val tvEnglish = findViewById<TextView>(R.id.tvEnglish)
        val bundle = intent.getBundleExtra("data")
        val student = bundle?.getParcelable<Student>("student")
        tvName.text = student?.name.toString()
        tvAge.text = student?.age.toString()
        val score = student?.score
        tvMath.text = score?.math.toString()
        tvEnglish.text = score?.english.toString()
    }
}