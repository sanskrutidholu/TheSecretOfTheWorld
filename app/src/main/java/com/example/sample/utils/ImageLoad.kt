package com.example.sample.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

open class ImageLoad(private val url: String, private val imageView: ImageView) :
    AsyncTask<Void?, Void?, Bitmap?>() {
    protected override fun doInBackground(vararg p0: Void?): Bitmap? {
        try {
            val urlConnection = URL(url)
            val connection = urlConnection
                .openConnection() as HttpsURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            return BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        imageView.setImageBitmap(result)
    }
}
