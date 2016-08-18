package com.study.yaodh.androidstudy.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by yaodh on 16/8/16.
 */
public class FileUtils {
    private static final String DOWNLOAD_DIR = "download";
    /**
     * 判断SD卡是否可以访问
     * @return
     */
    public static boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable();
    }

    /**
     * 获取缓存目录
     * 有SD卡 -> getExternalCacheDir -> SDCard/Android/data/package/cache API<19需要申请权限
     * 无SD卡 -> getCacheDir -> data/data/package/cache 不需要申请权限
     *
     * @return
     */
    public static File getCacheDir(Context context) {
        if (isSDCardAvailable()) {
            return context.getExternalCacheDir();
        } else {
            return context.getCacheDir();
        }
    }

    public static File getFileDir(Context context) {
        if(isSDCardAvailable()) {
            return context.getExternalFilesDir(null);
        } else {
            return context.getFilesDir();
        }
    }

    public static File getDownloadDir(Context context) {
        File dir = new File(getFileDir(context), File.separator + "download" + File.separator);
        if(!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    /**
     * 保存内容到指定目录
     *
     * @param bytes
     * @param dir 目录
     * @param fileName
     * @return
     */
    public static boolean saveFile(byte[] bytes, File dir, String fileName) {
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return false;
            }
        }
        return saveFile(bytes, new File(dir, fileName));
    }

    /**
     * 保存内容到指定文件，必须保证目录存在
     *
     * @param bytes 二进制数据
     * @param file 文件
     * @return
     */
    public static boolean saveFile(byte[] bytes, File file) {
        if (bytes == null) {
            return false;
        }
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return false;
                }
            }
            fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        file.delete();
        return false;
    }
}
