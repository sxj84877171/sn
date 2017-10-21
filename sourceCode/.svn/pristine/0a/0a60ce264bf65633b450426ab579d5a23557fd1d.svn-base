package com.sunvote.txpad.cache;

import android.content.Context;

import com.sunvote.util.SPUtils;

/**
 * Created by Elvis on 2017/9/29.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class SpCache {

    private static final SpCache ourInstance = new SpCache();

    public static SpCache getInstance() {
        return ourInstance;
    }

    private SpCache() {
    }

    private Context mContext ;

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public boolean putString(String key, String value) {
        return SPUtils.putString(mContext,key, value);
    }

    /**
     * SP中读取String
     *
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值null
     */
    public String getString(String key) {
        return SPUtils.getString(mContext,key);
    }

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public String getString( String key, String defaultValue) {
        return SPUtils.getString(mContext,key,defaultValue);
    }

    /**
     * SP中写入int类型value
     *
     * @param key     键
     * @param value   值
     * @return true: 写入成功<br>false: 写入失败
     */
    public boolean putInt(String key, int value) {
        return SPUtils.putInt(mContext,key,value);
    }

    /**
     * SP中读取int
     *
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public int getInt(String key) {
        return SPUtils.getInt(mContext,key);
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public int getInt(String key, int defaultValue) {
        return SPUtils.getInt(mContext,key,defaultValue);
    }

    /**
     * SP中写入long类型value
     *
     * @param key     键
     * @param value   值
     * @return true: 写入成功<br>false: 写入失败
     */
    public boolean putLong(String key, long value) {
        return SPUtils.putLong(mContext,key,value);
    }

    /**
     * SP中读取long
     *
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public long getLong(String key) {
        return SPUtils.getLong(mContext,key, -1);
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public long getLong(String key, long defaultValue) {
        return SPUtils.getLong(mContext,key, defaultValue);
    }

    /**
     * SP中写入float类型value
     *
     * @param key     键
     * @param value   值
     * @return true: 写入成功<br>false: 写入失败
     */
    public boolean putFloat(Context context, String key, float value) {
        return SPUtils.putFloat(mContext,key, value);
    }

    /**
     * SP中读取float
     *
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public float getFloat(String key) {
        return SPUtils.getFloat(mContext, key, -1);
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public float getFloat(String key, float defaultValue) {
        return SPUtils.getFloat(mContext,key, defaultValue);
    }

    /**
     * SP中写入boolean类型value
     *
     * @param key     键
     * @param value   值
     * @return true: 写入成功<br>false: 写入失败
     */
    public boolean putBoolean(String key, boolean value) {
        return SPUtils.putBoolean(mContext,key, value);
    }

    /**
     * SP中读取boolean
     *
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值false
     */
    public boolean getBoolean(String key) {
        return SPUtils.getBoolean(mContext, key, false);
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return SPUtils.getBoolean(mContext,key, defaultValue);
    }
}
