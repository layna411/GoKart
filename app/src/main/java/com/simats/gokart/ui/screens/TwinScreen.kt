package com.simats.gokart.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simats.gokart.viewmodel.DigitalTuneViewModel
import java.net.URISyntaxException

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun TwinScreen(
    vehicleType: String,
    viewModel: DigitalTuneViewModel = viewModel()
) {
    val context = LocalContext.current
    val url = "" // TODO: Replace with your actual URL

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webChromeClient = WebChromeClient()
            webViewClient = object : androidx.webkit.WebViewClientCompat() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    val url = request.url.toString()
                    if (url.startsWith("intent://")) {
                        try {
                            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                            if (intent != null) {
                                view.context.startActivity(intent)
                                return true
                            }
                        } catch (e: URISyntaxException) {
                            e.printStackTrace()
                        }
                    } else if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, request.url)
                            view.context.startActivity(intent)
                            return true
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    return false
                }
            }
            settings.javaScriptEnabled = true
            settings.allowFileAccess = true
            settings.allowContentAccess = true
            loadUrl(url)
        }
    }, modifier = Modifier.fillMaxSize())
}
