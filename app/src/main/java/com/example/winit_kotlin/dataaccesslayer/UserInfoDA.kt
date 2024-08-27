package com.example.winit_kotlin.dataaccesslayer

import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.parsers.BlaseUserDco

class UserInfoDA {
    fun insertUpdateUserInfo(blaseUserDco: BlaseUserDco){
        synchronized(MyApplication.APP_DB_LOCK){
            DatabaseHelper.openDataBase().use { database ->
                try {


                    var query = "select COUNT(*) from tblUsers where UserCode = '"+blaseUserDco.userId+"'"
                    var queryinsert = " INSERT INTO tblUsers (UserCode, RoleId, UserName, Description, ParentCode) VALUES(?,?,?,?,?)"
                    var queryupdate = "Update tblUsers set RoleId=?, UserName=?, Description=?, ParentCode=? where UserCode = '"+blaseUserDco.userId+"'"
                    database.compileStatement(query).use { stmtSelect ->
                        if (stmtSelect.simpleQueryForLong()<=0){
                            database.compileStatement(queryinsert).use { stmtInsert ->
                                stmtInsert.bindString(1, blaseUserDco.userId)
                                stmtInsert.bindString(2, blaseUserDco.role)
                                stmtInsert.bindString(3, blaseUserDco.username)
                                stmtInsert.bindString(4, blaseUserDco.username)
                                stmtInsert.bindString(5, blaseUserDco.salesmanCode)
                                stmtInsert.executeInsert()

                                stmtInsert.close()
                            }

                        }else{
                            database.compileStatement(queryupdate).use { stmtUpdate ->
                                stmtUpdate.bindString(1, blaseUserDco.role)
                                stmtUpdate.bindString(2, blaseUserDco.username)
                                stmtUpdate.bindString(3, blaseUserDco.username)
                                stmtUpdate.bindString(4, blaseUserDco.salesmanCode)
                                stmtUpdate.execute()

                                stmtUpdate.close()
                            }
                        }
                        stmtSelect.close()
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }finally {
                    database?.close()
                }
            }
        }
    }
}