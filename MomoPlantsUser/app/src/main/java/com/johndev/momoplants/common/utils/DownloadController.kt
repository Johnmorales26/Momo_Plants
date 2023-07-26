package com.johndev.momoplants.common.utils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.Constants.URL_APP_DOWNLOAD
import dagger.hilt.android.qualifiers.ApplicationContext
import firebase.com.protolitewrapper.BuildConfig
import java.io.File
import javax.inject.Inject

class DownloadController(private val context: Context) {

    companion object {
        private const val FILE_NAME = "appUpdate.apk"
        private const val FILE_BASE_PATH = "file://"
        private const val MIME_TYPE = "application/vnd.android.package-archive"
        private const val PROVIDER_PATH = ".provider"
        private const val APP_INSTALL_PATH = "\"application/vnd.android.package-archive\""
    }

    fun enqueueDownload() {
        var destination =
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/"
        destination += FILE_NAME
        val uri = Uri.parse("$FILE_BASE_PATH$destination")
        val file = File(destination)
        if (file.exists()) file.delete()
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(URL_APP_DOWNLOAD)
        val request = DownloadManager.Request(downloadUri)
        request.apply {
            setMimeType(MIME_TYPE)
            setTitle(context.getString(R.string.title_file_download))
            setDescription(context.getString(R.string.downloading))
            setDestinationUri(uri)
        }
        showInstallOption(destination, uri)
        downloadManager.enqueue(request)
        Toast.makeText(context, R.string.downloading, Toast.LENGTH_LONG).show()
    }

    private fun showInstallOption(
        destination: String,
        uri: Uri
    ) {

        val onComplete = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context,
                intent: Intent
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val contentUri = FileProvider.getUriForFile(
                        context,
                        BuildConfig.APPLICATION_ID + PROVIDER_PATH,
                        File(destination)
                    )
                    val install = Intent(Intent.ACTION_VIEW)
                    install.apply {
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
                        data = contentUri
                    }
                    context.let {
                        it.startActivity(install)
                        it.unregisterReceiver(this)
                    }
                } else {
                    val install = Intent(Intent.ACTION_VIEW)
                    install.apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        setDataAndType(
                            uri,
                            APP_INSTALL_PATH
                        )
                    }
                    context.let {
                        it.startActivity(install)
                        it.unregisterReceiver(this)
                    }
                }
            }
        }
        context.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }
}