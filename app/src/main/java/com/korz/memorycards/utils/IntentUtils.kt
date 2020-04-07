package ru.id_east.gm.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast


object IntentUtils {
    @JvmStatic
    fun openUri(uri: String, context: Context) {
        val uriText = if (!uri.startsWith("http://") && !uri.startsWith("https://")) {
            "http://" + uri
        } else {
            uri
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriText))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context,"Invalid link",Toast.LENGTH_SHORT).show()
        }

    }

    @JvmStatic
    fun openFacebookUri(uri: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getFacebookPageURL(uri, context)))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    @JvmStatic
    fun openTwitterUri(user: String, context: Context) {
        var intent: Intent
        try {
            // get the Twitter app if possible
            context.getPackageManager().getPackageInfo("com.twitter.android", 0)
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=$user"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        } catch (e: Exception) {
            // no Twitter app, revert to browser
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/$user"))
        }

        context.startActivity(intent)
    }

    fun getFacebookPageURL(facebookUrl: String, context: Context): String {
        val packageManager = context.packageManager
        try {
            val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
            return if (versionCode >= 3002850) { //newer versions of fb app
                "fb://facewebmodal/f?href=$facebookUrl"
            } else { //older versions of fb app
                return facebookUrl
            }
        } catch (e: PackageManager.NameNotFoundException) {
            return facebookUrl //normal web url
        }

    }

    @JvmStatic
    fun dialPhoneNumber(number: String, context: Context) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number))
        context.startActivity(intent)
    }

    @JvmStatic
    fun buildRouteTo(latitude: Double, longitude: Double, context: Context) {
        val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }
}