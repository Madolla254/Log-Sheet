package com.example.logsheet

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
val DATABASENAME = "MY DATABASE"
val TABLENAME = "Logs"
val COL_NAME = "name"
val COL_LOGNUMBER = "lognumber"
val COL_DATE = "date"
val COL_STARTTIME = "starttime"
val COL_ENDTIME = "endtime"
val COL_LOCATION = "location"
val COL_BATTERY = "battery"
val COL_TOTALFLIGHTTIME = "totalflighttime"
val COL_COMMENTS = "comments"
val COL_PREFLIGHT = "preflight"
val COL_PACKAGING = "packaging"
val COL_HOMEBASE = "homebase"
val COL_ID = "id"

class DatabaseHelper (var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
1){
    override fun onCreate(p0: SQLiteDatabase?) {
        var db=p0
        //val createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME + " VARCHAR(256)," + COL_AGE + " INTEGER)"
        val createTable = "CREATE TABLE "+TABLENAME+" ("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_NAME+" VARCHAR(256),"+COL_LOGNUMBER+" INTEGER,"+COL_DATE+" VARCHAR(256),"+COL_STARTTIME+" VARCHAR(256),"+COL_ENDTIME+" VARCHAR(256),"+COL_LOCATION+" VARCHAR(256),"+COL_BATTERY+" VARCHAR(256),"+COL_TOTALFLIGHTTIME+" VARCHAR(256),"+COL_COMMENTS+" VARCHAR(256),"+COL_PREFLIGHT+" VARCHAR(256),"+COL_PACKAGING+" VARCHAR(256),"+COL_HOMEBASE+" VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        var db=p0
        val createTable = "CREATE TABLE "+TABLENAME+" ("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_NAME+" VARCHAR(256),"+COL_LOGNUMBER+" INTEGER,"+COL_DATE+" VARCHAR(256),"+COL_STARTTIME+" VARCHAR(256),"+COL_ENDTIME+" VARCHAR(256),"+COL_LOCATION+" VARCHAR(256),"+COL_BATTERY+" VARCHAR(256),"+COL_TOTALFLIGHTTIME+" VARCHAR(256),"+COL_COMMENTS+" VARCHAR(256),"+COL_PREFLIGHT+" VARCHAR(256),"+COL_PACKAGING+" VARCHAR(256),"+COL_HOMEBASE+" VARCHAR(256))"
        db?.execSQL(createTable)
    }
    fun insertData(user: User) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, user.name)
        contentValues.put(COL_LOGNUMBER, user.logNumber)
        contentValues.put(COL_DATE, user.date)
        contentValues.put(COL_STARTTIME, user.startTime)
        contentValues.put(COL_ENDTIME, user.endTime)
        contentValues.put(COL_LOCATION, user.location)
        contentValues.put(COL_BATTERY, user.battery)
        contentValues.put(COL_TOTALFLIGHTTIME, user.totalFlightTime)
        contentValues.put(COL_COMMENTS, user.comments)
        contentValues.put(COL_PREFLIGHT, user.preflight)
        contentValues.put(COL_PACKAGING, user.packaging)
        contentValues.put(COL_HOMEBASE, user.homeBase)


        //contentValues.put(COL_AGE, user.age)
        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }


    fun readData(): List<User> {
        val dataList = mutableListOf<User>()
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLENAME", null)


        try {
            if (cursor != null && cursor.moveToFirst()) {
                val nameINdex = cursor.getColumnIndex("name")
                val logNumberIndex = cursor.getColumnIndex("lognumber")
                val dateIndex = cursor.getColumnIndex("date")
                val startTimeIndex = cursor.getColumnIndex("starttime")
                val endTimeIndex = cursor.getColumnIndex("endtime")
                val locationIndex = cursor.getColumnIndex("location")
                val batteryIndex = cursor.getColumnIndex("battery")
                val totalFlightTimeIndex = cursor.getColumnIndex("totalflighttime")
                val commentsIndex = cursor.getColumnIndex("comments")
                val preflightIndex = cursor.getColumnIndex("preflight")
                val packagingIndex = cursor.getColumnIndex("packaging")
                val homeBaseIndex = cursor.getColumnIndex("homebase")
                val idIndex = cursor.getColumnIndex("id")




                if (cursor != null && cursor.count > 0) {
                    do {
                        var user = User("", 0, "", "", "", "", "", "", "", "true", "true", "true", 0)
                        user.id = if (!cursor.isNull(idIndex)) {
                            cursor.getString(idIndex).toInt()
                        } else {
                            0
                        }
                        user.name = if (!cursor.isNull(nameINdex)) {
                            cursor.getString(nameINdex).toString()
                        } else {
                            "empty"
                        }

                        user.logNumber = if (!cursor.isNull(logNumberIndex)) {
                            cursor.getInt(logNumberIndex)
                        } else {
                            0
                        }

                        user.date = if (!cursor.isNull(dateIndex)) {
                            cursor.getString(dateIndex).toString()
                        }else{
                            "empty"
                        }

                        user.endTime = if (!cursor.isNull(endTimeIndex)) {
                            cursor.getString(endTimeIndex).toString()
                        }else{
                            "empty"}

                        user.startTime = if (!cursor.isNull(startTimeIndex)) {
                            cursor.getString(startTimeIndex).toString()
                        }else{"empty"}

                        user.location = if (!cursor.isNull(locationIndex)) {
                            cursor.getString(locationIndex).toString()
                        }else{"empty"}
                        user.battery = if (!cursor.isNull(batteryIndex)) {
                            cursor.getString(batteryIndex).toString()
                        }else{"empty"}
                        user.totalFlightTime = if (!cursor.isNull(totalFlightTimeIndex)) {
                            cursor.getString(totalFlightTimeIndex).toString()

                        }else{"emty"}
                        user.comments = if (!cursor.isNull(commentsIndex)) {
                            cursor.getString(commentsIndex).toString()
                        }else{"empty"}
                        user.preflight = if (!cursor.isNull(preflightIndex)) {
                            cursor.getString(preflightIndex).toString()
                        }else{ "false"}
                        user.packaging = if (!cursor.isNull(packagingIndex)) {
                            cursor.getString(packagingIndex).toString()
                        }else{"false"}
                        user.homeBase = if (!cursor.isNull(homeBaseIndex)) {
                            cursor.getString(homeBaseIndex).toString()

                        }else{"false"}



                        dataList.add(user)
                    } while (cursor.moveToNext())
                } else {
                    Log.e("DatabaseHelper", "Column 'your_column_name' not found in the table.")
                }
            } else {
                Log.w("DatabaseHelper", "No data found in the table or cursor is null.")
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error reading data: ${e.message}")
        } finally {
            cursor?.close()
            db.close()
        }

        return dataList.toList()
    }

    fun readData(id: Int): List<User> {
        val dataList = mutableListOf<User>()
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLENAME WHERE id = $id", null)


        try {
            if (cursor != null && cursor.moveToFirst()) {
                val nameINdex = cursor.getColumnIndex("name")
                val logNumberIndex = cursor.getColumnIndex("lognumber")
                val dateIndex = cursor.getColumnIndex("date")
                val startTimeIndex = cursor.getColumnIndex("starttime")
                val endTimeIndex = cursor.getColumnIndex("endtime")
                val locationIndex = cursor.getColumnIndex("location")
                val batteryIndex = cursor.getColumnIndex("battery")
                val totalFlightTimeIndex = cursor.getColumnIndex("totalflighttime")
                val commentsIndex = cursor.getColumnIndex("comments")
                val preflightIndex = cursor.getColumnIndex("preflight")
                val packagingIndex = cursor.getColumnIndex("packaging")
                val homeBaseIndex = cursor.getColumnIndex("homebase")
                val idIndex = cursor.getColumnIndex("id")




                if (cursor != null && cursor.count > 0) {
                    do {
                        var user = User("", 0, "", "", "", "", "", "", "", "true", "true", "true", 0)
                        user.id = if (!cursor.isNull(idIndex)) {
                            cursor.getString(idIndex).toInt()
                        } else {
                            0
                        }
                        user.name = if (!cursor.isNull(nameINdex)) {
                            cursor.getString(nameINdex).toString()
                        } else {
                            "empty"
                        }

                        user.logNumber = if (!cursor.isNull(logNumberIndex)) {
                            cursor.getInt(logNumberIndex)
                        } else {
                            0
                        }

                        user.date = if (!cursor.isNull(dateIndex)) {
                            cursor.getString(dateIndex).toString()
                        }else{
                            "empty"
                        }

                        user.endTime = if (!cursor.isNull(endTimeIndex)) {
                            cursor.getString(endTimeIndex).toString()
                        }else{
                            "empty"}

                        user.startTime = if (!cursor.isNull(startTimeIndex)) {
                            cursor.getString(startTimeIndex).toString()
                        }else{"empty"}

                        user.location = if (!cursor.isNull(locationIndex)) {
                            cursor.getString(locationIndex).toString()
                        }else{"empty"}
                        user.battery = if (!cursor.isNull(batteryIndex)) {
                            cursor.getString(batteryIndex).toString()
                        }else{"empty"}
                        user.totalFlightTime = if (!cursor.isNull(totalFlightTimeIndex)) {
                            cursor.getString(totalFlightTimeIndex).toString()

                        }else{"emty"}
                        user.comments = if (!cursor.isNull(commentsIndex)) {
                            cursor.getString(commentsIndex).toString()
                        }else{"empty"}
                        user.preflight = if (!cursor.isNull(preflightIndex)) {
                            cursor.getString(preflightIndex).toString()
                        }else{ "false"}
                        user.packaging = if (!cursor.isNull(packagingIndex)) {
                            cursor.getString(packagingIndex).toString()
                        }else{"false"}
                        user.homeBase = if (!cursor.isNull(homeBaseIndex)) {
                            cursor.getString(homeBaseIndex).toString()

                        }else{"false"}



                        dataList.add(user)
                    } while (cursor.moveToNext())
                } else {
                    Log.e("DatabaseHelper", "Column 'your_column_name' not found in the table.")
                }
            } else {
                Log.w("DatabaseHelper", "No data found in the table or cursor is null.")
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error reading data: ${e.message}")
        } finally {
            cursor?.close()
            db.close()
        }

        return dataList.toList()
    }




}