package com.wanggk.words

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    private lateinit var mController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

 }

    override fun onStart() {
        super.onStart()
        mController = Navigation.findNavController(findViewById(R.id.frament))
        NavigationUI.setupActionBarWithNavController(this, mController)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mController.navigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        mController.navigateUp()
        return super.onSupportNavigateUp()
    }
}