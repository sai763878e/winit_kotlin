package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.dataobject.Customer
import com.example.winit_kotlin.dataobject.SellingSKUClassification
import com.example.winit_kotlin.dataobject.StoreCheckClassificationDO
import java.util.Vector

class SellingSKUClassificationsDA {
    fun getAllSellingSKUClassificationsForStoreCheck(sqLiteDatabase: SQLiteDatabase?): Vector<StoreCheckClassificationDO>? {
        synchronized(MyApplication.APP_DB_LOCK) {
            var vecSellingSKU: Vector<StoreCheckClassificationDO>? = null
            var cursor: Cursor? = null

            try {
                var db = sqLiteDatabase
                if (db == null || !db.isOpen) {
                    db = DatabaseHelper.openDataBase()
                }

                val query = """
                SELECT SellingSKUClassificationId, Code, Description, Sequence 
                FROM tblSellingSKUClassification 
                WHERE (IsActive = 'True' OR IsActive = 1) 
                ORDER BY Sequence ASC
            """.trimIndent()

                cursor = db.rawQuery(query, null)

                if (cursor.moveToFirst()) {
                    vecSellingSKU = Vector()
                    do {
                        val classificationDO = StoreCheckClassificationDO().apply {
                            sellingSKUClassificationId = cursor.getInt(0)
                            code = cursor.getString(1)
                            description = cursor.getString(2)
                            sequence = cursor.getInt(3)
                        }
                        vecSellingSKU.add(classificationDO)
                    } while (cursor.moveToNext())
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                cursor?.close()
            }

            return vecSellingSKU
        }
    }
    fun getCustomerSellingSKUGroup(
        customer: Customer,
        sqLiteDatabase: SQLiteDatabase?,
        userCode: String
    ): SellingSKUClassification? {
        synchronized(MyApplication.APP_DB_LOCK) {
            var gSellingSKU: SellingSKUClassification? = null
            var cursor: Cursor? = null
            var cursor2: Cursor? = null
            var cursor3: Cursor? = null

            try {
                val database = sqLiteDatabase ?: DatabaseHelper.openDataBase()

                val query = "SELECT Name, FieldName FROM tblCustomerSellingSKUGroup ORDER BY Priority"
                Log.d("Selling SKU", "query $query")
                cursor = database.rawQuery(query, null)

                if (cursor.moveToFirst()) {
                    do {
                        val sellingSKU = SellingSKUClassification().apply {
                            name = cursor.getString(0)
                            fieldName = cursor.getString(1)
                        }

                        var condition = ""
                        when (sellingSKU.fieldName) {
                            "Code" -> {
                                sellingSKU.code = customer.site
                                condition = " AND GroupCode = '${customer.site}' "
                            }
                            "Attribute8" -> {
                                sellingSKU.code = customer.Attribute8
                                condition = " AND GroupCode = '${customer.Attribute8}' "
                            }
                            "ChannelCode" -> {
                                sellingSKU.code = customer.channelCode
                                condition = " AND GroupCode = '${customer.channelCode}' "
                            }
                            "Route" -> {
                                sellingSKU.code = customer.routeCode
                                condition = " AND GroupCode = '${customer.routeCode}' "
                            }
                            "RouteType" -> {
                                sellingSKU.code = customer.routeType
                                condition = " AND GroupCode = '${customer.routeType}' "
                            }
                            "SubChannelCode" -> {
                                sellingSKU.code = customer.subChannelCode
                                condition = " AND GroupCode = '${customer.subChannelCode}' "
                            }
                            "SubSubChannelCode" -> {
                                sellingSKU.code = customer.subSubChannelCode
                                condition = " AND GroupCode = '${customer.subSubChannelCode}' "
                            }
                        }

                        val sellingSKUQuery = "SELECT COUNT(*) FROM tblSellingSKU S WHERE GroupType = '${sellingSKU.name}' COLLATE NOCASE $condition"
                        Log.d("Selling SKU", "query $sellingSKUQuery")

                        cursor2 = database.rawQuery(sellingSKUQuery, null)
                        if (cursor2.moveToFirst()) {
                            val count = cursor2.getInt(0)
                            if (count > 0) {
                                gSellingSKU = sellingSKU
                                break
                            }
                        }
                    } while (cursor.moveToNext())
                }

                if (gSellingSKU == null) {
                    val sellingSKU = SellingSKUClassification().apply {
                        name = "User"
                        fieldName = "UserCode"
                        code = userCode
                    }
                    val userQuery = "SELECT COUNT(*) FROM tblSellingSKU S WHERE GroupType = 'User' AND GroupCode = '$userCode' COLLATE NOCASE"
                    Log.d("Selling SKU", "query $userQuery")

                    cursor3 = database.rawQuery(userQuery, null)
                    if (cursor3.moveToFirst()) {
                        val count = cursor3.getInt(0)
                        if (count > 0) {
                            gSellingSKU = sellingSKU
                        }
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                cursor?.close()
                cursor2?.close()
                cursor3?.close()
            }

            return gSellingSKU
        }
    }

}