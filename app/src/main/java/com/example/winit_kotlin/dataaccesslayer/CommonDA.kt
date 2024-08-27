package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper

class CommonDA {
    fun getCurrencyCode(userCode : String) : String{
        synchronized(MyApplication.APP_DB_LOCK){
            var cursor: Cursor? = null
            var database: SQLiteDatabase? = null
            var currencyCode = ""
            val query = "SELECT Category FROM tblUsers WHERE UserCode = ?"
            DatabaseHelper.openDataBase().use { database ->

                try {
                    cursor = database.rawQuery(query, arrayOf(userCode))


                    cursor?.let {
                        if (it.moveToFirst()){
                            currencyCode = it.getString(0)
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    cursor?.let {
                        if (!it.isClosed) it.close()
                    }
                    database?.close()
                }
            }
            return currencyCode
        }

    }

    fun updateTripDate(){
        synchronized(MyApplication.APP_DB_LOCK){

            DatabaseHelper.openDataBase().use { database ->
                try {
                    var query ="Update tblJourney SET EndTime = StartTime,Attribute1 = 'N' WHERE EndTime='"+ AppConstants.DEFAULT_TIME+"'"
                    database.compileStatement(query).use { strstm ->
                        strstm.execute()
                        strstm.close()
                    }
                }catch (e : Exception){

                }
            }
        }
    }
    fun getPrinterType(routeCode: String): String {
        synchronized(MyApplication.APP_DB_LOCK) {
            var cursor: Cursor? = null
            var database: SQLiteDatabase? = null
            var printerType = ""

            try {
                database = DatabaseHelper.openDataBase()
                cursor = database.rawQuery("SELECT WHCode FROM tblRoute WHERE Code = ?", arrayOf(routeCode))

                if (cursor.moveToFirst()) {
                    printerType = cursor.getString(0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.let {
                    if (!it.isClosed) {
                        it.close()
                    }
                }

                database?.close()
            }

            return printerType
        }
    }
    fun getItemMaxUOM(): HashMap<String, String> {
        synchronized(MyApplication.APP_DB_LOCK) {
            val hmItemMaxUOM = HashMap<String, String>()
            val query = "Select ItemCode, UOM, MAX(EAConversion) Conversion FROM tblUOMFactor GROUP BY ItemCode ORDER BY ItemCode"

            var objSqliteDB: SQLiteDatabase? = null
            var cursor: Cursor? = null

            try {
                objSqliteDB = DatabaseHelper.openDataBase()
                cursor = objSqliteDB.rawQuery(query, null)

                if (cursor.moveToFirst()) {
                    do {
                        val itemCode = cursor.getString(0)
                        if (!hmItemMaxUOM.containsKey(itemCode)) {
                            hmItemMaxUOM[itemCode] = cursor.getString(1)
                        }
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                objSqliteDB?.close()
            }

            return hmItemMaxUOM
        }
    }

}