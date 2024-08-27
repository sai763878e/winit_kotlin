package com.example.winit_kotlin.webAccessLayer

import android.content.Context
import android.os.Environment
import android.preference.Preference
import android.text.TextUtils
import com.example.winit_kotlin.common.AppConstants
import com.example.winit_kotlin.parsers.BaseXmlHandler
import com.google.android.gms.vision.clearcut.LogUtils
import org.apache.http.conn.ConnectTimeoutException
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.SocketTimeoutException
import java.net.URL
import java.util.Date
import javax.xml.parsers.SAXParserFactory


class ConnectionHelper(private val listener : ConnectionExceptionListener?) {

    companion object {
        var TIMEOUT_CONNECT_MILLIS = 30000
        var TIMEOUT_READ_MILLIS = TIMEOUT_CONNECT_MILLIS - 5000

        fun writeIntoLogPerfectStore(str: String){

            deleteLogFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/PerfectStore.txt")
            var fileOutputStream = FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/PerfectStore.txt",true)
            var bufferedOutputStream = BufferedOutputStream(fileOutputStream)
            bufferedOutputStream.write(str.toByteArray())
            bufferedOutputStream.flush()
            bufferedOutputStream.close()
            fileOutputStream.close()

        }
        fun writeIntoLog(str: String){

            deleteLogFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/OrderLog.txt")
            var fileOutputStream = FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/OrderLog.txt",true)
            var bufferedOutputStream = BufferedOutputStream(fileOutputStream)
            bufferedOutputStream.write(str.toByteArray())
            bufferedOutputStream.flush()
            bufferedOutputStream.close()
            fileOutputStream.close()

        }
        @Throws(IOException::class)
        fun writeIntoLogForDeliveryAgent(str: String) {
            deleteLogFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/DeliveryLog.txt")
            var fileOutputStream = FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/DeliveryLog.txt",true)
            var bufferedOutputStream = BufferedOutputStream(fileOutputStream)
            bufferedOutputStream.write(str.toByteArray())
            bufferedOutputStream.flush()
            bufferedOutputStream.close()
            fileOutputStream.close()

        }
        @Throws(IOException::class)
        fun writeIntoCrashLog(str: String) {
            deleteLogFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/CrashLog.txt")
            var fileOutputStream = FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/CrashLog.txt",true)
            var bufferedOutputStream = BufferedOutputStream(fileOutputStream)
            bufferedOutputStream.write(str.toByteArray())
            bufferedOutputStream.flush()
            bufferedOutputStream.close()
            fileOutputStream.close()

        }
        @Throws(IOException::class)
        fun writeIntoLogForVanStockLog(str: String) {
            deleteLogFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/VanStockLog.txt")
            var fileOutputStream = FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/VanStockLog.txt",true)
            var bufferedOutputStream = BufferedOutputStream(fileOutputStream)
            bufferedOutputStream.write(str.toByteArray())
            bufferedOutputStream.flush()
            bufferedOutputStream.close()
            fileOutputStream.close()

        }
        @Throws(IOException::class)
        fun writeIntoMovementLog(str: String) {
            deleteLogFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/MovementLog.txt")
            var fileOutputStream = FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/MovementLog.txt",true)
            var bufferedOutputStream = BufferedOutputStream(fileOutputStream)
            bufferedOutputStream.write(str.toByteArray())
            bufferedOutputStream.flush()
            bufferedOutputStream.close()
            fileOutputStream.close()

        }
        @Throws(IOException::class)
        fun writeErrorLogForDeliveryAgent(str: String) {
            deleteLogFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/DeliveryErrorLog.txt")
            var fileOutputStream = FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/DeliveryErrorLog.txt",true)
            var bufferedOutputStream = BufferedOutputStream(fileOutputStream)
            bufferedOutputStream.write(str.toByteArray())
            bufferedOutputStream.flush()
            bufferedOutputStream.close()
            fileOutputStream.close()

        }

        @Throws(IOException::class)
        fun writeIntoLog(str: String,inputStream: InputStream){
            try {

                var bufferedInputStream = BufferedInputStream(inputStream)
                var fileOutputStreamor = FileOutputStream("${AppConstants.DATABASE_PATH}OrderResponse.xml")
                var bufferedOutputStreamor = BufferedOutputStream(fileOutputStreamor)
                deleteLogFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/OrderLog.txt")
                var fileOutputStream = FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/OrderLog.txt")
                var bufferedOutputStream = BufferedOutputStream(fileOutputStream)

                bufferedOutputStream.write(str.toByteArray())

                var byteArray = ByteArray(1024)
                var noBytes : Int
                while (bufferedInputStream.read(byteArray).also { noBytes =it } != -1){
                    bufferedOutputStreamor.write(byteArray,0,noBytes)
                    bufferedOutputStream.write(byteArray,0,noBytes)
                }
                bufferedOutputStreamor.flush();
                bufferedOutputStreamor.close();
                fileOutputStreamor.close();
                bufferedOutputStreamor.flush();
                bufferedOutputStreamor.close();
                fileOutputStream.close();
                bufferedOutputStream.close();
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        fun writeIntoLog(type : String , methodName :String,  strRequest  : String,  success : String){

            try {
                when{
                    type.equals("all" ,ignoreCase = true)->{

                        writeIntoLogForDeliveryAgent("\n--------------------------------------------------------");
                        writeIntoLogForDeliveryAgent("\n Posting Time: " +  Date().toString());
                        writeIntoLogForDeliveryAgent("\n URL: " + ServiceURLs.MAIN_URL);
                        writeIntoLogForDeliveryAgent("\n SoapAction: " + ServiceURLs.SOAPAction+methodName);
                        writeIntoLogForDeliveryAgent("\n--------------------------------------------------------");
                        writeIntoLogForDeliveryAgent("\n Request: " + strRequest);
                        writeIntoLogForDeliveryAgent("\n Response: " + success);
                    }
                    type.equals("vanstock",ignoreCase = true)->{
                        writeIntoLogForVanStockLog("\n--------------------------------------------------------");
                        writeIntoLogForVanStockLog("\n Posting Time: " +  Date().toString());
                        writeIntoLogForVanStockLog("\n URL: " + ServiceURLs.MAIN_URL);
                        writeIntoLogForVanStockLog("\n SoapAction: " + ServiceURLs.SOAPAction+methodName);
                        writeIntoLogForVanStockLog("\n--------------------------------------------------------");
                        writeIntoLogForVanStockLog("\n Request: " + strRequest);
                        writeIntoLogForVanStockLog("\n Response: " + success);
                    }
                    type.equals("Movements",ignoreCase = true)->{
                        writeIntoMovementLog("\n--------------------------------------------------------");
                        writeIntoMovementLog("\n Posting Time: " +  Date().toString());
                        writeIntoMovementLog("\n URL: " + ServiceURLs.MAIN_URL);
                        writeIntoMovementLog("\n SoapAction: " + ServiceURLs.SOAPAction+methodName);
                        writeIntoMovementLog("\n--------------------------------------------------------");
                        writeIntoMovementLog("\n Request: " + strRequest);
                        writeIntoMovementLog("\n Response: " + success);
                    }
                    else ->
                    {
                        writeErrorLogForDeliveryAgent("\n--------------------------------------------------------");
                        writeErrorLogForDeliveryAgent("\n Posting Time: " +  Date().toString());
                        writeErrorLogForDeliveryAgent("\n URL: " + ServiceURLs.MAIN_URL);
                        writeErrorLogForDeliveryAgent("\n SoapAction: " + ServiceURLs.SOAPAction+methodName);
                        writeErrorLogForDeliveryAgent("\n--------------------------------------------------------");
                        writeErrorLogForDeliveryAgent("\n Request: " + strRequest);
                        writeErrorLogForDeliveryAgent("\n Response: " + success);
                    }                }
            }catch (e : Exception){
                e.printStackTrace()
            }

        }

        fun deleteLogFile(path : String){
            try {
                var file = File(path)
                if (file.exists()){
                    var sizeInMB : Long = file.length()/1048576
                    if (sizeInMB >=5)
                        file.delete()

                }
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    public interface ConnectionExceptionListener {
        fun onConnectionException(msg: Any)
    }

   public interface ConnectionHelperListener {
        fun onResponseRetrieved(msg: String)
    }

    private var strRequest: String? = null
    private var methodName: String? = null
    private var handler: BaseXmlHandler<*>? = null

    // UIHandler is an inner class to handle UI requests by thread
//    private inner class UIHandler(looper: Looper) : Handler(looper) {
//        override fun handleMessage(msg: Message) {
//            when (msg.what) {
//                DISPLAY_UI_DIALOG -> listener?.onConnectionException(msg.obj)
//                else -> Unit
//            }
//        }
//
//        companion object {
//            const val DISPLAY_UI_DIALOG = 1
//        }
//    }



    fun sendRequestBulk(
        context: Context,
        strRequest: String,
        handler: BaseXmlHandler<*>,
        methodName: String,
        preference: com.example.winit_kotlin.common.Preference
    ) {
        com.example.winit_kotlin.utilities.LogUtils.errorLog("sendRequest_Bulk methodName", methodName)
        ServiceURLs.MAIN_URL = getURL(context, preference)

        val objPostforXml = UrlPost().apply {
            TIMEOUT_CONNECT_MILLIS = 600000
            TIMEOUT_READ_MILLIS = 600000
        }
        this.strRequest = strRequest
        this.methodName = methodName
        this.handler = handler

        com.example.winit_kotlin.utilities.LogUtils.errorLog("ServiceURLs.MAIN_URL", ServiceURLs.MAIN_URL)
        try {
            when {
                methodName.equals(ServiceURLs.POSTCUSTOMERPERFECTSTOREMONTHLYSCOREFROMXML, ignoreCase = true) -> {
                    writeIntoLogPerfectStore("\n--------------------------------------------------------")
                    writeIntoLogPerfectStore("\n Posting Time: ${Date()}")
                    writeIntoLogPerfectStore("\n URL: ${ServiceURLs.MAIN_URL}")
                    writeIntoLogPerfectStore("\n SoapAction: ${ServiceURLs.SOAPAction}$methodName")
                    writeIntoLogPerfectStore("\n Request: $strRequest")
                }
                methodName.equals(ServiceURLs.PostTrxDetailsFromXML, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.PostPaymentDetailsFromXML, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.VoidOrderPayments, ignoreCase = true) -> {
                    writeIntoLog("\n--------------------------------------------------------")
                    writeIntoLog("\n Posting Time: ${Date()}")
                    writeIntoLog("\n URL: ${ServiceURLs.MAIN_URL}")
                    writeIntoLog("\n SoapAction: ${ServiceURLs.SOAPAction}$methodName")
                    writeIntoLog("\n Request: $strRequest")
                }
                methodName.equals(ServiceURLs.GetVanStockLogDetail, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.ShipStockMovementsFromXML, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.GetAppActiveStatus, ignoreCase = true) -> {
                    writeIntoLog("vanstock", methodName, strRequest, "")
                }
                else -> {
                    writeIntoLog("all", methodName, strRequest, "")
                }
            }

            val inputStream = objPostforXml.soapPost(strRequest, URL(ServiceURLs.MAIN_URL), "${ServiceURLs.SOAPAction}$methodName", preference)

//            val spf = SAXParserFactory.newInstance()
//            val sp = spf.newSAXParser()
//            val xr = sp.xmlReader
//
//            xr.contentHandler = handler

            when {
                ((methodName.equals(ServiceURLs.PostPaymentDetailsFromXML, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.VoidOrderPayments, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.PostTrxDetailsFromXML, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.LOGIN_METHOD, ignoreCase = true )) && false) -> {
                    if (inputStream != null) {
                        writeIntoLog("\n\n Response: \n${Date()}\n", inputStream)
                    } else {
                        writeIntoLog("\n\n Response: NULL \n${Date()}\n")
                    }

                    if (handler != null) {
                        val file = File(context.filesDir, "OrderResponse.xml")

                        if (!file.exists()) {
                            println("File not found!")
                            return
                        }
                        val xmlContent = file.bufferedReader().use(BufferedReader::readText)
                         handler.parseXML(xmlContent)
                    }

                    File(AppConstants.DATABASE_PATH + "OrderResponse.xml").takeIf { it.exists() }?.delete()
                }
                else -> {
                    if (handler != null) {
                        handler.parseXML(inputStreamToString(inputStream))
                    }
                }
            }
        } catch (e: ConnectTimeoutException) {

            e.printStackTrace()
        } catch (e: SocketTimeoutException) {

            e.printStackTrace()
        } catch (e: Exception) {

            e.printStackTrace()
        }
    }
    fun inputStreamToString(inputStream: InputStream?): String {
        return BufferedReader(InputStreamReader(inputStream)).use { reader ->
            reader.readText()
        }
    }
    private fun getURL(mContext: Context, preference: com.example.winit_kotlin.common.Preference): String {
        var URL = ServiceURLs.MAIN_GLOBAL_URL
        if (!TextUtils.isEmpty(
                preference.getStringFromPreference(
                    com.example.winit_kotlin.common.Preference.SETTINGS_URL,
                    ""
                )
            )
        ) URL = ("http://" + preference.getStringFromPreference(
            com.example.winit_kotlin.common.Preference.SETTINGS_URL,
            ""
        )).toString() + "/Services.asmx"
        return URL
    }


    fun sendRequestBulkForAll(
        context: Context,
        strRequest: String,
        handler: BaseXmlHandler<*>,
        methodName: String,
        preference: com.example.winit_kotlin.common.Preference
    ) {
        com.example.winit_kotlin.utilities.LogUtils.errorLog("sendRequest_Bulk methodName", methodName)
        ServiceURLs.MAIN_URL = getURL(context, preference)

        val objPostforXml = UrlPost().apply {
            TIMEOUT_CONNECT_MILLIS = 600000
            TIMEOUT_READ_MILLIS = 600000
        }
        this.strRequest = strRequest
        this.methodName = methodName
        this.handler = handler

        com.example.winit_kotlin.utilities.LogUtils.errorLog("ServiceURLs.MAIN_URL", ServiceURLs.MAIN_URL)
        try {
            when {
                methodName.equals(ServiceURLs.POSTCUSTOMERPERFECTSTOREMONTHLYSCOREFROMXML, ignoreCase = true) -> {
                    writeIntoLogPerfectStore("\n--------------------------------------------------------")
                    writeIntoLogPerfectStore("\n Posting Time: ${Date()}")
                    writeIntoLogPerfectStore("\n URL: ${ServiceURLs.MAIN_URL}")
                    writeIntoLogPerfectStore("\n SoapAction: ${ServiceURLs.SOAPAction}$methodName")
                    writeIntoLogPerfectStore("\n Request: $strRequest")
                }
                methodName.equals(ServiceURLs.PostTrxDetailsFromXML, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.PostPaymentDetailsFromXML, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.VoidOrderPayments, ignoreCase = true) -> {
                    writeIntoLog("\n--------------------------------------------------------")
                    writeIntoLog("\n Posting Time: ${Date()}")
                    writeIntoLog("\n URL: ${ServiceURLs.MAIN_URL}")
                    writeIntoLog("\n SoapAction: ${ServiceURLs.SOAPAction}$methodName")
                    writeIntoLog("\n Request: $strRequest")
                }
                methodName.equals(ServiceURLs.GetVanStockLogDetail, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.ShipStockMovementsFromXML, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.GetAppActiveStatus, ignoreCase = true) -> {
                    writeIntoLog("vanstock", methodName, strRequest, "")
                }
                else -> {
                    writeIntoLog("all", methodName, strRequest, "")
                }
            }

            val inputStream = objPostforXml.soapPost(strRequest, URL(ServiceURLs.MAIN_URL), "${ServiceURLs.SOAPAction}$methodName", preference)

//            val spf = SAXParserFactory.newInstance()
//            val sp = spf.newSAXParser()
//            val xr = sp.xmlReader
//
//            xr.contentHandler = handler

            when {
                ((methodName.equals(ServiceURLs.PostPaymentDetailsFromXML, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.VoidOrderPayments, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.PostTrxDetailsFromXML, ignoreCase = true) ||
                        methodName.equals(ServiceURLs.LOGIN_METHOD, ignoreCase = true )) && false) -> {
                    if (inputStream != null) {
                        writeIntoLog("\n\n Response: \n${Date()}\n", inputStream)
                    } else {
                        writeIntoLog("\n\n Response: NULL \n${Date()}\n")
                    }

                    if (handler != null) {
                        val file = File(context.filesDir, "OrderResponse.xml")

                        if (!file.exists()) {
                            println("File not found!")
                            return
                        }
                        val xmlContent = file.bufferedReader().use(BufferedReader::readText)
                        handler.parseXML(xmlContent)
                    }

                    File(AppConstants.DATABASE_PATH + "OrderResponse.xml").takeIf { it.exists() }?.delete()
                }
                else -> {
                    if (handler != null) {
                        handler.parseXMLForAll(inputStreamToString(inputStream),methodName)
                    }
                }
            }
        } catch (e: ConnectTimeoutException) {

            e.printStackTrace()
        } catch (e: SocketTimeoutException) {

            e.printStackTrace()
        } catch (e: Exception) {

            e.printStackTrace()
        }
    }



}