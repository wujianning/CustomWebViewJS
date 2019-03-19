package com.wjn.customwebviewjs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebViewActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private X5WebView x5WebView;
    private String url = "file:///android_asset/hhh.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x5webview);
        initHardwareAccelerate();
        initView();
    }

    /**
     * 启用硬件加速
     */

    private void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 初始化各种View
     * */

    private void initView() {
        progressBar=findViewById(R.id.activity_x5webview_progressbar);
        x5WebView = findViewById(R.id.activity_x5webview_x5webview);
        x5WebView.loadUrl(url);
        x5WebView.setWebViewClient(new myWebViewClient());
        x5WebView.setWebChromeClient(new myWebChromeClient());
        // 添加js交互接口类，并起别名 AndroidWebView
        x5WebView.addJavascriptInterface(new JavascriptInterface(this), "AndroidWebView");
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

    /**
     * WebViewClient监听
     * */

    private class myWebViewClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            webView.loadUrl(s);
            return true;
        }
    }

    /**
     * WebChromeClient监听
     * */

    private class myWebChromeClient extends WebChromeClient{

        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (i == 100) {
                progressBar.setVisibility(View.GONE);//加载完网页进度条消失
            } else {
                progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                progressBar.setProgress(i);//设置进度值
            }
        }
    }

    /**
     * onKeyDown方法
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (x5WebView != null && x5WebView.canGoBack()) {
                x5WebView.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * onDestroy方法
     * */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null!=x5WebView){
            x5WebView.destroy();//释放资源
        }
    }

}
