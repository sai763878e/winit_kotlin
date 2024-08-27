package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.utils.CalendarUtils
import java.util.Vector

class StatusDA {
    fun getCompletedOptionsStatus(customerId: String, action: String): Vector<String> {
        synchronized(MyApplication.APP_DB_LOCK) {
            val vecStatusDO = Vector<String>()
            val date = CalendarUtils.getCurrentDate()
            val tempDate = CalendarUtils.getOrderPostDate()
            var mDatabase: SQLiteDatabase? = null
            var cursor: Cursor? = null

            try {
                mDatabase = DatabaseHelper.openDataBase()
                val query = """
                SELECT Type 
                FROM tblStatus 
                WHERE Customersite = '$customerId' 
                AND Action = '$action' 
                AND (Date = '$date' OR Date LIKE '$tempDate%')
            """.trimIndent()

                cursor = mDatabase.rawQuery(query, null)
                Log.d("SOS", "getCompletedOptionsStatus $query")

                if (cursor.moveToFirst()) {
                    do {
                        val task = cursor.getString(0)
                        vecStatusDO.add(task)
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                mDatabase?.close()
            }

            return vecStatusDO
        }
    }

}