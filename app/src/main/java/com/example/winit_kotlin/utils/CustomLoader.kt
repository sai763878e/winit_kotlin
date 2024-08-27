package com.example.winit_kotlin.utils
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.winit_kotlin.R
import java.lang.ref.WeakReference

object LoaderUtils {

    private var loaderDialog: AlertDialog? = null
    private var messageTextView: WeakReference<TextView>? = null

    fun showLoader(context: Context, message: String) {
//        if (loaderDialog == null) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_loader, null)
            val textView: TextView = dialogView.findViewById(R.id.loader_text)
            messageTextView = WeakReference(textView)

            loaderDialog = AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create()
//        }

        messageTextView?.get()?.text = message  // Update the message here

        loaderDialog?.apply {
//            if (isShowing)
//                dismiss()
            if (!isShowing) {
                show()
            }
        }
    }

    fun hideLoader() {
        loaderDialog?.apply {
            if (isShowing) {
                dismiss()
            }
        }
    }
}