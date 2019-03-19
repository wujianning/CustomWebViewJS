package com.wjn.customwebviewjs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class WebViewAndroidJSActivity extends AppCompatActivity {

    private WebView webView;
    private ToastUtils toast;
    private String pickPhotoFuncNameName;//手机相册 JS传递
    private String takePhotofuncName;//手机拍照 JS传递
    private String mPath;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webviewandroidjs);
        initView();
    }

    /**
     * 初始化各种View
     */

    private void initView() {
        toast=new ToastUtils(this);
        webView = findViewById(R.id.activity_webviewandroidjs_webview);
        //设置WebSettings
        WebViewSettingsUtils.setWebSettings(webView);
        //添加WebViewClient事件
        webView.setWebViewClient(new myWebViewClient());
        // 添加js交互接口类，并起别名 AndroidWebView
        webView.addJavascriptInterface(new JavascriptInterface(this), "App");
        //webview加载网页
        webView.loadUrl("file:///android_asset/index1.html");
    }

    /**
     * WebViewClient监听
     */

    private class myWebViewClient extends WebViewClient {

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            WebResourceResponse webResourceResponse = super.shouldInterceptRequest(view, url);
            if (url == null) {
                return webResourceResponse;
            }
            if (url.endsWith("native-app.js")) {
                try {
                    webResourceResponse = new WebResourceResponse("text/javascript", "UTF-8", WebViewAndroidJSActivity.this.getAssets().open("local.js"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return webResourceResponse;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            WebResourceResponse webResourceResponse = super.shouldInterceptRequest(view, request);
            if (request == null) {
                return webResourceResponse;
            }
            String url = request.getUrl().toString();
            if (url != null && url.endsWith("native-app.js")) {
                try {
                    webResourceResponse = new WebResourceResponse("text/javascript", "UTF-8", WebViewAndroidJSActivity.this.getAssets().open("local.js"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return webResourceResponse;
        }
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
        public void native_launchFunc(final String funcName, final String jsonStr) {
            if (funcName.startsWith("pickPhotoFromLibrary")) {
                selectImageFromLocal(funcName);//手机相册
            } else if (funcName.startsWith("makePhotoFromCamera")) {
                takePhoto(funcName);//手机拍照
            }
        }

    }

    /**
     * 手机相册
     * */

    public void selectImageFromLocal(String funcName){
        pickPhotoFuncNameName = funcName;
        if(FileHelper.isSdCardExist()){
            Intent intent;
            if(Build.VERSION.SDK_INT<19){
                intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            }else{
                intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            startActivityForResult(intent, StringConstant.REQUEST_CODE_SELECT_PICTURE);
        }else{
            toast.showToast(StringConstant.Filestatus1);
        }
    }

    /**
     *手机拍照
     * */

    public void takePhoto(String funcName){
        takePhotofuncName = funcName;
        if(FileHelper.isSdCardExist()){
            Uri photoURI=null;
            String name=String.valueOf(System.currentTimeMillis());
            mPath=FileHelper.createAvatarPathPicture(name);
            Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getPackageManager())!=null){
                //创建一个File
                File photoFile = new File(mPath);
                if(photoFile != null){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        //如果是7.0及以上的系统使用FileProvider的方式创建一个Uri
                        photoURI= FileProvider.getUriForFile(WebViewAndroidJSActivity.this, "com.wjn.customwebviewjs.fileprovider", photoFile);
                        takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    } else {
                        //7.0以下使用这种方式创建一个Uri
                        photoURI = Uri.fromFile(photoFile);
                    }
                    //将Uri传递给系统相机
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, StringConstant.REQUEST_CODE_TAKE_PHOTO);
                }
            }
        }else{
            toast.showToast(StringConstant.Filestatus1);
        }
    }

    /**
     * onActivityResult方法
     * */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case StringConstant.REQUEST_CODE_SELECT_PICTURE://本地相册
                //系统相册选取完成
                Uri uri = data.getData();
                if (uri != null) {
                    String filePath;
                    if (!TextUtils.isEmpty(uri.toString()) && uri.toString().startsWith("file")) {
                        filePath = uri.getPath();
                    } else {
                        filePath = RealPathUtil.getRealPathFromURI(this, uri);
                    }
                    bitmap=BitmapFactory.decodeFile(filePath);
                    String base64Image = Base64Util.bitmapToBase64(bitmap);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("image64", base64Image);
                    jsonObject.addProperty("message", "图片获取成功");
                    Log.d("WebViewActivity", "jsonObject:" + jsonObject);
                    webView.loadUrl("javascript:sdk_nativeCallback(\'" + pickPhotoFuncNameName + "\',\'" + jsonObject + "\')");
                }
                break;
            case StringConstant.REQUEST_CODE_TAKE_PHOTO://手机拍照
                bitmap=BitmapFactory.decodeFile(mPath);
                String base64Image = Base64Util.bitmapToBase64(bitmap);
                Log.d("WebViewActivity2", base64Image);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("image64", base64Image);
                webView.loadUrl("javascript:sdk_nativeCallback(\'" + takePhotofuncName + "\',\'" + jsonObject + "\')");
                break;
        }
    }

    /**
     * onDestroy方法
     * */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Base64Util.recycleBitmap(bitmap);//释放Bitmap
        //删除剪切保存的图片
        File file = new File(FileHelper.getTheRootDirectory() + StringConstant.PICTURTemporary_Path);
        Base64Util.deleteFile(file);
        webView.destroy();
    }
}
