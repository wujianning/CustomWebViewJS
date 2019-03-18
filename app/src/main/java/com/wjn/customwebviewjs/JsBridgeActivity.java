package com.wjn.customwebviewjs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

public class JsBridgeActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView111;
    private BridgeWebView bridgeWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsbridge);
        initView();
    }

    /**
     * 初始化各种View
     */

    private void initView() {
        textView=findViewById(R.id.activity_jsbridge_textview1);
        textView111=findViewById(R.id.activity_jsbridge_textview111);
        bridgeWebView=findViewById(R.id.activity_jsbridge_bridgewebview);
        bridgeWebView.setWebChromeClient(new myWebChromeClient());

        //Android 通过 JSBridge 调用 JS
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * callHandler方法 Android 调用JS
                 * 参数1 handlerName：JS中规定的方法 Android JS 关于该方法名要一致
                 * 参数2 data：Android传递给JS的参数
                 * 参数3 callBack：回调 JS返回给Android的返回值
                 * */

                bridgeWebView.callHandler("functionInJs", "JS你好，这是我Android传递给你的数据呀！！！", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Toast.makeText(JsBridgeActivity.this, data, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //Android 通过 JSBridge 调用 JS 111
        textView111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bridgeWebView.callHandler("functionInJs111", "JS你好，这是我Android传递给你的数据呀！！！111", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Toast.makeText(JsBridgeActivity.this, data, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //JS 通过 JSBridge 调用 Android
        bridgeWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //JS传递给Android
                Toast.makeText(JsBridgeActivity.this,  data, Toast.LENGTH_LONG).show();
                //Android返回给JS的消息
                function.onCallBack("JS你好，这是我Android传递给你的数据呀！！！");
            }
        });

        bridgeWebView.loadUrl("file:///android_asset/lll.html");
    }

    /**
     * WebChromeClient 实现类
     * */

    public class myWebChromeClient extends WebChromeClient{

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            //创建一个Builder来显示网页中的对话框
            new AlertDialog.Builder(JsBridgeActivity.this)
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
    }

}
