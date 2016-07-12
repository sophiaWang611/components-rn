/*
 * @Author: Mars Tsang
 * @Mail: zmars@me.com
 */

package com.rncomponents.utils;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zzmars on 14-10-17.
 */
public class FileUtils {
    public final static String APP_DIR_NAME = "mishi";
    public final static String TEMP_DIR_NAME = "temp";

    public static String fileNameTmpImage() {
        return "img_" + currentTimeString() + ".jpg";
    }

    private static String currentTimeString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
        Date curDate = new Date(System.currentTimeMillis());
        return format.format(curDate);
    }

    public static boolean DeleteFile(String strFullFileName) {
        if (null == strFullFileName || 0 >= strFullFileName.length())
            return false;

        File file = new File(strFullFileName);
        if (file.isFile()) {
            return file.delete();
        }
        return false;
    }

    public static String imageStorePath() {
        String path = getSDPath() + "/" + APP_DIR_NAME + "/" + TEMP_DIR_NAME + "/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    private static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取根目录
        }
        return sdDir.getAbsolutePath();
    }

    public static String savePath() {
        return Environment.getExternalStorageDirectory()
                + "/mishi/";
    }
}
