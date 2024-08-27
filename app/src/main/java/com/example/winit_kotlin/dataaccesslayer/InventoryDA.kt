package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.dataobject.UOMConversionFactorDO
import com.example.winit_kotlin.utils.CalendarUtils
import com.example.winit_kotlin.webAccessLayer.ConnectionHelper
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter

class InventoryDA {

    fun getUomDetails(): HashMap<String, UOMConversionFactorDO> {
        val hmUOMFactor = hashMapOf<String, UOMConversionFactorDO>()
        val appDbLock = MyApplication.APP_DB_LOCK

        synchronized(appDbLock) {
            var mDatabase: SQLiteDatabase? = null
            var cursorsUoms: Cursor? = null

            try {
                val uomQuery = """
                SELECT DISTINCT TUF.ItemCode, TUF.UOM, TUF.Factor, TUF.EAConversion
                FROM tblUOMFactor TUF
                ORDER BY TUF.ItemCode, TUF.EAConversion DESC
            """
                mDatabase = DatabaseHelper.openDataBase()
                cursorsUoms = mDatabase.rawQuery(uomQuery, null)

                if (cursorsUoms.moveToFirst()) {
                    do {
                        val conversionFactorDO = UOMConversionFactorDO().apply {
                            ItemCode = cursorsUoms.getString(0)
                            UOM = cursorsUoms.getString(1)
                            factor = cursorsUoms.getFloat(2)
                            eaConversion = cursorsUoms.getFloat(3)
                        }
                        val key = "${conversionFactorDO.ItemCode}${conversionFactorDO.UOM}"
                        hmUOMFactor[key] = conversionFactorDO
                    } while (cursorsUoms.moveToNext())
                }
            } catch (e: Exception) {
                val sw = StringWriter()
                val pw = PrintWriter(sw)
                e.printStackTrace(pw)
                try {
                    ConnectionHelper.writeIntoCrashLog("\n\nCrash at ${CalendarUtils.getCurrentDateAsString()} getUomDetails ${sw.toString()}")
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            } finally {
                mDatabase?.let { if (it.isOpen) it.close() }
                cursorsUoms?.let { if (!it.isClosed) it.close() }
            }
        }

        return hmUOMFactor
    }

}