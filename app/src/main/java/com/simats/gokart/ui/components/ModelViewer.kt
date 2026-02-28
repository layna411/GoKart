package com.simats.gokart.ui.components

import android.content.ClipData
import android.content.Intent
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import androidx.webkit.WebViewAssetLoader
import java.io.File

@Composable
fun ModelViewer(modelName: String, modifier: Modifier = Modifier) {
    AndroidView(modifier = modifier, factory = { context ->
        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(context))
            .build()

        // Copy the model from assets to a cache directory for the AR viewer
        val modelsDir = File(context.cacheDir, "models")
        if (!modelsDir.exists()) {
            modelsDir.mkdirs()
        }
        val modelFile = File(modelsDir, modelName)
        context.assets.open(modelName).use { input ->
            modelFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        val arModelUri = FileProvider.getUriForFile(
            context,
            "com.simats.gokart.provider", // Make sure this matches your provider authorities
            modelFile
        )

        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = object : WebViewClient() {
                override fun shouldInterceptRequest(
                    view: WebView,
                    request: WebResourceRequest
                ): WebResourceResponse? {
                    return assetLoader.shouldInterceptRequest(request.url)
                }

                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                    val url = request.url
                    if (url.scheme == "intent") {
                        try {
                            val intent = Intent.parseUri(url.toString(), Intent.URI_INTENT_SCHEME)
                            // Grant permission to the receiving app
                            intent.clipData = ClipData.newUri(context.contentResolver, "Go-Kart Model", arModelUri)
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            context.startActivity(intent)
                            return true
                        } catch (e: Exception) {
                            // Could not parse the intent or start the activity
                        }
                    }
                    return false
                }
            }
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = false
            settings.allowContentAccess = false

            var htmlData = context.assets.open("model_viewer.html").bufferedReader().use { it.readText() }
            val modelUrl = "https://appassets.androidplatform.net/assets/$modelName"

            htmlData = htmlData.replace("MODEL_SRC_PLACEHOLDER", modelUrl)
            htmlData = htmlData.replace("AR_SRC_PLACEHOLDER", arModelUri.toString())

            loadDataWithBaseURL("https://appassets.androidplatform.net", htmlData, "text/html", "UTF-8", null)
        }
    })
}
