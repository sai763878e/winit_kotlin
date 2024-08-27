package com.example.winit_kotlin.webAccessLayer

import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import com.example.winit_kotlin.common.Preference
import com.example.winit_kotlin.utilities.LogUtils
import com.example.winit_kotlin.utils.CalendarUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL

class UrlPost {

    private var connection: HttpURLConnection? = null
    // Initializing

    var TIMEOUT_CONNECT_MILLIS: Int = 600000

    var TIMEOUT_READ_MILLIS: Int = 600000

    val URLPOST_CONNECTION: String = "URLPOST_CONNECTION"

    val URLPOST_PENDING_INVOICE_CONNECTION: String = "PENDING_INVOICE_CONNECTION"
    private var inputStream: InputStream? = null


    public fun soapPost(xmlString : String,url : URL,soapUrl :String,preference : Preference): InputStream? {

        try {
            LogUtils.errorLog("soapPost", soapUrl + " start")
            if (!soapUrl.contains("Hello"))
                writeIntoLog(("soapPost " + soapUrl + " start " + CalendarUtils.getCurrentDateAsString()).toString() + "\n")
            System.setProperty("http.keepAlive", "false")
            startTimer(soapUrl)
            connection = url.openConnection() as HttpURLConnection?
            connection!!.doInput = true
            connection!!.doOutput = true
            connection!!.useCaches = false
            connection!!.connectTimeout = TIMEOUT_CONNECT_MILLIS
            connection!!.readTimeout = TIMEOUT_READ_MILLIS
            connection!!.requestMethod = "POST"
            connection!!.setRequestProperty("Content-Type", "text/xml;charset=UTF-8")
            connection!!.setRequestProperty("UserCode", getUserCode(preference))
            if (!soapUrl.contains("Hello"))
                connection!!.setRequestProperty("SOAPAction", soapUrl)


            //				connection.setRequestProperty("Connection","Close");
//				connection.setRequestProperty("Accept-Encoding","gzip, deflate");
            //gZipOutStream = new GZIPOutputStream(connection.getOutputStream());
//				PrintWriter writer = new PrintWriter(gZipOutStream);
            val writer = PrintWriter(connection!!.outputStream)
            writer.write(xmlString)
            writer.flush()

            val httpCode = connection!!.responseCode
            val resMsg = connection!!.responseMessage
            preference.saveIntInPreference(Preference.HTTP_RESPONSE_CODE, httpCode)
            preference.saveStringInPreference(Preference.HTTP_RESPONSE_METHOD, soapUrl)
            preference.commitPreference()

            inputStream = connection!!.inputStream

            //				gZipInputStream = new GZIPInputStream(inputStream);
            LogUtils.errorLog("soapPost", soapUrl + " end ")
            UrlPost.writeIntoLog(("soapPost " + soapUrl + " end " + CalendarUtils.getCurrentDateAsString()).toString() + "\n")
            setEOTStatus(preference)

        }catch (e :Exception){
            preference.saveIntInPreference(Preference.HTTP_RESPONSE_CODE,404)
            preference.saveStringInPreference(Preference.HTTP_RESPONSE_METHOD,soapUrl)
            preference.commitPreference()
            e.printStackTrace()
        }
        finally {

        }
        return inputStream;
    }
    private fun startTimer(methodName: String) {
        handlerThread = HandlerThread("MyHandlerThread")
        handlerThread!!.start()
        timerHandler = Handler(handlerThread!!.looper)
        LogUtils.errorLog("startTimer", "startTimer connect")
        var NOTIFY_INTERVAL: Long = 0

        if (methodName.equals(
                ServiceURLs.SOAPAction + ServiceURLs.Hello,
                ignoreCase = true
            )
        ) NOTIFY_INTERVAL = (1000 * 10).toLong()
        NOTIFY_INTERVAL =
            if (methodName.equals(
                    ServiceURLs.SOAPAction + ServiceURLs.VERIFY_LOGIN_METHOD,
                    ignoreCase = true
                )
            ) (60000 * 5).toLong()
            else if (methodName.equals(
                    ServiceURLs.SOAPAction + ServiceURLs.LOGIN_METHOD,
                    ignoreCase = true
                )
            ) (60000 * 5).toLong()
            else if (methodName.equals(
                    ServiceURLs.SOAPAction + ServiceURLs.GetPendingInvoiceFromSAP,
                    ignoreCase = true
                ) || methodName.equals(
                    ServiceURLs.SOAPAction + ServiceURLs.GetCustomerCreditLimit,
                    ignoreCase = true
                ) || methodName.equals(
                    ServiceURLs.SOAPAction + ServiceURLs.GetGetCustomerbySellerInvoiceDetails,
                    ignoreCase = true
                )
            ) (60000 * 1).toLong()
            else if (methodName.equals(
                    ServiceURLs.SOAPAction + ServiceURLs.GetTrxHeaderForApp,
                    ignoreCase = true
                )
            ) (60000 * 2).toLong()
            else (60000 * 3).toLong()

        timerHandler!!.postDelayed(timerRunnable, NOTIFY_INTERVAL)
    }

    //	Handler timerHandler = new Handler(Looper.getMainLooper());
    var timerHandler: Handler? = null
    var handlerThread: HandlerThread? = null
    var timerRunnable: Runnable = Runnable {
        LogUtils.errorLog("CustomTimerTask", "CustomTimerTask disconnect1")
        if (connection != null) {
            LogUtils.errorLog("CustomTimerTask", "CustomTimerTask disconnect2")
            connection!!.disconnect()
        }
        if (inputStream != null) {
            LogUtils.errorLog("CustomTimerTask", "CustomTimerTask disconnect3")
            try {
                inputStream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        stopTimer()
    }


    fun stopTimer(){
        timerHandler?.removeCallbacks(timerRunnable)
        timerHandler= null
    }

    companion object{
        fun writeIntoLog(str :String){
            try {
                val folder = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "URLConnectionLogs"
                )
                if (!folder.exists() && !folder.mkdirs()) {
                    return
                }
                val logFile = File(
                    folder,
                    ("ConnectionLog_" + CalendarUtils.getCurrentDate()).toString() + ".txt"
                ) //@rajaprasad 22-05-2024 need
                try {
                    FileOutputStream(logFile).use { fos ->
                        fos.write(str.toByteArray())
                        fos.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } catch (e :Exception){
                e.printStackTrace()
            }
        }
    }

    fun setEOTStatus(preference: Preference){
        if (connection != null){

            var isEOTDone : String? = connection!!.getHeaderField("IsEOTDone")
            if (preference != null){
                if (isEOTDone != null && isEOTDone.equals("true", ignoreCase = true)) {
                    if (!preference.getBooleanFromPreference(Preference.IS_EOT_DONE,false)){
                        preference.saveBooleanInPreference(Preference.IS_EOT_DONE,true)
                    }
                }

            }
        }
    }

    fun getUserCode(preference : Preference) : String{
        var usercode : String = ""
        if (preference != null)
            usercode = preference.getStringFromPreference(Preference.EMP_NO,"")

        return usercode
    }
}