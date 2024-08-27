package com.example.winit_kotlin.webAccessLayer

import com.example.winit_kotlin.utilities.LogUtils

class BuildXMLRequest {

companion object{
    private const val SOAP_HEADER : String =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
            "<soap:Body>";
    private const val SOAP_FOOTER : String ="</soap:Body>"+
            "</soap:Envelope>";
    fun loginRequest(UserName : String,password : String,gcmId : String,deviceId : String,versionNo :String) : String{
        LogUtils.errorLog("CheckLogin", "CheckLogin - $UserName")
        val sb = StringBuilder()
        try {
            sb.append(SOAP_HEADER)
                .append("<CheckLogin xmlns=\"http://tempuri.org/\">")
                .append("<UserName>").append(UserName).append("</UserName>")
                .append("<Password>").append(password).append("</Password>")
                .append("<GCMKey>").append(gcmId).append("</GCMKey>")
                .append("<DeviceId>").append(deviceId).append("</DeviceId>")
                .append("<VersionNo>").append(versionNo).append("</VersionNo>")
                .append("</CheckLogin>")
                .append(SOAP_FOOTER)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        LogUtils.errorLog("CheckLogin", "" + sb.toString())

        return sb.toString()
    }

    fun getVersionDetails(deviceType: String, versionNumber: String): String {
        val strXML = SOAP_HEADER +
                "<GetVersionDetails xmlns=\"http://tempuri.org/\">" +
                "<DeviceType>" + deviceType + "</DeviceType>" +
                "<VersionNo>" + versionNumber + "</VersionNo>" +
                "</GetVersionDetails>" +
                SOAP_FOOTER
        LogUtils.errorLog("VersionRequest", strXML)
        return strXML
    }

    fun CheckMobilityStatus(userCode: String) : String{
        val strXML = SOAP_HEADER+
                "<CheckMobilityStatus xmlns=\"http://tempuri.org/\">" +
                "<UserCode>" + userCode + "</UserCode>" +
                "</CheckMobilityStatus>" +
                SOAP_FOOTER
        LogUtils.errorLog("VersionRequest", strXML)
        return strXML
    }
    fun validateUserLogin(
        userCode: String?,
        logType: String?,
        gcmId: String?,
        macAddress: String?
    ): String {
        val sb = java.lang.StringBuilder()
        try {
            sb.append(SOAP_HEADER)
                .append("<PostSignInSignOutLog xmlns=\"http://tempuri.org/\">")
                .append("<UserCode>").append(userCode).append("</UserCode>")
                .append("<logType>").append(logType).append("</logType>")
                .append("<GCMKey>").append(gcmId).append("</GCMKey>")
                .append("<MacAddress>").append(macAddress).append("</MacAddress>")
                .append("</PostSignInSignOutLog>")
                .append(SOAP_FOOTER)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        LogUtils.errorLog("CheckLogin", "" + sb.toString())

        return sb.toString()
    }
    //GetMasterData
    fun getMasterDate(empNo: String?): String {
        val strXML = java.lang.StringBuilder()
        strXML.append(SOAP_HEADER)
            .append("<GetMasterDataFile xmlns=\"http://tempuri.org/\">")
            .append("<UserCode>").append(empNo).append("</UserCode>")
            .append("<lsd>0</lsd>")
            .append("<lst>0</lst>")
            .append("</GetMasterDataFile>")
            .append(SOAP_FOOTER)
        LogUtils.errorLog("strXML", "" + strXML)
        return strXML.toString()
    }

    fun getCPSMasterDate(usercode: String?): String {
        val strXML = java.lang.StringBuilder()
        strXML.append(SOAP_HEADER)
            .append("<GetMasterDataFileForCPSPromotion  xmlns=\"http://tempuri.org/\">")
            .append("<UserCode>").append(usercode).append("</UserCode>")
            .append("<lsd>0</lsd>")
            .append("<lst>0</lst>")
            .append("</GetMasterDataFileForCPSPromotion>")
            .append(SOAP_FOOTER)
        LogUtils.errorLog("strXML", "" + strXML)
        return strXML.toString()
    }
}


}