package com.wjn.customwebviewjs;

import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewSettingsUtils {

    public static void setWebSettings(WebView webView){
        if(null==webView){
            return ;
        }
        WebSettings settings=webView.getSettings();
        //设置setting属性
        settings.setJavaScriptEnabled(true);//支持js
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//设置缓存方式  不使用缓存只从网络获取数据
        settings.setDomStorageEnabled(true);//开启DOM storage API功能（HTML5 提供的一种标准的接口，主要将键值对存储在本地，在页面加载完毕后可以通过 JavaScript 来操作这些数据。）
        settings.setDefaultTextEncodingName("utf-8");//设置默认编码
        settings.setUseWideViewPort(false);//将图片调整到适合webview的大小
        settings.setSupportZoom(true);//支持缩放
        settings.setDisplayZoomControls(false);//设定缩放控件隐藏
        settings.setAllowFileAccess(true);//设置可以访问文件
        settings.setBuiltInZoomControls(true);//设置支持缩放
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        settings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true);//支持自动加载图片
    }

}
