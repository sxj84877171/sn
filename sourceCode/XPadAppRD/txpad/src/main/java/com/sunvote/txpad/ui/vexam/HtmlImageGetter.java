package com.sunvote.txpad.ui.vexam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.sunvote.txpad.R;
import com.sunvote.util.LogUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static com.sunvote.sunvotesdk.BaseStationManager.TAG;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class HtmlImageGetter implements Html.ImageGetter {

    private Context context ;

    public HtmlImageGetter(Context context){
        this.context = context;
    }

    @Override
    public Drawable getDrawable(String s) {
        LogUtil.i("HtmlImageGetter","HtmlImageGetter,url:" + s);
        final LevelListDrawable levelListDrawable = new LevelListDrawable();
       /* Glide.with(context).load(s).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable =new BitmapDrawable(resource);
                levelListDrawable.addLevel(0,0,drawable);
            }
        });
        return levelListDrawable;*/
        Drawable drawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
        levelListDrawable.addLevel(0,0,drawable);
        levelListDrawable.setBounds(0, 0, 100, 100);
        return levelListDrawable;
    }

//    class LoadImage extends AsyncTask<Object, Void, Bitmap> {
//
//        private LevelListDrawable mDrawable;
//
//        @Override
//        protected Bitmap doInBackground(Object... params) {
//            String source = (String) params[0];
//            mDrawable = (LevelListDrawable) params[1];
//            LogUtil.d(TAG, "doInBackground " + source);
//            try {
//                InputStream is = new URL(source).openStream();
//                return BitmapFactory.decodeStream(is);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            Log.d(TAG, "onPostExecute drawable " + mDrawable);
//            Log.d(TAG, "onPostExecute bitmap " + bitmap);
//            if (bitmap != null) {
//                BitmapDrawable d = new BitmapDrawable(bitmap);
//                mDrawable.addLevel(1, 1, d);
//                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
//                mDrawable.setLevel(1);
//            }
//        }
//    }
}
