package com.example.winit_kotlin.dataobject

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper

class ClearTaxDA {

    fun isClearTaxEnabledRoute(routeId: String): Boolean {
        synchronized(MyApplication.APP_DB_LOCK) {
            var isClearTaxEnabledRoute = false
            var objSqliteDB: SQLiteDatabase? = null
            var cursorRoute: Cursor? = null

            try {
                objSqliteDB = DatabaseHelper.openDataBase()

                val routeQuery = "select EnableEInvoiceIntegration, SalesOrgCode from tblroute where code='$routeId'"
                Log.i("cleartax", "isClearTaxEnabled setting route_query: $routeQuery")

                cursorRoute = objSqliteDB.rawQuery(routeQuery, null)

                if (cursorRoute != null && cursorRoute.moveToFirst()) {
                    val valueRoute = cursorRoute.getString(0).trim()
                    val salesOrg = cursorRoute.getString(1)

                    isClearTaxEnabledRoute = valueRoute.equals("true", ignoreCase = true) || valueRoute == "1"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursorRoute?.close()
            }

            return isClearTaxEnabledRoute
        }
    }

}