package com.example.winit_kotlin.utils

import android.app.Activity
import android.content.Context
import android.provider.Settings
import android.telephony.TelephonyManager

fun Activity.getUniqueID(): String {
    var myAndroidDeviceId = ""
    try {
        val mTelephony = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        myAndroidDeviceId = if (!mTelephony.deviceId.isNullOrEmpty()) {
            mTelephony.deviceId
        } else {
            Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
        }
        if (myAndroidDeviceId.isNotEmpty()) {
            myAndroidDeviceId = myAndroidDeviceId.uppercase()
        }
        return myAndroidDeviceId
    } catch (e: SecurityException) {
        return Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
    }

}
fun Activity.getVersionName() : String{
    var versionName : String ="0.0"

    try {
        versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
    } catch (e : Exception) {
        e.printStackTrace();
    }
    return versionName;
}