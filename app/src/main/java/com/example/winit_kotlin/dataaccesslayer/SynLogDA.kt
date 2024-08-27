package com.example.winit_kotlin.dataaccesslayer

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.dataobject.SynLogDO

class SynLogDA {
    fun getSynchLog(entity : String) : SynLogDO?{
        synchronized(MyApplication.APP_DB_LOCK){
            var sqLiteDatabase : SQLiteDatabase? = null
            var cursor : Cursor? = null
            var synLogDO : SynLogDO? = null
            try {
                sqLiteDatabase = DatabaseHelper.openDataBase()
                var query  = "select * from tblSynLog where Entity = '$entity'"
                cursor = sqLiteDatabase.rawQuery(query,null)
                if (cursor.moveToFirst()){
                    synLogDO = SynLogDO()
                    synLogDO.entity = cursor.getString(0)
                    synLogDO.action = cursor.getString(1)
                    synLogDO.UPMJ = cursor.getString(2)
                    synLogDO.UPMT = cursor.getString(3)
                    synLogDO.TimeStamp = cursor.getString(4)
                }

            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                cursor?.close()
                sqLiteDatabase?.close()
            }
            return synLogDO
        }

    }
    fun insertSynchLog(synLogDO: SynLogDO): Boolean {
        synchronized(MyApplication.APP_DB_LOCK) {
            var objSqliteDB: SQLiteDatabase? = null
            return try {
                objSqliteDB = DatabaseHelper.openDataBase()

                val stmtSelectRec = objSqliteDB.compileStatement("SELECT COUNT(*) from tblSynLog WHERE Entity =?")
                val stmtInsert = objSqliteDB.compileStatement("INSERT INTO tblSynLog(Entity, Action, UPMJ, UPMT, TimeStamp) VALUES(?,?,?,?,?)")
                val stmtUpdate = objSqliteDB.compileStatement("UPDATE tblSynLog SET Action = ?, UPMJ = ?, UPMT = ?, TimeStamp = ? WHERE Entity = ?")

                stmtSelectRec.bindString(1, synLogDO.entity)
                val countRec = stmtSelectRec.simpleQueryForLong()

                if (countRec > 0) {
                    if (!synLogDO.UPMJ.equals("0", ignoreCase = true)) {
                        stmtUpdate.bindString(1, synLogDO.action)
                        stmtUpdate.bindString(2, synLogDO.UPMJ)
                        stmtUpdate.bindString(3, synLogDO.UPMT)
                        stmtUpdate.bindString(4, synLogDO.TimeStamp)
                        stmtUpdate.bindString(5, synLogDO.entity)
                        stmtUpdate.execute()
                    }
                } else {
                    stmtInsert.bindString(1, synLogDO.entity)
                    stmtInsert.bindString(2, synLogDO.action)
                    stmtInsert.bindString(3, synLogDO.UPMJ)
                    stmtInsert.bindString(4, synLogDO.UPMT)
                    stmtInsert.bindString(5, synLogDO.TimeStamp)
                    stmtInsert.executeInsert()
                }

                stmtSelectRec.close()
                stmtUpdate.close()
                stmtInsert.close()

                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            } finally {
                if (objSqliteDB?.isOpen == true)
                    objSqliteDB?.close()
            }
        }
    }

}