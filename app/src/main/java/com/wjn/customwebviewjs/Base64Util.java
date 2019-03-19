package com.wjn.customwebviewjs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Base64Util {

    /**
     * bitmap转为base64
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 72, baos);
                bitmap.recycle();
                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 回收bitmap
     * */

    public static void recycleBitmap(Bitmap bitmap){
        if(bitmap!=null){
            if(!bitmap.isRecycled()){
                bitmap.recycle();
            }
        }
    }

    /**
     * 删除文件夹
     * */

    public static void deleteFile(File file){
        if(file!=null){
            if(file.exists()){//判断文件是否存在
                if(file.isFile()){//判断是否是文件
                    file.delete();//delete()方法 你应该知道 是删除的意思;
                }else if(file.isDirectory()){//否则如果它是一个目录
                    File files[]=file.listFiles(); // 声明目录下所有的文件 files[];
                    for(int i=0;i<files.length;i++){ // 遍历目录下所有的文件
                        deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                    }
                }
                file.delete();
            }
        }
    }

}
