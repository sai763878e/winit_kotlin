package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper

class JourneyPlanDA {
    fun getStartDayForCurrentRunningJourney() : String{
        var startJourney : String = ""
        var cursor : Cursor? = null
        var database: SQLiteDatabase? = null
        synchronized(MyApplication.APP_DB_LOCK){

            var query = "SELECT StartTime FROM tblJourney" +
                    " WHERE EndTime like '" + AppConstants.DEFAULT_TIME_LOG + "%' AND OdometerReadingEnd='0'\n" +
                    " Order by StartTime DESC limit 1"

            DatabaseHelper.openDataBase().use { database ->
                try {
                    cursor = database.rawQuery(query,null)

                    cursor?.let {
                        if (it.moveToFirst())
                            if (it.getString(0) != null && it.getString(0)
                                    .contains("T")
                            ) startJourney =
                                it.getString(0).split("T".toRegex())
                                    .dropLastWhile { it.isEmpty() }
                                    .toTypedArray()[0]
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }finally {
                    cursor?.let {
                        if (!it.isClosed)it.close()
                    }
                    database?.close()
                }
            }
        }
        return startJourney;
    }
    fun getCurrentRunningTripDateOnly(): String {
        synchronized(MyApplication.APP_DB_LOCK) {
            var sqLiteDatabase: SQLiteDatabase? = null
            var data = ""
            var cursor: Cursor? = null

            try {
                val query = "SELECT DATE(StartTime) FROM tblJourney WHERE EndTime LIKE '${AppConstants.DEFAULT_TIME_LOG}%' ORDER BY StartTime DESC"
                sqLiteDatabase = DatabaseHelper.openDataBase()
                cursor = sqLiteDatabase.rawQuery(query, null)
                if (cursor.moveToFirst()) {
                    data = cursor.getString(0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                if (sqLiteDatabase?.isOpen == true) {
                    sqLiteDatabase.close()
                }
            }

            return data
        }
    }

}