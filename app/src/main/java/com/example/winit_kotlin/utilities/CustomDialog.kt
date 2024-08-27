package com.example.winit_kotlin.utilities

import android.app.Activity
import com.example.winit_kotlin.R
import android.app.Dialog
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.Window


/** Class to create the Custom Dialog **/
class CustomDialog : Dialog {

    private var isCancellable: Boolean = true

    /**
     * Primary constructor
     * @param context
     * @param view
     */
    constructor(context: Context, view: View) : super(context, R.style.Dialog) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view)
    }

    /**
     * Secondary constructor
     * @param context
     * @param view
     * @param lpW
     * @param lpH
     */
    constructor(context: Context, view: View, lpW: Int, lpH: Int) : this(context, view, lpW, lpH, true)

    /**
     * Secondary constructor with isCancellable parameter
     * @param context
     * @param view
     * @param lpW
     * @param lpH
     * @param isCancellable
     */
    constructor(context: Context, view: View, lpW: Int, lpH: Int, isCancellable: Boolean) : super(context, R.style.Dialog) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view, LayoutParams(lpW, lpH))
        this.isCancellable = isCancellable
    }

    /**
     * Secondary constructor with isCancellable and style parameters
     * @param context
     * @param view
     * @param lpW
     * @param lpH
     * @param isCancellable
     * @param style
     */
    constructor(context: Context, view: View, lpW: Int, lpH: Int, isCancellable: Boolean, style: Int) : super(context, style) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view, LayoutParams(lpW, lpH))
        this.isCancellable = isCancellable
    }

    override fun onBackPressed() {
        if (isCancellable) {
            super.onBackPressed()
        }
    }

    override fun setCanceledOnTouchOutside(cancel: Boolean) {
        super.setCanceledOnTouchOutside(cancel)
    }

    fun showCustomDialog() {
        try {
            try {
                // Extract the base context from the ContextThemeWrapper
                val activity = when (context) {
                    is Activity -> context as Activity
                    is ContextThemeWrapper -> (context as ContextThemeWrapper).baseContext as? Activity
                    else -> null
                }

                if (activity != null && !activity.isFinishing) {
                    show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
