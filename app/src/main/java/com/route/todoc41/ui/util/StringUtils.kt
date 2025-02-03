package com.route.todoc41.ui.util

import android.content.Intent
import android.os.Build
import android.os.Parcelable

fun getFormattedTime(hour:Int,minutes:Int):String{
    val minutesString = if (minutes==0) "00" else minutes.toString()
    return "${getHourIn12(hour)}:$minutesString ${getAmPm(hour)}"
}

fun getHourIn12(hour: Int):Int{
    return if (hour>12) hour - 12 else if (hour==0) 12 else hour
}

fun getAmPm(hour: Int):String{
    return if (hour<12) "AM" else "PM"

}
inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}