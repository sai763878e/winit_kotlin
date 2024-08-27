package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.dataobject.SettingsDO

class MasterDA {

    fun getSettings(key: String): SettingsDO {
        synchronized(MyApplication.APP_DB_LOCK) {
            val settingsDO = SettingsDO
            val trimmedKey = key.trim()

            // Use the 'use' function for automatic resource management
            try {
                val mDatabase = DatabaseHelper.openDataBase()
                mDatabase.use { db ->
                    val cursor = db.rawQuery("SELECT SettingName, SettingValue FROM tblSettings WHERE TRIM(SettingName) = ?", arrayOf(trimmedKey))
                    cursor.use { c ->
                        if (c.moveToFirst()) {
                            settingsDO.SettingName = c.getString(0)
                            settingsDO.SettingValue = c.getString(1)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace() // Consider using a logging framework
            }

            return settingsDO
        }
    }
}