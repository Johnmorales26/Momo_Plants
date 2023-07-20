package com.johndev.momoplants.common.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.Notification
import com.johndev.momoplants.common.utils.Constants.ERROR_EXIST
import es.dmoral.toasty.Toasty

fun openFragment(fragment: Fragment, fragmentManager: FragmentManager, containerId: Int) {
    fragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .commit()
}

fun printErrorToast(msgRes: Int? = null, msgText: String? = null, context: Context) = if (msgRes != null) {
    Toasty.error(context, msgRes, Toast.LENGTH_SHORT, true).show()
} else {
    msgText?.let { Toasty.error(context, msgText, Toast.LENGTH_SHORT, true).show() }
}

fun printSuccessToast(msgRes: Int? = null, msgText: String? = null, context: Context) = if (msgRes != null) {
    Toasty.success(context, msgRes, Toast.LENGTH_SHORT, true).show()
} else {
    msgText?.let { Toasty.success(context, msgText, Toast.LENGTH_SHORT, true).show() }
}

fun printInfoToast(msgRes: Int? = null, msgText: String? = null, context: Context) = if (msgRes != null) {
    Toasty.info(context, msgRes, Toast.LENGTH_SHORT, true).show()
} else {
    msgText?.let { Toasty.info(context, msgText, Toast.LENGTH_SHORT, true).show() }
}

fun printNormalToast(msgRes: Int? = null, msgText: String? = null, context: Context) = if (msgRes != null) {
    Toasty.normal(context, msgRes, Toast.LENGTH_SHORT).show()
} else {
    msgText?.let { Toasty.normal(context, msgText, Toast.LENGTH_SHORT).show() }
}

fun printSnackbarMsg(view: View, msgRes: Int, context: Context) =
    Snackbar.make(view, context.getString(msgRes), Snackbar.LENGTH_SHORT).show()

fun validFields(fields: List<Pair<TextInputEditText, TextInputLayout>>, context: Context): Boolean {
    var isValid = true
    for ((field, layout) in fields) {
        if (field.text.isNullOrEmpty()) {
            layout.run {
                error = context.getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            layout.error = null
        }
    }
    return isValid
}

fun Toolbar.setupNavigationTo(fragment: Fragment, fragmentManager: FragmentManager) {
    setNavigationOnClickListener {
        openFragment(
            fragment = fragment,
            fragmentManager = fragmentManager,
            containerId = R.id.container
        )
    }
}

fun setupimageUrl(imgCover: ImageView, imgRes: Int) {
    imgCover.load(imgRes) {
        crossfade(true)
        placeholder(R.drawable.ic_broken_image)
        //transformations(CircleCropTransformation())
    }
}

fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun getMsgErrorByCode(errorCode: String?): Int = when (errorCode) {
    ERROR_EXIST -> R.string.error_unique_code
    else -> R.string.error_unknow
}

fun hideKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun onSetupStatusNotification(context: Context, status: Int): String {
    return when (status - 1) {
        0 -> context.getString(R.string.order_status_ordered)
        1 -> context.getString(R.string.order_status_preparing)
        2 -> context.getString(R.string.order_status_send)
        3 -> context.getString(R.string.order_status_delivered)
        else -> context.getString(R.string.order_status_unknown)
    }
}

fun lauchNotification(activity: Activity, notification: Notification) {
    executeOrRequestPermission(activity) {
        simpleNotification(activity, notification)
    }
}

fun lauchNotificationWithReciber(activity: Activity, notification: Notification) {
    executeOrRequestPermission(activity) {
        buttonNotification(activity, notification)
    }
}