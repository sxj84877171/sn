package com.sunvote.txpad.ui.fragment.manager;

import android.text.TextUtils;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.txpad.Constants;
import com.sunvote.txpad.base.BaseFragmentPresent;
import com.sunvote.txpad.base.BaseSubscriber;
import com.sunvote.txpad.cache.SpCache;
import com.sunvote.util.SPUtils;

/**
 * Created by Elvis on 2017/9/18.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class BaseStationFragmentPresent extends BaseFragmentPresent<BaseStationFragmentModel,IBaseStationFragmentView> {

    public void init(){
        view.showKeyPadWorkingMode(model.getBaseStationMode());
        view.showKeyPadModel(model.getKeyPadMode());
        view.showCh(model.getChMsg());
        view.showSignMode(model.queryMode());
    }

    public void setFreeMode(){
        view.showProgress();
        mRxManager.add(model.writeMode(2).subscribe(new BaseSubscriber<String>(){
            @Override
            public void onNext(String s) {
                view.showKeyPadWorkingMode(s);
                view.dismissProgress();
            }
        }));
    }

    public void setFixedMode(){
        view.showProgress();
        mRxManager.add(model.writeMode(1).subscribe(new BaseSubscriber<String>(){
            @Override
            public void onNext(String s) {
                view.showKeyPadWorkingMode(s);
                view.dismissProgress();
            }
        }));
    }

    public void deviceCheck(){
        view.showCheckFragment();
    }

    public void connectAgain(){
        if(!BaseStationManager.getInstance().getBaseStationInfo().isConnected()){
            view.showProgress();
            BaseStationManager.getInstance().init();
            view.refeash();
        }
        view.dismissProgress();
    }

    public void writeChanel(String chanel){
        if(!TextUtils.isEmpty(chanel)){
            int ch = Integer.parseInt(chanel);
            if(ch > 0 && ch <= 80) {
                view.showProgress();
                model.writeBaseStationChanel("" + ch).subscribe(new BaseSubscriber<String>(){
                    @Override
                    public void onNext(String s) {
                        view.dismissProgress();
                        view.showCh(s);
                        view.refeash();
                    }
                });
                return;
            }
        }
        view.showFail();
    }

    public void saveSignMode(){
        view.showProgress();
        model.saveSignMode();
        view.showSignMode(model.queryMode());
        view.dismissProgress();
    }

    public void saveSignUID(){
        view.showProgress();
        model.saveSignUID();
        view.showSignMode(model.queryMode());
        view.dismissProgress();
    }

    public void saveSignBlindSn(){
        view.showProgress();
        model.saveSignBindUID();
        view.showSignMode(model.queryMode());
        view.dismissProgress();
    }

    public void showChooseKeyboardType(int type){
        model.saveChooseKeyboardType(type);
        view.showChooseKeyboardType(type);
    }
}
