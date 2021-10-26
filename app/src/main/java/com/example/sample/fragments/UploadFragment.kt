package com.example.sample.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import com.example.sample.R

class UploadFragment : Fragment() {

    private var myWebView: WebView? = null
    private var mUploadMessage: ValueCallback<Uri>? = null
    private val FILECHOOSER_RESULTCODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_upload, container, false)

        myWebView = root.findViewById(R.id.wv_upload)

        myWebView!!.webViewClient = WebViewClient()
        myWebView!!.loadUrl("https://app.webandappdevelopment.com/gallery_app/login.php")
        val webSettings = myWebView!!.settings
        webSettings.javaScriptEnabled = true
        myWebView!!.settings.loadWithOverviewMode = true
        myWebView!!.settings.useWideViewPort = true

        myWebView!!.webChromeClient = object : WebChromeClient() {
            fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
    //                progressDialog.dismiss();
                view.loadUrl(url!!)
                return true
            }

            fun onPageFinished(view: WebView?, url: String?) {
    //                    progressDialog.dismiss();
            }

            fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                Toast.makeText(
                    root.context,
                    "Unable to load now. Please try again later.",
                    Toast.LENGTH_SHORT
                ).show()
                //                progressDialog.dismiss();
            }

            override fun onProgressChanged(view: WebView, progress: Int) {}
            fun openFileChooser(uploadMsg: ValueCallback<Uri>?) {
                mUploadMessage = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                activity!!.startActivityForResult(
                    Intent.createChooser(i, "File Chooser"),
                    FILECHOOSER_RESULTCODE
                )
            }

            // For Android 3.0+
            fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String?) {
                mUploadMessage = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "*/*"
                activity!!.startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE
                )
            }

            //For Android 4.1
            fun openFileChooser(
                uploadMsg: ValueCallback<Uri>?,
                acceptType: String?,
                capture: String?
            ) {
                mUploadMessage = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                activity!!.startActivityForResult(
                    Intent.createChooser(i, "File Chooser"),
                    FILECHOOSER_RESULTCODE
                )
            }
        }

        return root
    }


}