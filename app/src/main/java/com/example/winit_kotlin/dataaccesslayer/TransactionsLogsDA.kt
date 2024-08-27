package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.dataobject.TrxLogHeaders
import com.example.winit_kotlin.webAccessLayer.ConnectionHelper
import java.io.StringWriter

class TransactionsLogsDA {

    fun getTransactionsLogCount(date : String) : Int{
        synchronized(MyApplication.APP_DB_LOCK){

            var cursor : Cursor? = null
            var count : Int = 0
            DatabaseHelper.openDataBase().use { database ->
               return try {


                   cursor = database.rawQuery("SELECT count(*) from tblTrxLogHeader where Date='"+date+"' ",null)
                   cursor?.let {
                       if (it.moveToFirst())
                           count = it.getInt(0)

                   }
                   count
                }catch (e : Exception){
                    e.printStackTrace()
                    0
                }finally {
                    cursor?.let {
                        if (!it.isClosed)it.close()
                    }
                   database?.close()
                }
            }
        }

    }

    fun insertTrxLogHeaders(vecCashDenominationDOs: List<TrxLogHeaders>): Boolean {
        synchronized(MyApplication.APP_DB_LOCK) {
            var objSqliteDB: SQLiteDatabase? = null
            return try {
                objSqliteDB = DatabaseHelper.openDataBase()
                objSqliteDB.beginTransaction()

                val stmtSelectRec = objSqliteDB.compileStatement("SELECT COUNT(*) from tblTrxLogHeader WHERE Date = ?")
                val stmtInsert = objSqliteDB.compileStatement("INSERT INTO tblTrxLogHeader (Date, TotalScheduledCalls, TotalActualCalls, TotalProductiveCalls, TotalSales, TotalCreditNotes, TotalCollections, CurrentMonthlySales) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
                val stmtUpdate = objSqliteDB.compileStatement("UPDATE tblTrxLogHeader SET TotalScheduledCalls = ?, TotalActualCalls = ?, TotalProductiveCalls = ?, TotalSales = ?, TotalCreditNotes = ?, TotalCollections = ?, CurrentMonthlySales = ? WHERE Date = ?")

                for (cashDenominationDO in vecCashDenominationDOs) {
                    stmtSelectRec.bindString(1, cashDenominationDO.trxDate)
                    val countRec = stmtSelectRec.simpleQueryForLong()

                    if (countRec != 0L) {
                        stmtUpdate.apply {
                            bindLong(1, cashDenominationDO.totalScheduledCalls.toLong())
                            bindLong(2, cashDenominationDO.totalActualCalls.toLong())
                            bindLong(3, cashDenominationDO.totalProductiveCalls.toLong())
                            bindDouble(4, cashDenominationDO.totalSales)
                            bindDouble(5, cashDenominationDO.totalCreditNotes)
                            bindDouble(6, cashDenominationDO.totalCollections)
                            bindDouble(7, cashDenominationDO.currentMonthlySales)
                            bindString(8, cashDenominationDO.trxDate)
                            execute()
                        }
                    } else {
                        stmtInsert.apply {
                            bindString(1, cashDenominationDO.trxDate)
                            bindLong(2, cashDenominationDO.totalScheduledCalls.toLong())
                            bindLong(3, cashDenominationDO.totalActualCalls.toLong())
                            bindLong(4, cashDenominationDO.totalProductiveCalls.toLong())
                            bindDouble(5, cashDenominationDO.totalSales)
                            bindDouble(6, cashDenominationDO.totalCreditNotes)
                            bindDouble(7, cashDenominationDO.totalCollections)
                            bindDouble(8, cashDenominationDO.currentMonthlySales)
                            executeInsert()
                        }
                    }
                }

                objSqliteDB.setTransactionSuccessful()

                stmtSelectRec.close()
                stmtUpdate.close()
                stmtInsert.close()

                true
            } catch (e: Exception) {
//                val sw = StringWriter()
//                e.printStackTrace(PrintWriter(sw))
//                try {
//                    ConnectionHelper.writeIntoCrashLog("\n\nCrash at ${CalendarUtils.getCurrentDateAsString()} insertTrxLogHeaders ${sw.toString()}")
//                } catch (ex: IOException) {
//                    ex.printStackTrace()
//                }
                false
            } finally {
                objSqliteDB?.endTransaction()
                objSqliteDB?.close()
            }
        }
    }

}