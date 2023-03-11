package com.wanggk.parcelable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edName = findViewById<EditText>(R.id.edName)
        val edAge = findViewById<EditText>(R.id.edAge)
        val edMath = findViewById<EditText>(R.id.edMath)
        val edEnglish = findViewById<EditText>(R.id.edEnglish)
        val btn = findViewById<Button>(R.id.btnStart)

        val student = Student()
        edName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                student.name = s.toString()
            }
        })
        edAge.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                student.age = s.toString().toInt()
            }
        })

        val score = Score()
        edMath.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                score.math = s.toString().toInt()
            }
        })
        edEnglish.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                score.english = s.toString().toInt()
            }
        })
        student.score = score

        btn.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            val bundle = Bundle()
            bundle.putParcelable("student", student)
            intent.putExtra("data", bundle)
            startActivity(intent)
        }
    }
}