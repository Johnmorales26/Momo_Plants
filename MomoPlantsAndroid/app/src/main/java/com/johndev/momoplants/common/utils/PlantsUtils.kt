package com.johndev.momoplants.common.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.Constants.ERROR_EQUALS
import com.johndev.momoplants.common.utils.Constants.ERROR_EXIST

fun openFragment(fragment: Fragment, fragmentManager: FragmentManager, containerId: Int) {
    fragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .commit()
}

fun getMsgErrorByCode(errorCode: String?): Int = when(errorCode) {
    ERROR_EXIST -> R.string.error_unique_code
    ERROR_EQUALS -> R.string.error_invalid_lenght
    else -> R.string.error_unknow
}

fun hideKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}