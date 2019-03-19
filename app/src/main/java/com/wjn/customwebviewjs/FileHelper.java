package com.wjn.customwebviewjs;

import android.os.Environment;
import android.text.format.DateFormat;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by wjn on 2017/11/24.
 * 文件工具类
 */

public class FileHelper {

    /**
     * 判断当前设备是否有外部存储(SD卡)
     */

    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 手机拍照生成路径
     * */

    public static String createAvatarPathPicture(String userName) {
        String dir = FileHelper.getTheRootDirectory() + StringConstant.PICTURTemporary_Path;
        File destDir = new File(dir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File file;
        if (userName != null) {
            file = new File(dir, userName + ".png");
        } else {
            file = new File(dir, new DateFormat().format("yyyy_MMdd_hhmmss",
                    Calendar.getInstance(Locale.CHINA)) + ".png");
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取sd卡根路径
     * */

    public static String getTheRootDirectory(){
        String result="";
        if(isSdCardExist()){
            result= Environment.getExternalStorageDirectory()+ "/";
        }
        return result;
    }

}

