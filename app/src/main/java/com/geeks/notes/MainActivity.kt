package com.geeks.notes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        supportFragmentManager.setFragmentResultListener(
            "new_note", this,
        ) { _, result ->
            val note: Notes? = result.getSerializable("note") as Notes?
            note?.let { mainList.add(it) }
            Log.e("ololo", "$mainList")
        }
    }

    companion object {
        private val mainList = arrayListOf<Notes>()

        fun getList(): ArrayList<Notes> {
            return mainList
        }
    }
}