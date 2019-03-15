package com.wjn.customwebviewjs;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
        uriMethod();
    }

    private void uriMethod(){
        String url="http://api.svipmovie.com/front/columns/getVideoList.do?catalogId=402834815584e463015584e539330016&pnum=1";
        Uri uri=Uri.parse(url);
        Log.d("TAG","全部链接:"+url);
        Log.d("TAG","-----------------------------");

        String scheme=uri.getScheme();// http
        Log.d("TAG","scheme:"+scheme);
        Log.d("TAG","-----------------------------");

        String authority=uri.getAuthority();// api.svipmovie.com
        Log.d("TAG","authority:"+authority);
        Log.d("TAG","-----------------------------");

        String encodedpath=uri.getEncodedPath();// /front/columns/getVideoList.do
        Log.d("TAG","encodedpath:"+encodedpath);
        Log.d("TAG","-----------------------------");

        String encodedquery=uri.getEncodedQuery();// catalogId=402834815584e463015584e539330016&pnum=1
        Log.d("TAG","encodedquery:"+encodedquery);
        Log.d("TAG","-----------------------------");

        //全部参数Key
        Set<String> collection = uri.getQueryParameterNames();
        Iterator<String> iterable = collection.iterator();
        String key = "";
        while (iterable.hasNext()) {
            key = key + iterable.next() + "###";
        }
        Log.d("TAG","key:"+key);
        Log.d("TAG","-----------------------------");

        //全部参数Value
        Set<String> collections = uri.getQueryParameterNames();
        Iterator<String> iterables = collections.iterator();
        String value = "";
        while (iterables.hasNext()) {
            value = value + uri.getQueryParameter(iterables.next()) + "###";
        }
        Log.d("TAG","value:"+value);
        Log.d("TAG","-----------------------------");

    }

    /**
     * 初始化各种View
     */

    private void initView() {
        TextView textView1=findViewById(R.id.activity_webview_textview1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WebViewActivity.this,WebViewLoadUrlActivity.class);
                startActivity(intent);
            }
        });

        TextView textView2=findViewById(R.id.activity_webview_textview2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WebViewActivity.this,WebViewEvaluateJavaScriptActivity.class);
                startActivity(intent);
            }
        });

        TextView textView3=findViewById(R.id.activity_webview_textview3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WebViewActivity.this,WebViewAddJavaScriptInterfaceActivity.class);
                startActivity(intent);
            }
        });

        TextView textView4=findViewById(R.id.activity_webview_textview4);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WebViewActivity.this,WebViewShouldOverrideUrlLoadingActivity.class);
                startActivity(intent);
            }
        });

        TextView textView5=findViewById(R.id.activity_webview_textview5);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WebViewActivity.this,WebViewJsDialogActivity.class);
                startActivity(intent);
            }
        });

    }

}
