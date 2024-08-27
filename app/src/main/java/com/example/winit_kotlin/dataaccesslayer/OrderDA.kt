package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.winit_kotlin.databaseaccess.DatabaseHelper

class OrderDA {

    fun getTripDateNew(objSqliteDB: SQLiteDatabase?): String {
        var tripDate = ""
        var cursor: Cursor? = null
        var db = objSqliteDB

        try {
            if (db == null || !db.isOpen) {
                db = DatabaseHelper.openDataBase()
            }
            val query = "SELECT DATE(StartTime) FROM tblJourney ORDER BY StartTime DESC"
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                tripDate = cursor.getString(0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }

        return tripDate
    }

}