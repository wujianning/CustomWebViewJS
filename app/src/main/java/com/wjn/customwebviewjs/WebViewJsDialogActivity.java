package com.wjn.customwebviewjs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

public class WebViewJsDialogActivity extends AppCompatActivity {

    private TextView textView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webviewjsdialog);
        initView();
    }

    /**
     * 初始化各种View
     */

    private void initView() {
        webView = findViewById(R.id.activity_webviewjsdialog_webview);
        //设置WebSettings
        WebViewSettingsUtils.setWebSettings(webView);
        //webview加载网页
        webView.loadUrl("file:///android_asset/kkk.html");
        //添加WebChromeClient监听
        webView.setWebChromeClient(new MyWebChromeClient());
        //TextView点击事件
        textView = findViewById(R.id.activity_webviewjsdialog_textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Android传递的参数666";
                webView.loadUrl("javascript:showAndroidToJsWithParameter('" + msg + "')");
            }
        });
    }

    /**
     * WebChromeClient 实现类
     */

    private class MyWebChromeClient extends WebChromeClient {

        /**
         * onJsAlert方法 拦截JS的alert对话框
         */

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            //创建一个Builder来显示网页中的对话框
            new AlertDialog.Builder(WebViewJsDialogActivity.this)
                    .setTitle("Alert对话框")
                    .setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    }).setCancelable(false).show();
            return true;
        }

        /**
         * onJsConfirm方法 拦截JS的confirm对话框
         */

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            new AlertDialog.Builder(WebViewJsDialogActivity.this)
                    .setTitle("Confirm对话框")
                    .setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    }).setCancelable(false).show();
            return true;
        }

        /**
         * onJsPrompt方法 拦截JS的prompt对话框
         */

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
            //①获得一个LayoutInflater对象factory,加载指定布局成相应对象
            final LayoutInflater inflater = LayoutInflater.from(WebViewJsDialogActivity.this);
            final View myview = inflater.inflate(R.layout.prompt_view, null);
            //设置TextView对应网页中的提示信息,edit设置来自于网页的默认文字
            ((TextView) myview.findViewById(R.id.textview)).setText(message);
            ((EditText) myview.findViewById(R.id.edittext)).setText(defaultValue);
            //定义对话框上的确定按钮
            new AlertDialog.Builder(WebViewJsDialogActivity.this).setTitle("Prompt对话框").setView(myview)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //单击确定后取得输入的值,传给网页处理
                            String value = ((EditText) myview.findViewById(R.id.edittext)).getText().toString();
                            result.confirm(value);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    }).show();
            return true;
        }
    }

}
