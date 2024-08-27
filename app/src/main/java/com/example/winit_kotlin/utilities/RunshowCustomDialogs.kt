package com.example.winit_kotlin.utilities

import android.content.Context
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import com.example.winit_kotlin.R
import com.example.winit_kotlin.common.Preference
import com.example.winit_kotlin.interfaces.OnDialogListeners

class RunshowCustomDialogs : Runnable {
    private var strTitle: String? = null // Title of the dialog
    private var strMessage: String? = null // Message to be shown in dialog
    private var firstBtnName: String? = null
    private var secondBtnName: String? = null
    private var from: String
    private lateinit var params: String
    private lateinit var paramateres: Any
    private var isCancelable = false
    private var context : Context
    private var posClickListener: View.OnClickListener? = null
    private var negClickListener: View.OnClickListener? = null

    private  var customDialog : CustomDialog? = null
    private var preference : Preference
    private lateinit var onDialogListeners : OnDialogListeners


    //primary constructor
    constructor(context: Context,preference: Preference,
                strTitle:String,
                strMessage: String,
                firstBtnName: String,
                secondBtnName: String?,
                from: String? = null,
                isCancelable: Boolean,onDialogListeners : OnDialogListeners){
        this.context = context
        this.strTitle = strTitle
        this.strMessage = strMessage
        this.firstBtnName = firstBtnName
        this.secondBtnName = secondBtnName
        this.isCancelable = isCancelable
        this.preference = preference
        this.onDialogListeners = onDialogListeners
        this.from = from ?: ""
    }

    // Secondary constructor with String params
    constructor(
        context: Context,
        preference: Preference,
        strTitle: String,
        strMessage: String,
        firstBtnName: String,
        secondBtnName: String?,
        from: String? = null,
        isCancelable: Boolean,
        onDialogListeners: OnDialogListeners,
        params: String? = null
    ) : this(context, preference,strTitle, strMessage, firstBtnName, secondBtnName, from, isCancelable,onDialogListeners) {
        this.params = params ?: ""
    }

    // Secondary constructor with Object params
    constructor(
        context: Context,
        preference: Preference,
        strTitle: String,
        strMessage: String,
        firstBtnName: String,
        secondBtnName: String,
        from: String? = null,
        isCancelable: Boolean,
        onDialogListeners: OnDialogListeners,
        params: Any
    ) : this(context,preference, strTitle, strMessage, firstBtnName, secondBtnName, from, isCancelable,onDialogListeners) {
        this.paramateres = params
    }

    override fun run() {
        if (customDialog!= null && customDialog!!.isShowing)
            customDialog!!.dismiss()

        var view : View
        val inflater = LayoutInflater.from(context)
        if (from.equals("Storecheck",ignoreCase = true)){

            view = inflater.inflate(R.layout.popup_storecheck_confirmation, null)
            customDialog = CustomDialog(context,view,preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 40,LayoutParams.WRAP_CONTENT,true)
            //				customDialog.setCancelable(isCancelable);
            customDialog!!.setCancelable(false)

           android.os.Handler(Looper.getMainLooper()).postDelayed({
               if (customDialog != null && customDialog!!.isShowing) customDialog!!.dismiss()
               from?.let { onDialogListeners.onButtonYesClick(it) }
           },3000)
            try {
                if (!customDialog!!.isShowing) customDialog!!.showCustomDialog()
            } catch (e: Exception) {
            }

        }else if (from.equals("Without_MSL_Items",ignoreCase = true)){


        }else{
            view = inflater.inflate(R.layout.custom_common_popup, null)
            customDialog = CustomDialog(context,view,preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 20,LayoutParams.WRAP_CONTENT,true)
            customDialog!!.setCancelable(isCancelable);

            val tvTitle = view.findViewById<View>(R.id.tvTitlePopup) as TextView
            val tvMessage = view.findViewById<View>(R.id.tvMessagePopup) as TextView
            val btnYes = view.findViewById<View>(R.id.btnYesPopup) as Button
            val btnNo = view.findViewById<View>(R.id.btnNoPopup) as Button

            tvTitle.text = strTitle
            tvMessage.text = strMessage
            btnYes.text = firstBtnName
            if (!TextUtils.isEmpty(secondBtnName)) {
                btnNo.text = secondBtnName
                btnNo.visibility = View.VISIBLE
            }else{
                btnNo.visibility = View.GONE
                btnYes.setBackgroundResource(R.drawable.roundcorner_new_app_color_one)
            }

            btnYes.setOnClickListener {
                customDialog!!.dismiss()

                if (from != null && from.equals("cancelorder", ignoreCase = true))
                    onDialogListeners.onButtonYesClick(from, params)
                else if (from != null && from.equals("StoreCheckObject", ignoreCase = true))
                    onDialogListeners.onButtonYesClick(from, paramateres)
                else
                    onDialogListeners.onButtonYesClick(from)
            }

            btnNo.setOnClickListener {
                customDialog!!.dismiss()
                onDialogListeners.onButtonNoClick(from)
            }
            try {
                if (!customDialog!!.isShowing)
                    customDialog!!.showCustomDialog()
            }catch (e: Exception){
                e.printStackTrace()
            }


        }


    }
}
