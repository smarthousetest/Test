package com.example.work

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.web_fragment.*

class WebFragment : Fragment() {

    companion object {
        fun newInstance() = WebFragment()
    }

    private lateinit var viewModel: WebViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val rootView=inflater.inflate(R.layout.web_fragment, container, false)
        val webView = rootView.findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        // включаем поддержку JavaScript
        webView.settings.javaScriptEnabled = true
        // указываем страницу загрузки
        webView.loadUrl("https://www.icndb.com/api/")

        return rootView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WebViewModel::class.java)
        // TODO: Use the ViewModel
    }

}