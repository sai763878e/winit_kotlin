package com.example.winit_kotlin.databaseaccess

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.MyApplication

class DatabaseHelper(context: Context) :SQLiteOpenHelper(context,AppConstants.DATABASE_NAME,null,1) {

    private val myContext: Context = context
    override fun onCreate(p0: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    companion object{
        private var _database: SQLiteDatabase? = null
        private var _databaseCPS: SQLiteDatabase? = null

        @Throws(SQLiteException::class)
        fun openDataBase(): SQLiteDatabase {
            try {


            synchronized(MyApplication.APP_DB_LOCK) {
                if (_database == null || !_database!!.isOpen) {
                    _database = SQLiteDatabase.openDatabase(
                        AppConstants.DATABASE_PATH + AppConstants.DATABASE_NAME,
                        null,
                        SQLiteDatabase.OPEN_READWRITE or SQLiteDatabase.CREATE_IF_NECESSARY
                    )
                }
                return _database!!
            }
            }catch (e : Exception){

                return _database!!
            }
        }

        @Throws(SQLiteException::class)
        fun closeDatabase() {
            _database?.close()
        }

        @Throws(SQLiteException::class)
        fun openCPSDataBase(): SQLiteDatabase {
            try {

                synchronized(MyApplication.APP_DB_LOCK) {
                    if (_database == null || !_database!!.isOpen) {
                        _database = SQLiteDatabase.openDatabase(
                            AppConstants.DATABASE_PATH + AppConstants.DATABASE_NAME_CPS,
                            null,
                            SQLiteDatabase.OPEN_READWRITE or SQLiteDatabase.CREATE_IF_NECESSARY
                        )
                    }
                    return _database!!
                }
            }catch (e : Exception){

                return _database!!
            }
        }

        fun createTables() {
            synchronized(MyApplication.APP_DB_LOCK) {
                try {
                    /*openDataBase();
            _database.execSQL("DROP TABLE IF EXISTS \"tblVisibilityHeader\"");
            _database.execSQL("CREATE TABLE \"tblVisibilityHeader\" (\"VisibilityId\" VARCHAR, \"CustomersiteId\" VARCHAR, \"VisibilityStatus\" INTEGER, \"Status\" INTEGER, \"AppVisibilityId\" VARCHAR)");

            _database.execSQL("DROP TABLE IF EXISTS \"tblVisibilityHeaderDetails\"");
            _database.execSQL("CREATE TABLE \"tblVisibilityHeaderDetails\" (\"VisibilityId\" VARCHAR, \"ItemDescription\" VARCHAR, \"ItemAmount\" INTEGER, \"FromDate\" VARCHAR, \"ToDate\" VARCHAR)");*/
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                } finally {
                    closedatabase()
                }
            }
        }

        //To close database
        fun closedatabase() {
            if (_database != null) _database!!.close()
        }
    }
}