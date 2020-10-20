package mx.com.satoritech.womandriveandroidcliente.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.lubinpc.todolist.R
import java.lang.Exception


class ProgressDialog(private val message: String = "Espere...") : DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder =
            AlertDialog.Builder(activity!!)
        val inflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.dialog_progress, null))
        builder.setCancelable(false)
        return builder.create().apply {
            setCancelable(false)
        }
    }


    override fun onStart() {
        super.onStart()
        val tvMessage = dialog!!.findViewById<TextView>(R.id.tv_message)
        tvMessage.text = message
        if (dialog == null || dialog!!.window == null) return
        val window = dialog!!.window
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            super.show(manager, tag)
        } catch (e: Exception) {
        }
    }

    companion object {
        private const val PROGRESS_CONTENT_SIZE_DP = 80
    }
}