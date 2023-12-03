package com.vsple.githubtestappmeraz.Util

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.vsple.githubtestappmeraz.R
import pl.droidsonroids.gif.GifImageView

object Utility {
    fun networkStatusCheck(context: Context?): Boolean {
        return if (context != null) {
            if (isWifiAvailable(context) || isMobileNetworkAvailable(context)) {
                true
            } else {
                false
            }
        } else {
            false
        }
    }
    fun isWifiAvailable(context: Context?): Boolean {
        if (context != null) {
            val connectManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI && activeNetworkInfo.isConnected
        }
        return false
    }

    fun isMobileNetworkAvailable(context: Context): Boolean {
        val connectManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE && activeNetworkInfo.isConnected
    }

    fun showShortMsg(context: Context, message: String) {
        Toast.makeText(
            context, message, Toast.LENGTH_SHORT
        ).show()
    }

    fun showLoader(gifImageView: GifImageView) {
        gifImageView.visibility = View.VISIBLE
    }

    fun hideLoader(gifLoader: GifImageView) {
        gifLoader.visibility = View.GONE
    }
}