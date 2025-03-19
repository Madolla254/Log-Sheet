package com.example.logsheet

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date

class NewLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_log)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db = DatabaseHelper(this)
        val context = this
        val btnInsert = findViewById<Button>(R.id.btnInsert)
        val etName = findViewById<EditText>(R.id.editTextName)
        val etLogNumber = findViewById<EditText>(R.id.editTextLogNumber)
        //val etAge = findViewById<EditText>(R.id.editTextAge)
        val etDate = findViewById<EditText>(R.id.editTextDate)
        val etStartTime = findViewById<EditText>(R.id.editTextStartTime)
        val etEndTime = findViewById<EditText>(R.id.editTextEndTime)
        val etLocation = findViewById<EditText>(R.id.editTextLocation)
        val etBattery = findViewById<EditText>(R.id.editTextBattery)
        val etTotalFlightTime = findViewById<EditText>(R.id.editTextTotalFlightTime)
        val etComments = findViewById<EditText>(R.id.editTextComments)
        val etPreflight = findViewById<CheckBox>(R.id.checkBoxPreflight)
        val etPackaging = findViewById<CheckBox>(R.id.checkBoxPackaging)
        val etHomeBase = findViewById<CheckBox>(R.id.checkBoxHomeBase)
        //val btnInsert=findViewById<Button>(R.id.btnInsert)
        //set initial values for date an time to be current date and time
        etDate.setText(SimpleDateFormat("dd/MM/yyyy").format(Date()))
        etStartTime.setText(SimpleDateFormat("HH:mm").format(Date()))
        //set event litener for when the end timer has been entered



                btnInsert.setOnClickListener {
                    if (etName.text.toString().isNotEmpty() &&
                        etLogNumber.text.toString().isNotEmpty() &&
                        etDate.text.toString().isNotEmpty() &&
                        etStartTime.text.toString().isNotEmpty() &&
                        etEndTime.text.toString().isNotEmpty() &&
                        etLocation.text.toString().isNotEmpty() &&
                        etBattery.text.toString().isNotEmpty() &&
                        etTotalFlightTime.text.toString().isNotEmpty() &&
                        etComments.text.toString().isNotEmpty()
                    ) {
                        var preflight: String
                        var packaging: String
                        var homeBase: String
                        if (etPreflight.isChecked) {
                            preflight = "true"
                        } else {
                            preflight = "false"
                        }
                        if (etHomeBase.isChecked) {
                            homeBase = "true"
                        } else {
                            homeBase = "false"
                        }
                        if (etPackaging.isChecked) {

                            packaging = "true"
                        } else {
                            packaging = "false"
                        }

                        val user = User(
                            etName.text.toString(),
                            etLogNumber.text.toString().toInt(),
                            etDate.text.toString(),
                            etStartTime.text.toString(),
                            etEndTime.text.toString(),
                            etLocation.text.toString(),
                            etBattery.text.toString(),
                            etTotalFlightTime.text.toString(),
                            etComments.text.toString(),
                            preflight,
                            packaging,
                            homeBase,
                            null
                        )
                        db.insertData(user)
                        Toast.makeText(context, "Data Inserted", Toast.LENGTH_SHORT).show()
                        etName.text.clear()
                        etLogNumber.text.clear()
                        val intent2 = Intent(context, MainActivity::class.java)
                        //intent2.putExtra("name", etName.text.toString())
                        startActivity(intent2)
                        // clearField()
                    } else {
                        Toast.makeText(
                            context,
                            "Please Fill All the Input fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            }
        }
