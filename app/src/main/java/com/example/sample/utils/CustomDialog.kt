package com.example.sample.utils

import android.app.AlertDialog
import android.content.Context

class CustomDialog(private val myContext: Context) {
    fun serverErrorDialog(context: Context?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Attention Required!!")
            .setMessage("We are unable to connect with server please try again later.")
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { dialog, which -> dialog.cancel() }
        val dialog = builder.create()
        dialog.show()
    }

    fun connectionErrorDialog(context: Context?) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Please check your internet connection.")
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { dialog, which -> dialog.cancel() }
        val dialog = builder.create()
        dialog.show()
    }

    fun showCustomDialog(
        context: Context?,
        title: String?,
        message: String?,
        cancelable: Boolean?,
        positiveButton: String?
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMessage(message)
            .setCancelable(cancelable!!)
            .setPositiveButton(
                positiveButton
            ) { dialog, which -> dialog.cancel() }
        val dialog = builder.create()
        dialog.show()
    }
}
