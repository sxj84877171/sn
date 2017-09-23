package com.sunvote.util;


import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 车主APP<br>
 * 作者： 孙向锦<br>
 * 时间： 2016/10/10<br>
 * 公司：长沙硕铠电子科技有限公司<br>
 * Email：sunxiangjin@soarsky-e.com<br>
 * 公司网址：http://www.soarsky-e.com<br>
 * 公司地址（Add）  ：湖南省长沙市岳麓区麓谷信息港A座8楼<br>
 * 版本：1.0.0.0<br>
 * 邮编：410000<br>
 * 程序功能：<br>
 * Toast的工具类<br>
 */
public class ToastUtil {

    /**
     * 展示给用户的Toast信息
     * @param context 上下文
     * @param resId  资源id
     */
    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    /**
     * 展示给用户的Toast信息
     * @param context 上下文
     * @param resId  资源id
     * @param duration  显示时长
     */
    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    /**
     * 展示给用户的Toast信息
     * @param context 上下文
     * @param text 消息内容
     */
    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 展示给用户的Toast信息
     * @param context 上下文
     * @param text 消息内容
     * @param duration 显示时长
     */
    public static void show(Context context, CharSequence text, int duration) {
        text = TextUtils.isEmpty(text == null ? "" : text.toString()) ? "请检查您的网络！"
                : text;
        Toast mToast = Toast.makeText(context, text, duration);
        mToast.show();
    }

    /**
     * 展示给用户的Toast信息
     * @param context 上下文
     * @param resId  资源id
     * @param args  资源id对应的字符串的占位符的内容
     */
    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args),
                Toast.LENGTH_SHORT);
    }

    /**
     * 展示给用户的Toast信息
     * @param context 上下文
     * @param format 消息内容
     * @param args 上下文
     */
    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    /**
     *  展示给用户的Toast信息
     * @param context 上下文
     * @param resId 资源id
     * @param duration 时长
     * @param args 资源id对应的字符串的占位符的内容
     */
    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args),
                duration);
    }

    /**
     * 展示给用户的Toast信息
     * @param context 上下文
     * @param format 消息内容
     * @param duration 时长
     * @param args 资源id对应的字符串的占位符的内容
     */
    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }
}
