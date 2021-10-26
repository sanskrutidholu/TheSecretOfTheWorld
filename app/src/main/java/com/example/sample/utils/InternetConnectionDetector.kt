package com.example.sample.utils

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class InternetConnectionDetector(var context: Context) {
    val isConnected: Boolean
        get() {
            val connectivity =
                context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                val info = connectivity.activeNetworkInfo
                if (info != null) {
                    if (info.state == NetworkInfo.State.CONNECTED || info.state == NetworkInfo.State.CONNECTING) {
                        return true
                    }
                }
            }
            return false
        }

}