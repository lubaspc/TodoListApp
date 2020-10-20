package mx.com.satoritech.womandriveandroidcliente.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lubinpc.todolist.utils.PROGRESS_DIALOG

object Dialogs {
    fun alert(
        context: Context,
        title: String,
        message: String
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok", null)
            .show()
    }

    fun alert(
        context: Context,
        title: String,
        message: String,
        cb: () -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                "Ok"
            ) { _,_ -> cb() }
            .show()
    }

    fun alert(
        context: Context,
        message: String
    ) {
        MaterialAlertDialogBuilder(context)
            .setMessage(message)
            .setPositiveButton("Ok", null)
            .show()
    }

    fun alert(
        context: Context?,
        message: String?,
        cb: () -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setMessage(message)
            .setPositiveButton(
                "Ok"
            ) { _, _-> cb() }
            .show()
    }

    fun alert(
        context: Context,
        message: String,
        onDismissListener: DialogInterface.OnDismissListener
    ) {
        MaterialAlertDialogBuilder(context)
            .setMessage(message)
            .setPositiveButton("Ok", null)
            .setOnDismissListener(onDismissListener)
            .show()
    }

    fun progress(
        mContext: Context,
        @StringRes message: Int
    ): ProgressDialog {
        val pDialog = ProgressDialog(mContext)
        pDialog.setTitle(null)
        pDialog.setMessage(mContext.getString(message))
        pDialog.setCancelable(false)
        pDialog.show()
        return pDialog
    }

    fun progressDialog(fragmentManager: FragmentManager
    ) = ProgressDialog().apply {
        isCancelable = false
        show(fragmentManager, PROGRESS_DIALOG)
    }

    fun alert(ctx: Context) {
        MaterialAlertDialogBuilder(ctx)
            .setMessage("Ocurrió un error al obtener la información, inténtalo mas tarde")
            .setPositiveButton("Ok", null)
            .show()
    }

    fun alert(
        ctx: Context,
        cb: () -> Unit
    ) {
        MaterialAlertDialogBuilder(ctx)
            .setMessage("Ocurrió un error al obtener la información, inténtalo mas tarde")
            .setPositiveButton(
                "Ok"
            ) { _,_ -> cb() }
            .show()
    }
}
