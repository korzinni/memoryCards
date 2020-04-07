package ru.id_east.gm.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.os.Environment
import android.view.PixelCopy
import android.view.SurfaceView
import android.view.View
import java.io.File
import java.io.FileOutputStream


object ImageUtils {

    fun loadBitmapFromView(v: View): Bitmap {
        val b = Bitmap.createBitmap(
            v.width,
            v.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(b)
        v.layout(v.left, v.top, v.right, v.bottom)
        v.draw(canvas)
        return b
    }

    fun getBitmap(view: View): Bitmap {
        view.setDrawingCacheEnabled(true)
        view.buildDrawingCache(true)
        val bitmap = Bitmap.createBitmap(view.getDrawingCache())
        view.setDrawingCacheEnabled(false)
        view.destroyDrawingCache()
        return bitmap
    }


    fun getBitmapFromView(view: View, activity: Activity, callback: (Bitmap?) -> Unit) {
        activity.window?.let { window ->
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2).also {
                view.getLocationInWindow(it)
            }
            val rect = Rect(
                locationOfViewInWindow[0],
                locationOfViewInWindow[1],
                locationOfViewInWindow[0] + view.width,
                locationOfViewInWindow[1] + view.height
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    PixelCopy.request(
                        window,
                        rect,
                        bitmap,
                        { copyResult: Int ->
                            if (copyResult == PixelCopy.SUCCESS) {
                                callback(bitmap)
                            }
                            // possible to handle other result codes ...
                        }, android.os.Handler()
                    )
                } catch (e: IllegalArgumentException) {
                    // PixelCopy may throw IllegalArgumentException, make sure to handle it
                    callback(getBitmap(view))
                    e.printStackTrace()
                }
            } else {
                callback(getBitmap(view))
            }
        }
    }

    fun storeScreenShot(bm: Bitmap?, fileName: String, context: Context?): File {
        val dir = File(context?.filesDir?.absolutePath)
            if (!dir.exists())
                dir.mkdirs()
            val file = File(dir, fileName)

            try {
                val fOut = FileOutputStream(file)
                bm?.compress(Bitmap.CompressFormat.JPEG, 10, fOut)
                fOut.flush()
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return file

    }
}