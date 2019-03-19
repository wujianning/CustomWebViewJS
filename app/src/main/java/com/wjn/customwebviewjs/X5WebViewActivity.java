package com.wjn.customwebviewjs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebViewActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private X5WebView x5WebView;
    private String homeurl = "https://blog.csdn.net/weixin_37730482";

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
        x5WebView.loadUrl(homeurl);
        x5WebView.setWebViewClient(new myWebViewClient());
        x5WebView.setWebChromeClient(new myWebChromeClient());
    }

    /**
     * WebViewClient监听
     * */

    private class myWebViewClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            webView.loadUrl(s);
            Log.d("TAG","homeurl----:"+homeurl);
            Log.d("TAG","s----:"+s);
            Log.d("TAG","-------------------------------");
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
