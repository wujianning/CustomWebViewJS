package com.wjn.customwebviewjs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewAddJavaScriptInterfaceActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webviewaddjavascriptinterface);
        initView();
    }

    /**
     * 初始化各种View
     */

    private void initView() {
        webView = findViewById(R.id.activity_webviewaddjavascriptinterface_webview);
        //设置WebSettings
        WebViewSettingsUtils.setWebSettings(webView);
        //webview加载网页
        webView.loadUrl("file:///android_asset/hhh.html");
        // 添加js交互接口类，并起别名 AndroidWebView
        webView.addJavascriptInterface(new JavascriptInterface(this), "AndroidWebView");
    }

    /**
     * Native JS 接口
     */

    @SuppressLint("JavascriptInterface")
    public class JavascriptInterface {

        /**
         * 构造方法
         */

        private Context context;
        public JavascriptInterface(Context context) {
            this.context = context;
        }

        /**
         * JS 调用 Android
         */

        @android.webkit.JavascriptInterface
        public void showInfoFromJs(String name) {
            Toast.makeText(context, "JS页面输入内容：" + name, Toast.LENGTH_LONG).show();
        }

    }

}
