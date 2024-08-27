package com.example.winit_kotlin.utilities

import android.util.Log
import java.io.*

object LogUtils {

    var isLoggingEnabled: Boolean = true

    var isDefaultLogEnabled: Boolean = true

    fun errorLog(tag: String, msg: String) {
        if (isLoggingEnabled) Log.e(tag, msg)
    }

    fun infoLog(tag: String?, msg: String?) {
        if (isLoggingEnabled) Log.i(tag, msg!!)
    }

    fun defaultLog(tag: String?, msg: String?) {
        if (isDefaultLogEnabled) Log.i(tag, msg!!)
    }

    fun debug(tag: String?, msg: String?) {
        if (isLoggingEnabled) Log.d(tag, msg!!)
    }

    fun printMessage(msg: String?) {
        if (isLoggingEnabled) println(msg)
    }

    fun enableLogging(isEnabled: Boolean) {
        isLoggingEnabled = isEnabled
    }

    @Throws(IOException::class)
    fun convertResponseToFile(`is`: InputStream?) {
        BufferedInputStream(`is`).use { bis ->
            FileOutputStream("/sdcard/Response.xml").use { fos ->
                BufferedOutputStream(fos).use { bos ->
                    val byt = ByteArray(1024)
                    var noBytes: Int
                    while (bis.read(byt).also { noBytes = it } != -1) {
                        bos.write(byt, 0, noBytes)
                    }
                    bos.flush()
                }
            }
        }
    }

    @Throws(IOException::class)
    fun convertRequestToFile(request: String) {
        FileOutputStream(File("/sdcard/Request.xml")).use { fos ->
            fos.write(request.toByteArray())
        }
    }

    fun getDataFromInputStream(inpStrData: InputStream?): StringBuffer? {
        return if (inpStrData != null) {
            try {
                BufferedReader(InputStreamReader(inpStrData)).use { brResp ->
                    val sbResp = StringBuffer()
                    var line: String?
                    while (brResp.readLine().also { line = it } != null) {
                        sbResp.append(line)
                    }
                    sbResp
                }
            } catch (e: Exception) {
                errorLog("LogUtils", "Exception occurred in getDataFromInputStream(): $e")
                null
            }
        } else {
            null
        }
    }

}
