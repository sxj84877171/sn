package com.sunvote.txpad.ui.fragment.manager;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.txpad.Constants;
import com.sunvote.txpad.base.BaseFragementModel;
import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.cache.SpCache;
import com.sunvote.util.TimeUtils;

import rx.Observable;
import rx.Subscriber;

/**
 *  * Created by Elvis on 2017/9/15.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class BaseStationFragmentModel extends BaseFragementModel {

    public String getBaseStationMode(){
        return BaseStationManager.getInstance().getBaseStationInfo().getKeyPadWorkingMode();//keyPadWorkingMode
    }

    /**
     * TODO 后续设置保存语言，下次启动时，不随系统的语言而变化，暂时不实现。
     * @return
     */
    public String getApplicationLanguage(){
        return null;
    }

    public String getKeyPadMode(){
        return "S52Plus" ;
    }

    public String getChMsg(){
        return BaseStationManager.getInstance().getBaseStationInfo().getBaseStationChannel();
    }

    public Observable<String> writeMode(final int mode){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                BaseStationManager.getInstance().writeKeyPad_WorkingMode(mode);
                TimeUtils.sleep(1000);
                BaseStationManager.getInstance().readKeyPad_WorkingMode();
                TimeUtils.sleep(1000);
                subscriber.onNext(getBaseStationMode());
                subscriber.onCompleted();
            }
        }).compose(BaseModel.<String>io_main());

    }

    public Observable<String> writeBaseStationChanel(final String chanel){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                BaseStationManager.getInstance().writeChanel(chanel);
                TimeUtils.sleep(1000);
                BaseStationManager.getInstance().readChanel();
                TimeUtils.sleep(1000);
                subscriber.onNext(getChMsg());
                subscriber.onCompleted();
            }
        }).compose(BaseModel.<String>io_main());
    }

    public void saveSignMode(){
        SpCache.getInstance().putInt(Constants.SAVE_SIGN_MODE_KEY,1);
    }

    public void saveSignUID(){
        SpCache.getInstance().putInt(Constants.SAVE_SIGN_MODE_KEY,2);
    }

    public int queryMode(){
        return SpCache.getInstance().getInt(Constants.SAVE_SIGN_MODE_KEY,1);
    }
}
