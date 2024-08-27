package com.example.winit_kotlin.utils

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.winit_kotlin.common.Preference
import com.example.winit_kotlin.interfaces.OnDialogListeners
import com.example.winit_kotlin.utilities.RunshowCustomDialogs

// Extension function to hide the keyboard in any Activity
fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocusView = currentFocus ?: View(this)
    inputMethodManager.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
}

fun Activity.showCustomDialog( context:Context,  preference: Preference,strTitle:String,  strMessage:String,  firstBtnName:String,  secondBtnName:String?,  from:String,onDialogListeners: OnDialogListeners){
   try {
//       runOnUiThread { RunshowCustomDialogs(context,preference,strTitle,strMessage,firstBtnName,secondBtnName,from,false,onDialogListeners) }

       runOnUiThread {
           val dialogRunnable = RunshowCustomDialogs(
               context,
               preference,
               strTitle,
               strMessage,
               firstBtnName,
               secondBtnName,
               from,
               false,
               onDialogListeners
           )
           Handler(Looper.getMainLooper()).post(dialogRunnable)
       }
   }catch (e : Exception){
       e.printStackTrace()
   }
}
fun Activity.showCustomDialog( context:Context,   preference: Preference,strTitle:String,  strMessage:String,  firstBtnName:String,  secondBtnName:String,  from:String,params:Any  ,onDialogListeners: OnDialogListeners){
    runOnUiThread {
        val dialogsRunnable = RunshowCustomDialogs(context,preference,strTitle,strMessage,firstBtnName,secondBtnName,from,false,onDialogListeners,params)
        Handler(Looper.getMainLooper()).post(dialogsRunnable)
    }
}
//fun Activity.showCustomDialog( context:Context,   preference: Preference,strTitle:String,  strMessage:String,  firstBtnName:String,  secondBtnName:String,  from:String,params:String  ,onDialogListeners: OnDialogListeners){
//    runOnUiThread { RunshowCustomDialogs(context,preference,strTitle,strMessage,firstBtnName,secondBtnName,from,false,onDialogListeners,params) }
//}
fun Activity.showCustomDialog( context:Context,  preference: Preference, strTitle:String,  strMessage:String,  firstBtnName:String,  secondBtnName:String,  from:String,isCancelable: Boolean,onDialogListeners: OnDialogListeners){
    runOnUiThread {
        val  dialogsRunnable = RunshowCustomDialogs(context,preference,strTitle,strMessage,firstBtnName,secondBtnName,from,isCancelable,onDialogListeners)

        Handler(Looper.getMainLooper()).post(dialogsRunnable)
    }
}