package com.wjn.customwebviewjs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;


public class RealPathUtil {

    /**
     * 根据Uri获取本地相册图片路径
     */

    public static String getRealPathFromURI(Context context, Uri uri) {
        String realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(context, uri);
        if (TextUtils.isEmpty(realPath)) {
            realPath = RealPathUtil.getRealPathFromURI_API11To18(context, uri);
        }
        if (TextUtils.isEmpty(realPath)) {
            realPath = RealPathUtil.getRealPathFromURI_API19(context, uri);
        }
        return realPath;
    }

    /**
     * 根据Uri获取本地相册图片路径 API19
     */

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        try {
            String wholeID = DocumentsContract.getDocumentId(uri);
            String id = wholeID.split(":")[1];
            String[] column = {MediaStore.Images.Media.DATA};
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        } catch (Exception e) {

        }
        return filePath;
    }

    /**
     * 根据Uri获取本地相册图片路径 API11To18
     */

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API11To18(Context context, Uri contentUri) {
        String result = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            CursorLoader cursorLoader = new CursorLoader(context, contentUri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result = cursor.getString(column_index);
            }
        } catch (Exception e) {

        }
        return result;
    }

    /**
     * 根据Uri获取本地相册图片路径 API11
     */

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
        String result = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        } catch (Exception e) {

        }
        return result;
    }

}
