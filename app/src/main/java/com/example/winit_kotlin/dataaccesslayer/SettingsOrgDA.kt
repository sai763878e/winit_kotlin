package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.dataobject.SettingsDO
import com.example.winit_kotlin.dataobject.SettingsDO.SettingId
import java.lang.Exception

class SettingsOrgDA {

    fun getSettingValueBySettingName(settingName : String) : Int{


        var database : SQLiteDatabase
        var value : Int = 0
        var cursor : Cursor? = null
        synchronized(MyApplication.APP_DB_LOCK){

                var query = "SELECT SettingValue FROM tblSettings WHERE TRIM(SettingName) = '"+ settingName + "'"
                DatabaseHelper.openDataBase().use { database ->
                    try {
                        cursor = database.rawQuery(query,null)

                        cursor?.let {
                            if (it.moveToFirst())
                                value = it.getInt(0)
                        }

                    }catch (e : Exception){
                        e.printStackTrace()
                    }finally {
                        cursor?.let {
                            if (!it.isClosed) it.close()
                        }
                        if (database.isOpen) database?.close()
                    }
                }

            return value
        }
    }
    fun getSettingValueStringByName(settingName: String): String {
        val query = "SELECT SettingValue FROM tblSettings WHERE TRIM(SettingName) = ?"
        var settingValue = ""

        var database: SQLiteDatabase? = null
        var cursor: Cursor? = null

        try {
            database = DatabaseHelper.openDataBase()
            cursor = database.rawQuery(query, arrayOf(settingName))

            if (cursor.moveToFirst()) {
                settingValue = cursor.getString(0) ?: ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            database?.close()
        }

        return settingValue
    }
    fun getSettingValueByName(settingId: String, mDatabase: SQLiteDatabase?): SettingsDO? {
        synchronized(MyApplication.APP_DB_LOCK) {
            var cursor: Cursor? = null
            var settingsDO: SettingsDO? = null

            try {
                val database = mDatabase?.takeIf { it.isOpen } ?: DatabaseHelper.openDataBase()

                val query = "SELECT * FROM tblSettings WHERE TRIM(SettingName) = '$settingId'"
                cursor = database.rawQuery(query, null)

                if (cursor.moveToFirst()) {
                    settingsDO = SettingsDO.apply {
                        SettingId = cursor.getInt(0)
                        SettingName = cursor.getString(1)
                        SettingValue = cursor.getString(2)
                        DataType = cursor.getString(3)
                        SalesOrgCode = cursor.getString(4)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }

            return settingsDO
        }
    }

    fun getSettingValue(settingName: String): Boolean {
        synchronized(MyApplication.APP_DB_LOCK) {
            var isValid = false
            var mDatabase: SQLiteDatabase? = null
            var cursor: Cursor? = null

            try {
                mDatabase = DatabaseHelper.openDataBase()

                val query = """
                SELECT SettingValue 
                FROM tblSettings 
                WHERE TRIM(SettingName) = '${settingName.trim()}' COLLATE NOCASE
            """.trimIndent()

                cursor = mDatabase.rawQuery(query, null)

                if (cursor.moveToFirst() && cursor.getInt(0) == 1) {
                    isValid = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                mDatabase?.close()
            }

            return isValid
        }
    }


}