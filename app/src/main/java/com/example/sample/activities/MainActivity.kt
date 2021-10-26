package com.example.sample.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.sample.R
import com.example.sample.utils.CustomDialog
import com.example.sample.utils.InternetConnectionDetector

class MainActivity : AppCompatActivity() {

    private val splash = 4000
    private var icd: InternetConnectionDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        icd = InternetConnectionDetector(this)

        Handler().postDelayed({
            if (icd!!.isConnected) {
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                finish()
            } else {
                callDialog()
            }
        },splash.toLong())
    }

    private fun callDialog() {
        val dialog = CustomDialog(this)
        dialog.connectionErrorDialog(this)
    }
}