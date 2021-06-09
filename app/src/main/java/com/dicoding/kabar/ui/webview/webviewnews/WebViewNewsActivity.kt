package com.dicoding.kabar.ui.webview.webviewnews

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.kabar.databinding.ActivityWebViewNewsBinding

class WebViewNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewNewsBinding

    companion object {
        const val EXTRA_WV = "extra_wv"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init(){
        val extras = intent.extras

        if(extras != null){
            val url = extras.getString(EXTRA_WV)
            binding.webViewNews.settings.javaScriptEnabled = true
            binding.webViewNews.loadUrl(url.toString())
        }

    }
}