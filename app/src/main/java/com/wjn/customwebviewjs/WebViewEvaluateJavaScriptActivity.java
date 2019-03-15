package com.wjn.customwebviewjs;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewEvaluateJavaScriptActivity extends AppCompatActivity {

    private WebView webView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webviewevaluatejavascript);
        initView();
    }

    /**
     * 初始化各种View
     */

    private void initView() {
        webView = findViewById(R.id.activity_webviewevaluatejavascript_webview);
        //设置WebSettings
        WebViewSettingsUtils.setWebSettings(webView);
        //webview加载网页
        webView.loadUrl("file:///android_asset/def.html");
        //点击事件
        textView = findViewById(R.id.activity_webviewevaluatejavascript_textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                evaluateJavascriptMethod();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void evaluateJavascriptMethod() {
        webView.evaluateJavascript("javascript:MyOnclick()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //此处为 js 返回的结果
                Toast.makeText(WebViewEvaluateJavaScriptActivity.this, value, Toast.LENGTH_LONG).show();
            }
        });
    }


}
