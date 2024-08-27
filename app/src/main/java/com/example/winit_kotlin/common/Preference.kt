package com.example.winit_kotlin.common

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class Preference(context: Context) {
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor: SharedPreferences.Editor = preferences.edit()

    companion object {
        const val IS_INSTALLED = "isInstalled"
        const val IS_UNLOADSTOCK = "IS_UNLOADSTOCK"
        const val DEVICE_DISPLAY_WIDTH = "DEVICE_DISPLAY_WIDTH"
        const val DEVICE_DISPLAY_HEIGHT = "DEVICE_DISPLAY_HEIGHT"
        const val SYNC_STATUS = "SYNCSTATUS"
        const val USER_ID = "user_id"
        const val USER_NAME = "USERNAME"
        const val SALES_ORG_CODE = "SALES_ORG_CODE"
        const val SALES_ORG_NAME = "SALES_ORG_NAME"
        const val USER_NAME_LOG = "USERNAMELOG"
        const val REGION = "REGION"
        const val PASSWORD = "PASSWORD"
        const val SALES_GROUP = "SALES_GROUP"
        const val USERSUBTYPE = "USERSUBTYPE"
        const val IS_ONLINE_ONLY = "IS_ONLINE_ONLY"
        const val IS_SYNC_FAILED = "IS_SYNC_FAILED"

        const val GCM_ID: String = "GCM_ID"
        const val USER_TYPE = "USER_TYPE"
        const val LANGUAGE = "LANGUAGE"
        const val REMEMBER_ME = "REMEMBER_ME"
        const val RECIEPT_NO = "RECIEPT_NO"
        const val CUSTOMER_SITE_ID = "CUSTOMER_SITE_ID"
        const val LAST_CUSTOMER_SITE_ID = "LAST_CUSTOMER_SITE_ID"
        const val INVOICE_NO = "INVOICE_NO"
        const val CUSTOMER_DETAIL = "CUSTOMER_DETAIL"
        const val LAST_JOURNEY_DATE = "LAST_JOURNEY_DATE"
        const val LAST_SYNC_TIME = "LAST_SYNC_TIME"
        const val LAST_SYNC_LSD = "LAST_SYNC_LSD"
        const val LAST_SYNC_LST = "LAST_SYNC_LST"
        const val TEMP_EMP_NO = "TEMP_EMP_NO"
        const val EMP_NO = "EMP_NO"
        const val JOURNEYCODE = "JOURNEYCODE"
        const val NOT_FROM_CHECKIN = "NOT_FROM_CHECKIN"
        const val SALESMAN_TYPE = "SALESMAN_TYPE"
        const val IS_DATA_SYNCED_FOR_USER = "IS_DATA_SYNCED_FOR_USER"
        const val IS_TELE_ORDER = "IS_TELE_ORDER"
        const val IS_EOT_DONE = "IS_EOT_DONE"
        const val IS_EOT_DONE_REPORT = "IS_EOT_DONE_REPORT"
        const val IS_JOURNEY_ENDED = "IS_JOURNEY_ENDED"
        const val IS_EOT_DONE_LATEST = "IS_EOT_DONE_LATEST"
        const val TRIP_DATE_LATEST = "TRIP_DATE_LATEST"
        const val IS_AUDIT_DONE = "IS_AUDIT_DONE"
        const val AUDIT_DATE_LATEST = "AUDIT_DATE_LATEST"
        const val PRINTER_URI = "PRINTER_URI"
        const val AUDIT_DATE = "AUDIT_DATE"
        const val IS_APP_CRASHED = "IS_APP_CRASHED"
        const val TRIP_DATE_TYPE = "TRIP_DATE_TYPE"
        const val BEGINNING_INVENTORY = "BEGINNING_INVENTORY"
        const val IS_EOT_LASTDAY_DONE = "IS_EOT_LASTDAY_DONE"
        const val IS_LOGIN_FIRST_TIME = "IS_LOGIN_FIRST_TIME"
        const val IS_EOT_UPLOADED = "IS_EOT_UPLOADED"
        const val IsStockVerified = "IsStockVerified"
        const val CURRENT_VEHICLE = "LAST_TRUCK"
        const val OFFLINE_DATE = "OFFLINE_DATE"
        const val INT_USER_ID = "int_user_id"
        const val DefaultLoad_Quantity = "DefaultLoad_Quantity"
        const val maxLoad_Quantity = "MaxLoad_Quantity"
        const val CUSTOMER_NAME = "CUSTOMER_NAME"
        const val PAYMENT_TYPE = "PAYMENT_TYPE"
        const val PAYMENT_TERMS_DESC = "PAYMENT_TERMS_DESC"
        const val SUB_CHANNEL_CODE = "SUB_CHANNEL_CODE"
        const val CHANNEL_CODE = "CHANNEL_CODE"
        const val CRASH_REPORT = "CRASH_REPORT"
        const val IS_UPLOAD_RUNNING = "IS_UPLOAD_RUNNING"
        const val GET_ALL_TRANSFER_SYNCHTIME = "GET_ALL_TRANSFER_SYNCHTIME"
        const val CUSTOMER_ID = "CUSTOMER_ID"
        const val LSD = "LSD"
        const val LST = "LST"
        const val USER_TYPE_NEW = "USER_TYPE_NEW"
        const val IS_VPN_CONNECTED = "IS_VPN_CONNECTED"
        const val BUILD_INSTALLATIONDATE = "BUILD_INSTALLATION_DATE"
        const val LSDActiveStatus = "LSDActiveStatus"
        const val LSTActiveStatus = "LSTActiveStatus"
        const val ROUTE_CODE = "ROUTE_CODE"
        const val ROUTE_TYPE = "ROUTE_TYPE"
        const val ROUTE_TYPE_NEW = "ROUTE_TYPE_NEW"
        const val ROUTE_LIMIT_REMAINING = "ROUTE_LIMIT_REMAINING"
        const val GetAllPromotions = "GetAllPromotions"
        const val GetSurveyMasters = "GetSurveyMasters"
        const val GetCustomersByUserID = "GetCustomersByUserID"
        const val GetTrxHeaderForApp = "GetTrxHeaderForApp"
        const val GetJPAndRouteDetails = "GetJPAndRouteDetails"
        const val STARTDAY_TIME = "STARTDAY_TIME"
        const val TRIP_DATE = "TRIP_DATE"
        const val JOURNEY_APP_ID = "JOURNEY_APP_ID"
        const val TRIP_DATE_START_DAY = "TRIP_DATE_START_DAY"
        const val IS_DAY_STARTED = "IS_DAY_STARTED"
        const val IS_ATTANDENCE_DONE = "IS_ATTANDENCE_DONE"
        const val HTTP_RESPONSE_CODE = "HTTP_RESPONSE_CODE"
        const val HTTP_RESPONSE_METHOD = "HTTP_RESPONSE_METHOD"
        const val NUMBER_OF_DECIMALS = "NUMBER_OF_DECIMALS"
        const val STARTDAY_TIME_ACTUAL = "STARTDAY_TIME_ACTUAL"
        const val START_JOURNEY_TIME_ACTUAL = "START_JOURNEY_TIME_ACTUAL"
        const val START_JOURNEY_REQUEST = "START_JOURNEY_REQUEST"
        const val START_JOURNEY_REQUEST_PENDING = "START_JOURNEY_REQUEST_PENDING"
        const val ENDAY_TIME = "STARTDAY_TIME"
        const val STARTDAY_VALUE = "STARTDAY_VALUE"
        const val ENDDAY_VALUE = "ENDDAY_VALUE"
        const val PASSCODE_SYNC = "PASSCODE_SYNC"
        const val TOTAL_TIME_TO_SERVE = "TOTAL_TIME_TO_SERVE"
        const val DAY_VARIFICATION = "DAY_VARIFICATION"
        const val GetAllAcknowledgedTask = "GetAllAcknowledgedTask"
        const val GetAllTask = "GetAllTask"
        const val CURRENCY_CODE = "Currency_Code"
        const val CustomerPerfectStoreMonthlyScoreUID = "CustomerPerfectStoreMonthlyScoreUID"
        const val VEHICLE_DO = "vehicleDO"
        const val IS_VANSTOCK_FROM_MENU_OPTION = "IS_VANSTOCK_FROM_MENU_OPTION"
        const val gcmId = "gcmId"
        const val SQLITE_DATE = "SQLITE_DATE"
        const val ORG_CODE = "ORG_CODE"
        const val PRINTER_TYPE = "PrinterType"
        const val PRESELLER = "PreSales"
        const val ORDER_PAYMENT_MODE = "ORDER_PAYMENT_MODE"
        const val VISIT_CODE = "VISIT_CODE"
        const val CUSTOMER_VISIT_ID = "CUSTOMER_VISIT_ID"
        const val ORG_NAME = "ORG_NAME"
        const val ORG_ADD = "ORG_ADD"
        const val ORG_FAX = "ORG_FAX"
        const val SERVER_NAME = "SERVER_NAME"
        const val IS_APP_FIRSTTIME_LAUNCH = "is_app_first_time_launch"
        const val IS_FIRSTTIME_LAUNCH = "is_first_time_launch"
        const val AUTH_TOKEN = "AUTH_TOKEN"
        const val ENDORSEMENT = "ENDORSEMENT"
        const val AUDIT_USER_CODE = "AUDIT_USER_CODE"
        const val AUDIT_USER_NAME = "AUDIT_USER_NAME"
        const val SETTINGS_URL = "SETTINGS"


        const val IS_CHECKED_IN: String = "IS_CHECKED_IN"
    }
    fun saveDoubleInPreference(key: String, value: Double) {
        editor.putString(key, value.toString()).apply()
    }
    fun saveIntInPreference(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }
    fun saveStringInPreference(key: String,value : String){
        editor.putString(key,value).apply()
    }
    fun saveBooleanInPreference(key: String,value: Boolean){
        editor.putBoolean(key,value);
    }
    fun commitPreference(){
        editor.commit()
    }

    fun removeFromPreference(key: String) {
        editor.remove(key).apply()
    }

    fun clearPreferences() {
        editor.clear().apply()
    }

    fun getStringFromPreference(key: String, defaultValue: String): String {
        return preferences.getString(key, defaultValue) ?: defaultValue
    }

    fun getBooleanFromPreference(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun getIntFromPreference(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun getDoubleFromPreference(key: String, defaultValue: Double): Double {
        return preferences.getString(key, defaultValue.toString())?.toDoubleOrNull() ?: defaultValue
    }

    fun getLongFromPreference(key: String): Long {
        return preferences.getLong(key, 0L)
    }

    fun getAllPreferences(): String {
        val preferencesMap = preferences.all
        return preferencesMap.entries.joinToString(separator = "\t\t") { (key, value) ->
            "$key: $value"
        }
    }

//    companion object {
//        fun getInstance(): Preference {
//            return Preference(MyApplicationNew.mContext)
//        }
//    }
}