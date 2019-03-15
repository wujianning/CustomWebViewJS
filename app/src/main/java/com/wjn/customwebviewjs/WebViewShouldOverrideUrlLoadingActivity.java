package com.wjn.customwebviewjs;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;

public class WebViewShouldOverrideUrlLoadingActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webviewshouldoverrideurlloading);
        initView();
    }

    /**
     * 初始化各种View
     */

    private void initView() {
        webView = findViewById(R.id.activity_webviewshouldoverrideurlloading_webview);
        //设置WebSettings
        WebViewSettingsUtils.setWebSettings(webView);
        //webview加载网页
        webView.loadUrl("file:///android_asset/zzz.html");
        //添加WebViewClient监听
        webView.setWebViewClient(new MyWebViewClient());
    }

    /**
     * WebViewClient 实现类
     */

    private class MyWebViewClient extends WebViewClient {

        /**
         * shouldOverrideUrlLoading方法 拦截url
         */

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            if (uri.getScheme().equals("js")) {// 如果url的协议 = 预先约定的 js 协议 就解析往下解析参数
                if (uri.getAuthority().equals("webview")) {
                    // 可以在协议上带有参数并传递到Android上
                    Set<String> collection = uri.getQueryParameterNames();
                    Iterator<String> iterable = collection.iterator();
                    String value = "";
                    while (iterable.hasNext()) {
                        value = value + uri.getQueryParameter(iterable.next()) + ";";
                    }
                    Toast.makeText(WebViewShouldOverrideUrlLoadingActivity.this, value, Toast.LENGTH_LONG).show();
                }
                view.stopLoading();
                return true;
            } else {//不是 事先规定的url直接跳转url
                view.loadUrl(url);
                return false;
            }
        }
    }

}
