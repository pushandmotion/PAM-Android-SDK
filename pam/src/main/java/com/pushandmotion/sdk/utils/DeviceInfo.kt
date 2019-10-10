package com.pushandmotion.sdk.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import java.util.*

class DeviceInfo {
    companion object{

        private val DEVICE_UUID = "deviceUUID"
        private val DEVICE_PUSH_TOKEN = "devicePushToken"

        fun getUUID(context: Context): String{
           val pref = context.getSharedPreferences("pam", Context.MODE_PRIVATE)
            var uuid =  pref.getString(DEVICE_UUID, "")
            if(uuid == ""){
                uuid = UUID.randomUUID().toString()
                pref.edit().also {
                    it.putString(DEVICE_UUID, uuid)
                }.commit()
            }
            return uuid!!
        }

        fun getDeviceModel(): String = Build.MODEL
        fun getAndroidVersion() = Build.VERSION.SDK_INT.toString()
        fun getAppID(context: Context) = context.packageName

        fun getAppVersion(context: Context):String{
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                context.packageManager.getPackageInfo(context.packageName,0).longVersionCode.toString()
            }else{
                context.packageManager.getPackageInfo(context.packageName,0).versionCode.toString()
            }
        }

        @SuppressLint("ServiceCast")
        fun getScreenWidth(context: Context):Int{
            val displayMetrics = DisplayMetrics()
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }

        @SuppressLint("ServiceCast")
        fun getScreenHeight(context: Context):Int{
            val displayMetrics = DisplayMetrics()
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }

        fun isDebugMode(context: Context):Boolean{
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                0 != context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
            } else {
               false
            }
        }

    }
}