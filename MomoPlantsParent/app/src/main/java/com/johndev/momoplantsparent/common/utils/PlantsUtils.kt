package com.johndev.momoplantsparent.common.utils

import android.content.Context
import android.text.Editable
import android.widget.Toast
import es.dmoral.toasty.Toasty

fun printMessage(msgRes: Pair<Int, ToastType>? = null, msgText: Pair<String, ToastType>? = null, context: Context) {
    msgRes?.let {
        when(it.second) {
            ToastType.Success -> { printSuccessToast(it.first, context = context) }
            ToastType.Normal -> { printNormalToast(it.first, context = context) }
            ToastType.Error -> { printErrorToast(it.first, context = context) }
            ToastType.Info -> { printInfoToast(it.first, context = context) }
        }
    } ?: {
        when(msgText!!.second) {
            ToastType.Success -> { printSuccessToast(msgText = msgText.first, context = context)}
            ToastType.Normal -> { printNormalToast(msgText = msgText.first, context = context)}
            ToastType.Error -> { printErrorToast(msgText = msgText.first, context = context)}
            ToastType.Info -> { printInfoToast(msgText = msgText.first, context = context)}
        }
    }
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

fun printToastMsg(msgRes: Int, context: Context) =
    Toast.makeText(context, context.getText(msgRes), Toast.LENGTH_SHORT).show()

fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)