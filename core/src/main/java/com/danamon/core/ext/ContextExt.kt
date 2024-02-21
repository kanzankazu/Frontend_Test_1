package com.danamon.core.ext

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

inline fun <reified T : AppCompatActivity> Context?.makeIntent() =
    Intent(this, T::class.java)

fun Context.delayFun(delayMillis: Long = 500, r: () -> Unit): Boolean {
    return Handler(Looper.getMainLooper()).postDelayed(r, delayMillis)
}

fun Context.simpleToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.openEmail(emailAddress: String, emailSubject: String = "", emailBodyMessage: String = "", emailAttachment: File? = null) {
    try {
        val emailIntent = Intent(Intent.ACTION_SENDTO)

        emailIntent.data = Uri.parse("mailto:") // only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBodyMessage)

        /*Attachment*/
        emailAttachment?.let {
            /*val root = Environment.getExternalStorageDirectory()
            val pathToMyAttachedFile = "temp/attachement.xml"
            val file = File(root, pathToMyAttachedFile)*/
            val file = it
            if (!file.exists() || !file.canRead()) {
                simpleToast("Email Attachment Not exist / Can't read")
                return
            }
            val uri = Uri.fromFile(file)
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri)
        }

        startActivity(Intent.createChooser(emailIntent, "Send Email"))
    } catch (e: ActivityNotFoundException) {
        simpleToast("There are no email client installed on your device.")
    }
}

fun Context.openLinkToWeb(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}
