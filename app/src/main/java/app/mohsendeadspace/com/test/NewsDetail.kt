package app.mohsendeadspace.com.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetail : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)


        webView.settings.javaScriptEnabled=true
        webView.webChromeClient= WebChromeClient()
        webView.webViewClient=object :WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {

            }

        }
        if(intent !=null)
            if(!intent.getStringExtra("webURL").isEmpty())
                 webView.loadUrl(intent.getStringExtra("webURL"))
    }
}
