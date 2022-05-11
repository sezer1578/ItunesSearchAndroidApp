package com.ozaltun.myitunesapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences =
            getSharedPreferences("com.ozaltun.myitunesapp.", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.remove("category")?.apply()
        sharedPreferences?.edit()?.remove("searchTerm")?.apply()
    }
}