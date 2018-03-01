package com.example.fredrik.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

const val URL = "http://runeberg.org/words/ss100.txt"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener<String> { s ->
            val dictionary = s.toLowerCase().split("\n")

            //"Simple" solution
            //val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dictionary)

            //"Advanced" solution
            val adapter = SuggestionAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dictionary)

            val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
            autoCompleteTextView.setAdapter(adapter)

        }, Response.ErrorListener { e ->
            println(e)
        })
        queue.add(stringRequest)
    }
}
