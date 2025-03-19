package com.example.logsheet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = "KotlinApp"
        val context = this
        val db = DatabaseHelper(context)
        //val editTextName = findViewById<EditText>(R.id.editTextName)
        //val  editTextAge = findViewById<EditText>(R.id.editTextAge)
        //val btnRead = findViewById<Button>(R.id.btnRead)
        //val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnNewLog = findViewById<Button>(R.id.btnNewLog)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        fun displayData() {
            val data = db.readData()

            if (data.isNotEmpty()) {
                val adapter = UserAdapter(data,this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)
            } else {
                Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show()
            }
        }
        displayData()

        btnNewLog.setOnClickListener {
            intent = Intent(context, NewLogActivity::class.java)
            startActivity(intent)


            }

//        fun clearField() {
//            editTextName.text.clear()
//            editTextAge.text.clear()
//        }
//        btnInsert.setOnClickListener {
//            if (editTextName.text.toString().isNotEmpty() &&
//                editTextAge.text.toString().isNotEmpty()
//            ) {
//                val user = User(editTextName.text.toString(), editTextAge.text.toString().toInt())
//                db.insertData(user)
//                clearField()
//            }
//            else {
//                Toast.makeText(context, "Please Fill All Data's", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//
//
//        btnRead.setOnClickListener {
//            displayData()
//
//            }
//
//
//    }


    }
}