package com.wjn.customwebviewjs;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class WebViewLoadUrlActivity extends AppCompatActivity {

    private WebView webView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webviewloadurl);
        initView();
    }

    /**
     * 初始化各种View
     */

    private void initView() {
        webView = findViewById(R.id.activity_webviewloadurl_webview);
        //设置WebSettings
        WebViewSettingsUtils.setWebSettings(webView);
        //webview加载网页
        webView.loadUrl("file:///android_asset/abc.html");
        //点击事件
        textView = findViewById(R.id.activity_webviewloadurl_textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:MyOnclick()");
            }
        });
    }

}
