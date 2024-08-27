package com.example.winit_kotlin.dataobject

import android.app.Activity
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.dataaccesslayer.OrderDA
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.utils.CalendarUtils


fun getEOTStatusForTodaysDate(tripDate: String): Boolean {
    synchronized(MyApplication.APP_DB_LOCK) {
        var sqLiteDatabase: SQLiteDatabase? = null
        var cursor: Cursor? = null
        var isEOTDone = false

        try {
            sqLiteDatabase = DatabaseHelper.openDataBase()
            val query = """
                SELECT COUNT(*) 
                FROM tblEOT 
                WHERE DATE(EOTTime) LIKE '%$tripDate%'
            """.trimIndent()

            cursor = sqLiteDatabase.rawQuery(query, null)

            if (cursor != null && cursor.moveToFirst() && cursor.getInt(0) > 0) {
                isEOTDone = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            sqLiteDatabase?.close()
        }

        return isEOTDone
    }

}
fun getEOTStatusLatest(): Boolean {
    synchronized(MyApplication.APP_DB_LOCK) {
        var slDatabase: SQLiteDatabase? = null
        var cursor: Cursor? = null
        var isEOTDone = false

        try {
            slDatabase = DatabaseHelper.openDataBase()
            val tripDate = OrderDA().getTripDateNew(slDatabase)

            val query = "SELECT count(*) FROM tblEOT WHERE DATE(EOTTime) = '$tripDate'"
            cursor = slDatabase.rawQuery(query, null)
            if (cursor != null && cursor.moveToFirst()) {
                if (cursor.getInt(0) > 0) {
                    isEOTDone = true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            slDatabase?.close()
        }

        return isEOTDone
    }
}

fun getEOTDateLatest(): String {
    synchronized(MyApplication.APP_DB_LOCK) {
        var slDatabase: SQLiteDatabase? = null
        var cursor: Cursor? = null
        var eotDate = ""

        try {
            slDatabase = DatabaseHelper.openDataBase()
            val tripDate = OrderDA().getTripDateNew(slDatabase)

            val query = "Select DATE(EOTTime) from tblEOT WHERE DATE(EOTTime) = '$tripDate'"
            cursor = slDatabase.rawQuery(query, null)

            if (cursor != null && cursor.moveToFirst()) {
                eotDate = cursor.getString(0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            slDatabase?.close()
        }

        return eotDate
    }
}


