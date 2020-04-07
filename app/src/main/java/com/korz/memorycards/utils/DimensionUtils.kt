package ru.id_east.gm.utils

import android.content.Context
import android.graphics.Point
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.WindowManager


object DimensionUtils {

    fun getStatusBarHeight(context: Context): Int {
        val resources = context.getResources()
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0)
            resources.getDimensionPixelSize(resourceId)
        else
            Math.ceil((if (VERSION.SDK_INT >= VERSION_CODES.M) 24.0 else 25.0) * resources.getDisplayMetrics().density).toInt()
    }

    fun getNavigationBarSize(context: Context): Point {
        val appUsableSize = getAppUsableScreenSize(context)
        val realScreenSize = getRealScreenSize(context)

        // navigation bar on the side
        if (appUsableSize.x < realScreenSize.x) {
            return Point(realScreenSize.x - appUsableSize.x, appUsableSize.y)
        }

        // navigation bar at the bottom
        return if (appUsableSize.y < realScreenSize.y) {
            Point(appUsableSize.x, realScreenSize.y - appUsableSize.y)
        } else Point()

        // navigation bar is not present
    }

    fun getAppUsableScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

    fun getRealScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()

        display.getRealSize(size)

//        if (Build.VERSION.SDK_INT >= 17) {
//            display.getRealSize(size)
//        } else if (Build.VERSION.SDK_INT >= 14) {
//            try {
//                size.x = Display::class.java.getMethod("getRawWidth").invoke(display) as Int
//                size.y = Display::class.java.getMethod("getRawHeight").invoke(display) as Int
//            } catch (e: IllegalAccessException) {
//            } catch (e: InvocationTargetException) {
//            } catch (e: NoSuchMethodException) {
//            }
//
//        }

        return size
    }
}