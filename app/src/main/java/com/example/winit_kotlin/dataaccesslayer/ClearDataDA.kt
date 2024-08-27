package com.example.winit_kotlin.dataaccesslayer

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper

class ClearDataDA {

    fun isCanDoClearData(): Boolean {
        synchronized(MyApplication.APP_DB_LOCK) {
            var isCanDoClearData = true
            val queryPendingOrder = "SELECT count(*) FROM tblTrxHeader WHERE Status <= 0"
            val queryPendingPayment = "SELECT count(*) FROM tblPaymentHeader WHERE Status <= 0"

            DatabaseHelper.openDataBase().use { sqLiteDatabase ->
                try {
                    sqLiteDatabase.compileStatement(queryPendingOrder).use { stmtCount ->
                        val count = stmtCount.simpleQueryForLong()
                        if (count > 0) {
                            isCanDoClearData = false
                        }
                    }

                    if (isCanDoClearData) {
                        sqLiteDatabase.compileStatement(queryPendingPayment).use { stmtCount ->
                            val count = stmtCount.simpleQueryForLong()
                            if (count > 0) {
                                isCanDoClearData = false
                            }
                        }
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }finally {
                    sqLiteDatabase?.close()
                }
            }

            return isCanDoClearData
        }
    }
    fun isCanDoClearDataNew(): Boolean {
        synchronized(MyApplication.APP_DB_LOCK) {
            var sqLiteDatabase: SQLiteDatabase? = null
            var stmtCount: SQLiteStatement? = null
            var isCanDoClearData = true
            val queryPendingOrder = "Select count(*) from tblTrxHeader where Status<=0"
            val queryPendingPayment =
                "Select count(*) from tblPaymentHeader where Status<=0"
            val queryPendingMovements =
                "Select count(*) from tblMovementHeader where Status='N'"
            //			String queryPendingCustomerVisit = "Select count(*) from tblCustomerVisit where Status=0";
//			String queryPendingStoreChekc = "Select count(*) from tblStoreCheck where Status<=0";
            try {
                DatabaseHelper.openDataBase().use { sqLiteDatabase ->

                    try {
                        sqLiteDatabase.compileStatement(queryPendingPayment).use {
                            stmtCount->
                            val count = stmtCount.simpleQueryForLong()
                            if (count > 0)  isCanDoClearData = false

                        }
                        if (isCanDoClearData){
                            sqLiteDatabase.compileStatement(queryPendingPayment).use { stmtCount->
                                val count = stmtCount.simpleQueryForLong()
                                if (count > 0)isCanDoClearData = false
                            }
                        }

                        if (isCanDoClearData){
                            sqLiteDatabase.compileStatement(queryPendingMovements).use { stmtCount ->
                                val count = stmtCount.simpleQueryForLong()
                                if (count > 0)isCanDoClearData = false
                            }
                        } else {

                        }
                    } catch (e : Exception){

                        sqLiteDatabase?.close()
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (sqLiteDatabase != null && sqLiteDatabase.isOpen) sqLiteDatabase.close()
            }
            return isCanDoClearData
        }
    }
}