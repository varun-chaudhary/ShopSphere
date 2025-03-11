package com.example.shopsphere.helper

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.shopsphere.R

object DialogHelper {

    private fun getCustomFont(context: Context): Typeface? {
        return ResourcesCompat.getFont(context, R.font.poppinsmedium)
    }

    private fun showDialog(
        context: Context,
        title: String?,
        textContent: String?,
        alertType: Int,
        textConfirm: String = "Ok",
        textConfirmSize: Float = 14f,
        onConfirm: () -> Unit = {},
    ) : SweetAlertDialog {
        val customFont = getCustomFont(context)

        val dialog = SweetAlertDialog(context, alertType)
            .setTitleText(title)
            .setConfirmText(textConfirm)
            .setContentText(textContent)
            .setConfirmClickListener {
                onConfirm()
                it.dismissWithAnimation()
            }

        dialog.setCanceledOnTouchOutside(true)

        dialog.show()

        val titleText = dialog.findViewById<TextView>(cn.pedant.SweetAlert.R.id.title_text)
        val contentText = dialog.findViewById<TextView>(cn.pedant.SweetAlert.R.id.content_text)
        val confirmButton = dialog.findViewById<Button>(cn.pedant.SweetAlert.R.id.confirm_button)
        val cancelButton = dialog.findViewById<Button>(cn.pedant.SweetAlert.R.id.cancel_button)

        titleText?.typeface = customFont
        contentText?.typeface = customFont
        confirmButton?.typeface = customFont
        confirmButton.textSize = textConfirmSize
        cancelButton?.typeface = customFont


        cancelButton.backgroundTintList = ContextCompat.getColorStateList(context, R.color.red)
        confirmButton.backgroundTintList = ContextCompat.getColorStateList(context, R.color.green)

        return dialog
    }

    fun showDialogLoading(
        context: Context,
        textContent: String?
    ): SweetAlertDialog {
        val dialog = showDialog(
            context = context,
            title = null,
            textContent = textContent,
            alertType = SweetAlertDialog.PROGRESS_TYPE
        )

        dialog.progressHelper.barColor = Color.parseColor("#29bf12")

        return dialog
    }

    fun showDialogWarning(
        context: Context,
        title: String?,
        textContent: String?,
        onConfirm: () -> Unit = {},
        onDismis: () -> Unit
    ): SweetAlertDialog {
        val dialog = showDialog(
            context = context,
            title = title,
            textContent = textContent,
            alertType = SweetAlertDialog.CUSTOM_IMAGE_TYPE,
            onConfirm = onConfirm
        )

        val contentText = dialog.findViewById<TextView>(cn.pedant.SweetAlert.R.id.content_text)
        val customImage = dialog.findViewById<ImageView>(cn.pedant.SweetAlert.R.id.custom_image)

        customImage?.layoutParams?.width = 400
        customImage?.layoutParams?.height = 400
        customImage?.requestLayout()
        contentText.textSize = 14f
        dialog.setCustomImage(R.drawable.icon_error)

        dialog.apply {
            setCancelClickListener { onDismis() }
            showCancelButton(true)
            setCancelText("Cancel")
        }

        return dialog
    }

    fun showDialogError(
        context: Context,
        title: String?,
        textContent: String?,
        onConfirm: () -> Unit = {}
    ) : SweetAlertDialog {
        return showDialog(
            context = context,
            title = title,
            textContent = textContent,
            alertType = SweetAlertDialog.ERROR_TYPE,
            onConfirm = onConfirm
        )
    }

    fun showDialogSuccess(
        context: Context,
        title: String?,
        textConfirm: String = "OK",
        textContent: String?,
        textConfirmSize: Float = 14f,
        onConfirm: () -> Unit = {}
    ): SweetAlertDialog {
        val dialog = showDialog(
            context = context,
            title = title,
            textConfirm = textConfirm,
            textContent = textContent,
            alertType = SweetAlertDialog.CUSTOM_IMAGE_TYPE,
            onConfirm = onConfirm,
            textConfirmSize = textConfirmSize
        )
        val customImage = dialog.findViewById<ImageView>(cn.pedant.SweetAlert.R.id.custom_image)
        customImage?.layoutParams?.width = 400
        customImage?.layoutParams?.height = 400
        customImage?.requestLayout()
        dialog.setCustomImage(R.drawable.icon_success)

        return dialog
    }
}