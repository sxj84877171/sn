package com.sunvote.txpad.base;

import android.content.Context;

import java.text.SimpleDateFormat;

/**
 * Created by Elvis on 2017/9/12.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:ETest
 */
public class BasePresent<M extends BaseModel,V extends BaseView> {

    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static final String SUCCESS = "0" ;

    protected M model;

    public void setModel(M model) {
        this.model = model;
    }

    public M getModel() {
        return model;
    }

    protected V view ;

    public RxManager mRxManager = new RxManager();

    public void setView(V view) {
        this.view = view;
    }

    public V getView() {
        return view;
    }

    public void init(){}

    public void onDestroy(){
        mRxManager.clear();
    }

    public void onCreate(Context context){

    }

    public String intToTimeString(int time){
        int modeSec = time % 60 ;
        String modeSecStr = null ;
        if(modeSec == 0){
            modeSecStr = "00" ;
        }else if(modeSec < 10){
            modeSecStr = "0" + modeSec ;
        }else{
            modeSecStr = "" + modeSec ;
        }
        int present = time / 60 ;
        String pStr = null ;
        if(present == 0){
            pStr = "00"  ;
        }else if(present < 10) {
            pStr = "0" + present ;
        }else{
            pStr = "" + present ;
        }
        return pStr + ":" + modeSecStr ;
    }
}
