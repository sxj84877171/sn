package com.sunvote.txpad.cache;

import android.text.TextUtils;

import com.sunvote.util.EncryptUtils;
import com.sunvote.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest） 缓存类
 */
public class FileCache {

    public static final String PATH = "/sdcard/ETest/data/";

    private static FileCache fileCache = new FileCache();

    private FileCache() {

    }

    public static FileCache getFileCache() {
        return fileCache;
    }

    /**
     * 根据key保存文件
     *
     * @param key 对应key
     * @param obj 对应文件
     */
    public void saveObject(String key, Object obj) {
        key = encryptFilename(key);
        File path = new File(PATH);
        if (!path.exists()) {
            path.mkdirs();
        }

        File dest = new File(PATH + key + ".obj");
        if (!dest.exists()) {
            try {
                dest.createNewFile();
            } catch (IOException e) {
                LogUtil.e("FileCache", e);
            } catch (Exception e) {
                LogUtil.e("FileCache", e);
            }
        }

        ObjectOutputStream objectOutputStream = null;

        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(dest));
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
        } catch (IOException e) {
            LogUtil.e("FileCache", e);
        } catch (Exception e) {
            LogUtil.e("FileCache", e);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    LogUtil.e("FileCache", e);
                }
            }
        }
    }

    /**
     * 根据key读取文件
     *
     * @param key
     * @return
     */
    public Object readObject(String key) {
        key = encryptFilename(key);
        File path = new File(PATH);
        if (!path.exists()) {
            path.mkdirs();
        }

        File dest = new File(PATH + key + ".obj");
        if (!dest.exists()) {
            return null;
        }

        ObjectInputStream objectOutputStream = null;

        try {
            objectOutputStream = new ObjectInputStream(new FileInputStream(dest));
            return objectOutputStream.readObject();
        } catch (IOException e) {
            LogUtil.e("FileCache", e);
        } catch (ClassNotFoundException e) {
            LogUtil.e("FileCache", e);
        } catch (Exception e) {
            LogUtil.e("FileCache", e);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    LogUtil.e("FileCache", e);
                }
            }
        }
        return null;
    }

    public void deleteCatch(String key) {
        key = encryptFilename(key);
        File path = new File(PATH);
        if (!path.exists()) {
            path.mkdirs();
        }

        File dest = new File(PATH + key + ".obj");
        if(dest.exists()){
            dest.delete();
        }
    }

    public String encryptFilename(String key){
        String retkey = EncryptUtils.encryptMD5ToString(key);
        if(TextUtils.isEmpty(retkey)){
            retkey = key;
        }
        if(retkey.length() > 15){
            retkey = retkey.substring(0,15);
        }
        return retkey;
    }
}
