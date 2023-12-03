package com.vsple.githubtestappmeraz.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.vsple.githubtestappmeraz.Models.SearchRepoModel.Item
import com.vsple.githubtestappmeraz.R
import com.vsple.githubtestappmeraz.databinding.ActivityWebViewBinding

class WebAcitivyt : AppCompatActivity() {
    lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

       var  url = intent.getStringExtra("url")

        binding.webView.webViewClient = WebViewClient()

        // this will load the url of the website
        binding.webView.loadUrl(url.toString())

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        binding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(false)
    }
}