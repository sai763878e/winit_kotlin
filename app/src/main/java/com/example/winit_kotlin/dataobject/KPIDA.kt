package com.example.winit_kotlin.dataobject

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.utilities.StringUtils

class KPIDA {

    fun getCustomerPerfectStoreMonthlyScoreId(customerID: String, year: String, month: String): String {
        synchronized(MyApplication.APP_DB_LOCK) {
            var customerPerfectStoreMonthlyScoreId = ""
            var objSqliteDB: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                objSqliteDB = DatabaseHelper.openDataBase()
                val query = """
                SELECT CustomerPerfectStoreMonthlyScoreId 
                FROM tblCustomerPerfectStoreMonthlyScore 
                WHERE CustomerCode = '$customerID' 
                AND Month = '$month' 
                AND Year = '$year'
            """.trimIndent()
                Log.d("SOS", "getCustomerPerfectStoreMonthlyScoreId $query")

                cursor = objSqliteDB.rawQuery(query, null)
                if (cursor.moveToNext()) {
                    customerPerfectStoreMonthlyScoreId = cursor.getString(0)?.takeIf { it.isNotEmpty() } ?: StringUtils.getUniqueUUID()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                objSqliteDB?.close()
            }
            return customerPerfectStoreMonthlyScoreId
        }
    }
    fun getNPDItems(salesOrgCode: String): HashMap<String, ProductDO> {
        synchronized(MyApplication.APP_DB_LOCK) {
            val hashMap = hashMapOf<String, ProductDO>()
            var mDatabase: SQLiteDatabase? = null
            var cursor: Cursor? = null

            try {
                val query = """
                SELECT ItemCode 
                FROM tblNPDItems US 
                WHERE ItemType = 'NPD' 
                AND SalesOrgCode = '$salesOrgCode' COLLATE NOCASE 
                AND date('now') BETWEEN strftime('%Y-%m-%d', US.ValidFrom) 
                AND strftime('%Y-%m-%d', US.ValidTo)
            """.trimIndent()

                mDatabase = DatabaseHelper.openDataBase()
                cursor = mDatabase.rawQuery(query, null)

                if (cursor.moveToFirst()) {
                    do {
                        val productsDO = ProductDO().apply {
                            SKU = cursor.getString(0)
                            // Description can be set here if available in the query
                            // Description = cursor.getString(1)
                        }
                        hashMap[productsDO.SKU] = productsDO
                    } while (cursor.moveToNext())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                mDatabase?.takeIf { it.isOpen }?.close()
            }

            return hashMap
        }
    }


}