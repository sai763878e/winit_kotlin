package com.example.winit_kotlin.utils

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.fragment.app.Fragment
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.common.MyApplication
import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.example.winit_kotlin.dataobject.Customer
import com.example.winit_kotlin.utilities.LogUtils
import java.net.URLDecoder

fun Activity.showLoader(context: Context,strmsg: String){
    runOnUiThread {
        LoaderUtils.showLoader(context,strmsg)
    }
}
fun Activity.hideLoader(){
    LoaderUtils.hideLoader()
}

fun Fragment.showLoader(strmsg: String) {
    activity?.runOnUiThread {
        LoaderUtils.showLoader(requireContext(), strmsg)
    }
}

fun Fragment.hideLoader() {
    activity?.runOnUiThread {
        LoaderUtils.hideLoader()
    }
}
fun Activity.isJourneyCurrentlyRunning(): Boolean {
    synchronized(MyApplication.APP_DB_LOCK) {
        var sqLiteDatabase: SQLiteDatabase? = null
        var cursor: Cursor? = null
        var isDayStarted = false

        try {
            val query = """
                SELECT COUNT(*) 
                FROM tblJourney
                WHERE EndTime LIKE '${AppConstants.DEFAULT_TIME_LOG}%' 
                AND OdometerReadingEnd = '0'
                ORDER BY StartTime DESC 
                LIMIT 1
            """.trimIndent()

            sqLiteDatabase = DatabaseHelper.openDataBase()
            cursor = sqLiteDatabase.rawQuery(query, null)

            if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
                isDayStarted = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.let {
                if (!it.isClosed) {
                    it.close()
                }
            }

            sqLiteDatabase?.let {
                if (it.isOpen) {
                    it.close()
                }
            }
        }

        return isDayStarted
    }

}
fun Activity.getTripDateType(): String {
    synchronized(MyApplication.APP_DB_LOCK) {
        var objSqliteDB: SQLiteDatabase? = null
        var tripDateType = ""
        var c: Cursor? = null

        try {
            objSqliteDB = DatabaseHelper.openDataBase()
            val query = "SELECT TR.TripDateType FROM tblRoute TR INNER JOIN tblUsers TU on TR.SalesmanCode = TU.UserCode"
            c = objSqliteDB.rawQuery(query, null)
            if (c.moveToFirst()) {
                tripDateType = c.getString(0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            c?.close()
            objSqliteDB?.close()
        }

        return tripDateType
    }
}

 fun Activity.getAddress(mallsDetails: Customer): String {
    var address = ""

    if (!mallsDetails.address1.isNullOrBlank()) {
        address = mallsDetails.address1!!
        LogUtils.infoLog("addresss1", mallsDetails.address1)
    }

    if (address.isBlank() && !mallsDetails.address2.isNullOrBlank()) {
        address = mallsDetails.address2!!
        LogUtils.infoLog("addresss2", mallsDetails.address2)
    } else if (!mallsDetails.address2.isNullOrBlank()) {
        address += ", ${mallsDetails.address2}"
        LogUtils.infoLog("addresss2", mallsDetails.address2)
    }

    /*if (address.isBlank() && !mallsDetails.addresss3.isNullOrBlank()) {
        address = mallsDetails.addresss3!!
        LogUtils.infoLog("addresss3", mallsDetails.addresss3)
    } else if (!mallsDetails.addresss3.isNullOrBlank()) {
        address += "\n${mallsDetails.addresss3}"
        LogUtils.infoLog("addresss3", mallsDetails.addresss3)
    }*/

    /*if (address.isBlank() && !mallsDetails.city.isNullOrBlank()) {
        address = mallsDetails.city!!
        LogUtils.infoLog("city", mallsDetails.city)
    } else if (!mallsDetails.city.isNullOrBlank()) {
        address += "\n${mallsDetails.city}"
        LogUtils.infoLog("city", mallsDetails.city)
    }*/

    return try {
        address = URLDecoder.decode(address, "UTF-8")
        address = URLDecoder.decode(address, "ISO-8859-1")
        address.replace("[-+.^:]".toRegex(), "")
    } catch (e: Exception) {
        e.printStackTrace()
        address
    }
}
fun Fragment.getAddress(mallsDetails: Customer): String {
    var address = ""

    if (!mallsDetails.address1.isNullOrBlank()) {
        address = mallsDetails.address1!!
        LogUtils.infoLog("addresss1", mallsDetails.address1)
    }

    if (address.isBlank() && !mallsDetails.address2.isNullOrBlank()) {
        address = mallsDetails.address2!!
        LogUtils.infoLog("addresss2", mallsDetails.address2)
    } else if (!mallsDetails.address2.isNullOrBlank()) {
        address += ", ${mallsDetails.address2}"
        LogUtils.infoLog("addresss2", mallsDetails.address2)
    }

    /*if (address.isBlank() && !mallsDetails.addresss3.isNullOrBlank()) {
        address = mallsDetails.addresss3!!
        LogUtils.infoLog("addresss3", mallsDetails.addresss3)
    } else if (!mallsDetails.addresss3.isNullOrBlank()) {
        address += "\n${mallsDetails.addresss3}"
        LogUtils.infoLog("addresss3", mallsDetails.addresss3)
    }*/

    /*if (address.isBlank() && !mallsDetails.city.isNullOrBlank()) {
        address = mallsDetails.city!!
        LogUtils.infoLog("city", mallsDetails.city)
    } else if (!mallsDetails.city.isNullOrBlank()) {
        address += "\n${mallsDetails.city}"
        LogUtils.infoLog("city", mallsDetails.city)
    }*/

    return try {
        address = URLDecoder.decode(address, "UTF-8")
        address = URLDecoder.decode(address, "ISO-8859-1")
        address.replace("[-+.^:]".toRegex(), "")
    } catch (e: Exception) {
        e.printStackTrace()
        address
    }
}

