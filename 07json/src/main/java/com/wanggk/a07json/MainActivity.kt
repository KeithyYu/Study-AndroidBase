package com.wanggk.a07json

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val student = Student()
        student.name = "JACK"
        student.age = 20
        val score = Score()
        score.math = 100
        score.chinise = 90
        score.english = 98
        student.score = score

        val student2 =  Student()
        student2.name = "MACK"
        student2.age = 23
        val score2 = Score()
        score2.math = 98
        score2.english = 99
        score2.chinise = 89
        student2.score = score2

        val json = Gson()
        val studentJson = json.toJson(student)
        val jsonStudent = json.fromJson("{\"age\":20,\"name\":\"JACK\",\"score\":{\"chinise\":90,\"english\":98,\"math\":100}}", Student::class.java)
    }
}