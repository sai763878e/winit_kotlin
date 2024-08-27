package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.utilities.StringUtils
import com.example.winit_kotlin.utils.CalendarUtils
import java.util.Date

class CustomerDA {

    fun updateLastJourneyLogCheckIn(visitCode : String){
        synchronized(MyApplication.APP_DB_LOCK){
            var cursor : Cursor? = null

            DatabaseHelper.openDataBase().use { database ->
                try {


                cursor = database.rawQuery("select ArrivalTime from tblCustomerVisit where VisitCode ='$visitCode'",null)

                database.compileStatement("Update tblCustomerVisit set "
                            + "Status=? ,OutTime = ?,TotalTimeInMins =? where IFNULL(OutTime,'') ='' ").use { stmtUpdate ->
                                var outDate : String = CalendarUtils.getCurrentDateTime()
                    stmtUpdate.bindString(1,"N")
                    stmtUpdate.bindString(2,outDate)
                    var minutes : Long = 0

                    cursor?.let {
                        if (it.moveToFirst())
                            minutes = CalendarUtils.getDateDifferenceInMinutes(it.getString(0),outDate)
                    }
                    stmtUpdate.bindString(3,""+minutes)
                    stmtUpdate.execute()
                    stmtUpdate.close()
                    cursor?.let {
                        if (!it.isClosed)it.close()
                    }

                }

                }catch (e : Exception){
                    e.printStackTrace()
                }finally {
                    database?.close()
                }

            }
        }
    }

    fun updateCustomerVisit(){
        synchronized(MyApplication.APP_DB_LOCK){
            DatabaseHelper.openDataBase().use { database ->
                try {
                    var query = "UPDATE tblCustomerVisit SET OutTime ='${CalendarUtils.getCurrentDateAsStringForJourneyPlan()}' WHERE OutTime =''"
                    database.compileStatement(query).use { stmtUpdate ->
                        stmtUpdate.executeUpdateDelete()
                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }finally {
                    database?.close()
                }
            }
        }
    }

    fun updateLastJourneyPerfectStoreLogCheckOut(CustomerPerfectStoreMonthlyScoreUID : String){
        synchronized(MyApplication.APP_DB_LOCK){
            var cursor : Cursor? = null
            DatabaseHelper.openDataBase().use { database ->

                try {
                    cursor = database.rawQuery("select CustomerPerfectStoreMonthlyScoreUID from tblCustomerPerfectStoreMonthlyScore where CustomerPerfectStoreMonthlyScoreUID ='"+CustomerPerfectStoreMonthlyScoreUID+"'",null)
                    cursor?.let {
                        if (it.moveToFirst()){
                            var query = ("Update tblCustomerPerfectStoreMonthlyScore set "
                                    + "Status=?  where CustomerPerfectStoreMonthlyScoreUID =? ")
                            database.compileStatement(query).use { stmtUpdate ->
                                stmtUpdate.bindString(1, "${AppConstants.PERFECT_STORE_UN_UPLOAD_STATUS}")
                                stmtUpdate.bindString(2, CustomerPerfectStoreMonthlyScoreUID)
                                stmtUpdate.execute()

                                stmtUpdate.close()
                            }

                        }
                        if (!it.isClosed)it.close()
                    }

                }catch (e: Exception){
                    e.printStackTrace()
                }finally {
                    database.close()
                }
            }
        }
    }

    fun getCustomerCreditLimitFromJP() :  HashMap<String, String>?{
        synchronized(MyApplication.APP_DB_LOCK){

            var  cursor : Cursor? = null;
            var sqLiteDatabase : SQLiteDatabase? = null
            var hmCreditLimit : HashMap<String,String> = HashMap()
            var customerLimit : String = ""
            try {
                sqLiteDatabase = DatabaseHelper.openDataBase()
                var query = "SELECT Site,CREDIT_LIMIT FROM vw_CustomerCreditLimit"

                cursor = sqLiteDatabase.rawQuery(query,null)

                if (cursor.moveToFirst()){
                    do {
                        customerLimit = cursor.getString(1)
                        if (customerLimit.isNullOrEmpty())
                            customerLimit = "0"

                        if (hmCreditLimit != null) {
                            hmCreditLimit.put(cursor.getString(0),customerLimit)
                        }
                    }while (cursor.moveToNext())
                }

            }catch (e : Exception){
                e.printStackTrace()
            }finally {
                cursor?.close()
                sqLiteDatabase?.close()
            }
            return if (hmCreditLimit.isNotEmpty()) hmCreditLimit else null
        }

    }
    fun getServedCustomerList(): HashMap<String,Int> ?{
        synchronized(MyApplication.APP_DB_LOCK){

            var  cursor : Cursor? = null;
            var sqLiteDatabase : SQLiteDatabase? = null
            var hmVisits : HashMap<String,Int> = HashMap()
            var customerLimit : String = ""
            try {
                sqLiteDatabase = DatabaseHelper.openDataBase()
                var query =
                    ("SELECT DISTINCT ClientCode, TypeOfCall FROM tblCustomerVisit WHERE (Date LIKE '%" + CalendarUtils.getDateAsString(
                        Date()
                    )).toString() + "%') AND IFNULL(Reason,'')!='Remote Sales' COLLATE NOCASE GROUP BY ClientCode"

                cursor = sqLiteDatabase.rawQuery(query,null)

                if (cursor.moveToFirst()){
                    do {
                        hmVisits.put(cursor.getString(0), cursor.getInt(1));
                    }while (cursor.moveToNext())
                }

            }catch (e : Exception){
                e.printStackTrace()
            }finally {
                cursor?.close()
                sqLiteDatabase?.close()
            }
            return if (hmVisits.isNotEmpty()) hmVisits else null
        }
    }
    fun isGoodReturnEnabled(customerId: String): Boolean {
        synchronized(MyApplication.APP_DB_LOCK) {
            var objSqliteDB: SQLiteDatabase? = null
            var cursor: Cursor? = null
            var isEnabled = false

            try {
                objSqliteDB = DatabaseHelper.openDataBase()
                val query = "SELECT Attribute6 FROM tblCustomer WHERE Site = '$customerId'"
                cursor = objSqliteDB.rawQuery(query, null)

                if (cursor.moveToFirst()) {
                    val value = cursor.getString(0)
                    if (!value.isNullOrEmpty()) {
                        isEnabled = when (value.lowercase()) {
                            "true" -> true
                            "false" -> false
                            else -> StringUtils.getInt(value) == 1
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                objSqliteDB?.close()
            }

            return isEnabled
        }
    }
    fun isBadReturnEnabled(customerId: String): Boolean {
        synchronized(MyApplication.APP_DB_LOCK) {
            var objSqliteDB: SQLiteDatabase? = null
            var cursor: Cursor? = null
            var isEnabled = false

            try {
                objSqliteDB = DatabaseHelper.openDataBase()
                val query = "SELECT Attribute7 FROM tblCustomer WHERE Site = '$customerId'"
                cursor = objSqliteDB.rawQuery(query, null)

                if (cursor.moveToFirst()) {
                    val value = cursor.getString(0)
                    if (!value.isNullOrEmpty()) {
                        isEnabled = when (value.lowercase()) {
                            "true" -> true
                            "false" -> false
                            else -> StringUtils.getInt(value) == 1
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                objSqliteDB?.close()
            }

            return isEnabled
        }
    }

    fun hasMandatoryStoreCheck(routeCode: String): Boolean {
        synchronized(MyApplication.APP_DB_LOCK) {
            var hasMandatoryStoreCheck = false
            var objSqliteDB: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                objSqliteDB = DatabaseHelper.openDataBase()
                val query = """
                SELECT COUNT(*) FROM tblRoute 
                WHERE (Attribute1 = 'True' COLLATE NOCASE OR Attribute1 = '1') 
                AND Code = '$routeCode'
            """.trimIndent()
                cursor = objSqliteDB.rawQuery(query, null)
                if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
                    hasMandatoryStoreCheck = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                objSqliteDB?.close()
            }
            return hasMandatoryStoreCheck
        }
    }
    fun isSKUQtyNeedtoCheck(routeCode: String): Boolean {
        synchronized(MyApplication.APP_DB_LOCK) {
            var hasMandatoryStoreCheck = false
            var objSqliteDB: SQLiteDatabase? = null
            var cursor: Cursor? = null
            try {
                objSqliteDB = DatabaseHelper.openDataBase()
                val query = """
                SELECT COUNT(*) FROM tblRoute 
                WHERE (IsSKUQtyNeedtoCheck = 'True' COLLATE NOCASE OR IsSKUQtyNeedtoCheck = '1') 
                AND Code = '$routeCode'
            """.trimIndent()
                cursor = objSqliteDB.rawQuery(query, null)
                if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
                    hasMandatoryStoreCheck = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
                objSqliteDB?.close()
            }
            return hasMandatoryStoreCheck
        }
    }


}