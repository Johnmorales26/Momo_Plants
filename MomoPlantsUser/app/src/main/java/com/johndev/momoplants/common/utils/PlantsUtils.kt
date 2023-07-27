package com.johndev.momoplants.common.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.Notification
import es.dmoral.toasty.Toasty
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

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

fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun onSetupStatusNotification(context: Context, status: Int): String {
    return when (status - 1) {
        0 -> context.getString(R.string.order_status_ordered)
        1 -> context.getString(R.string.order_status_preparing)
        2 -> context.getString(R.string.order_status_send)
        3 -> context.getString(R.string.order_status_delivered)
        else -> context.getString(R.string.order_status_unknown)
    }
}

fun launchNotification(activity: Activity, notification: Notification) {
    executeOrRequestPermission(activity) {
        simpleNotification(activity, notification)
    }
}

fun launchNotificationWithReceiver(activity: Activity, notification: Notification) {
    executeOrRequestPermission(activity) {
        buttonNotification(activity, notification)
    }
}

fun AppCompatActivity.checkSelfPermissionCompat(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission)

fun AppCompatActivity.shouldShowRequestPermissionRationaleCompat(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun AppCompatActivity.requestPermissionsCompat(
    permissionsArray: Array<String>,
    requestCode: Int
) {
    ActivityCompat.requestPermissions(this, permissionsArray, requestCode)
}



fun readUrlFile(url: String): String? {
    var data: String? = null
    var iStream: InputStream? = null
    var urlConnection: HttpURLConnection? = null
    try {
        urlConnection = URL(url).openConnection() as HttpURLConnection?
        urlConnection?.connect()
        iStream = urlConnection?.inputStream
        val br = BufferedReader(InputStreamReader(iStream))
        val sb = StringBuilder()
        var line: String?
        while (br.readLine().also { line = it } != null) {
            sb.append(line)
        }
        data = sb.toString()
        br.close()
    } catch (e: Exception) {
        Log.w("", "Exception while downloading url: $e")
    } finally {
        iStream?.close()
        urlConnection?.disconnect()
    }
    return data
}

fun buildAlertDialog(context: Context, resTitle: Int, resMessage: Int): AlertDialog {
    val alertDialog = AlertDialog.Builder(context).create()
    alertDialog.setTitle(context.getString(resTitle))
    alertDialog.setMessage(context.getString(resMessage))
    alertDialog.setCancelable(false)
    return alertDialog
}

fun openFragment(action: NavDirections, activity: Activity, navController: NavController) {
    val bottomNavigation = activity.findViewById<BottomNavigationView>(R.id.bottom_navigation)
    navController.addOnDestinationChangedListener { _, destination, _ ->
        when(destination.id) {
            R.id.navigation_chat -> bottomNavigation.visibility = View.GONE
            R.id.navigation_track -> bottomNavigation.visibility = View.GONE
            R.id.navigation_details -> bottomNavigation.visibility = View.GONE
            else -> bottomNavigation.visibility = View.VISIBLE
        }
    }
    navController.navigate(action)
}